package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskMessageProducer;
import com.example.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@Validated
public class TaskController {

    private final TaskService taskService;

    private final TaskMessageProducer taskMessageProducer;

    public TaskController(TaskService taskService, TaskMessageProducer taskMessageProducer) {
        this.taskService = taskService;
        this.taskMessageProducer = taskMessageProducer;
    }


    @GetMapping
    public Page<Task> getAllTasks(Pageable pageable) {
        return taskService.getAllTasks(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<String> createTask(@Valid @RequestBody Task task) {
        taskMessageProducer.sendTaskToQueue(task);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Task is being processed.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,@Valid @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Task> markTaskAsCompleted(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.markTaskAsCompleted(id));
    }


    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam String message) {
        taskMessageProducer.sendMessage("taskQueue", message);
        return ResponseEntity.ok("Message sent to queue: " + message);
    }
}
