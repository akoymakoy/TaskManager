package com.example.taskmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank (message = "Title cannot be empty")
    private String title;

    private String description;

    @FutureOrPresent (message = "Cant be past due date")
    private LocalDate dueDate;

    @Column(nullable = false)
    private boolean completed;

}