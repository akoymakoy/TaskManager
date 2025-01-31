package com.example.taskmanager.service;

import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService (TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public Page<Task> getAllTasks(Pageable pageable){
       return taskRepository.findAll(pageable);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    public Task createTask(@Valid Task task) { // ✅ Ensure @Valid is here
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask){
       return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setDueDate(updatedTask.getDueDate());
            task.setCompleted(updatedTask.isCompleted());
            return taskRepository.save(task);
        }).orElseThrow( () -> new RuntimeException(("Task not found for updating")) );
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }

    public Task markTaskAsCompleted(Long id){
        return taskRepository.findById(id).map (task -> {
            task.setCompleted(true);
            return taskRepository.save(task);
        }).orElseThrow( () -> new RuntimeException(("Task not found for updating")) );
    }

}