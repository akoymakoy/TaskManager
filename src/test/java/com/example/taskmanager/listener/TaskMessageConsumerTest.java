package com.example.taskmanager.listener;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class TaskMessageConsumerTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskMessageConsumer taskMessageConsumer;

    @Test
    void receiveTask_ShouldSaveTask() throws Exception {
        Task task = new Task();
        task.setTitle("JMS Task");
        task.setDescription("Received from queue");
        task.setDueDate(LocalDate.of(2025, 1, 25));
        task.setCompleted(false);

        ObjectMapper objectMapper = new ObjectMapper();
        String taskJson = objectMapper.writeValueAsString(task);

        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);

        taskMessageConsumer.receiveTask(taskJson);

        Mockito.verify(taskRepository, Mockito.times(1)).save(Mockito.any(Task.class));
    }
}
