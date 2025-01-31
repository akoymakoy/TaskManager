package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskMessageProducer {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public TaskMessageProducer(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(String queue, String message) {
        jmsTemplate.convertAndSend(queue, message);
        System.out.println("Message sent to queue [" + queue + "]: " + message);
    }

    public void sendTaskToQueue(Task task) {
        try {
            String taskJson = objectMapper.writeValueAsString(task);
            jmsTemplate.convertAndSend("taskQueue", taskJson);
            System.out.println("Task sent to queue as JSON: " + taskJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert Task to JSON", e);
        }
    }
}
