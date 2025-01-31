package com.example.taskmanager.listener;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TaskMessageConsumer {

    private final TaskRepository taskRepository;
    private final ObjectMapper objectMapper;

    public TaskMessageConsumer(TaskRepository taskRepository, ObjectMapper objectMapper) {
        this.taskRepository = taskRepository;
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "taskQueue")
    public void receiveTask(String taskJson) {
        try {
            Task task = objectMapper.readValue(taskJson, Task.class);
            System.out.println("Received Task from Queue: " + task);
            taskRepository.save(task);
        } catch (Exception e) {
            System.err.println("Failed to process Task message: " + e.getMessage());
        }
    }
}
