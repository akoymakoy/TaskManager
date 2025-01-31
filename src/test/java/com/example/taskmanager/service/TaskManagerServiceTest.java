package com.example.taskmanager.service;

import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskManagerServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask(){
        Task task = new Task(1L, "Mock Task", "Mock Description", null, false);
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        Task created = taskService.createTask(task);
        assertNotNull(created);
        assertEquals("Mock Task", created.getTitle());
    }

    @Test
    void testGetTaskById(){
        Task task = new Task(1L, "Mock Task", "Mock Description", null, false);
        when(taskRepository.findById(any(Long.class))).thenReturn(Optional.of(task));
        Task searched = taskService.getTaskById(1L);
        assertEquals("Mock Task", searched.getTitle());
    }

    @Test
    void getTaskById_TaskNotFound_ShouldThrowException() {
        Long taskId = 123L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> {
            taskService.getTaskById(taskId);
        });

        assertEquals("Task not found with id: 123", exception.getMessage());
    }
}
