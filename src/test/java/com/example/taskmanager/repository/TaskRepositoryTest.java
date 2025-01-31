package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    private Task testTask;

    @BeforeEach
    void setUp() {
        testTask = Task.builder()
                .title("Complete project")
                .description("Finish before the deadline")
                .dueDate(LocalDate.now().plusDays(5))
                .completed(false)
                .build();
    }

    @Test
    void saveTask_ShouldSaveToDatabase() {
        Task savedTask = taskRepository.save(testTask);

        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getTitle()).isEqualTo("Complete project");
    }

    @Test
    void findTaskById_ShouldReturnTask() {
        Task savedTask = taskRepository.save(testTask);

        Optional<Task> foundTask = taskRepository.findById(savedTask.getId());

        assertThat(foundTask).isPresent();
        assertThat(foundTask.get().getTitle()).isEqualTo("Complete project");
    }

    @Test
    void findAllTasks_ShouldReturnListOfTasks() {
        taskRepository.save(testTask);
        taskRepository.save(Task.builder().title("Another Task").dueDate(LocalDate.now().plusDays(3)).completed(false).build());

        List<Task> tasks = taskRepository.findAll();

        assertThat(tasks).hasSize(2);
    }

    @Test
    void deleteTaskById_ShouldRemoveTask() {
        Task savedTask = taskRepository.save(testTask);

        taskRepository.deleteById(savedTask.getId());

        Optional<Task> deletedTask = taskRepository.findById(savedTask.getId());
        assertThat(deletedTask).isEmpty();
    }

    @Test
    void findTaskById_TaskNotFound_ShouldReturnEmptyOptional() {
        Long nonExistingId = 999L;

        Optional<Task> task = taskRepository.findById(nonExistingId);

        assertThat(task).isEmpty();
    }
}
