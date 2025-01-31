package com.example.taskmanager.controller;

import com.example.taskmanager.exception.TaskManagerExceptionHandler;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskMessageProducer;
import com.example.taskmanager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc
@Import(TaskManagerExceptionHandler.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @MockBean
    private TaskMessageProducer taskMessageProducer;

    @Test
    void createTask_ValidRequest_ShouldSendToQueue() throws Exception {

        LocalDate futureDate = LocalDate.now().plusDays(5);

        Task task = new Task();
        task.setTitle("Valid Task");
        task.setDescription("This is a valid test");
        task.setDueDate(futureDate);
        task.setCompleted(false);

        System.out.println("Sending valid request: " + objectMapper.writeValueAsString(task));

        Mockito.doNothing().when(taskMessageProducer).sendTaskToQueue(Mockito.any(Task.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()))
                .andExpect(status().isAccepted());

        Mockito.verify(taskMessageProducer, Mockito.times(1)).sendTaskToQueue(Mockito.any(Task.class));
    }


    @Test
    void createTask_InvalidDueDate_ShouldReturnBadRequest() throws Exception {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setDueDate(LocalDate.of(2020, 1, 1));
        task.setCompleted(false);

        System.out.println("Sending invalid request: " + objectMapper.writeValueAsString(task));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.dueDate").value("Cant be past due date"));
    }

    @Test
    void getTaskById_UnexpectedException_ShouldReturnInternalServerError() throws Exception {
        Mockito.when(taskService.getTaskById(Mockito.anyLong())).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("An unexpected error occurred: Unexpected error"));
    }
}
