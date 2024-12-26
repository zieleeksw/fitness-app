package com.example.main_fitness_app.exercises;

import com.example.main_fitness_app.exercises.dto.ExerciseResponse;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
class ExerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    ExerciseEntity(String name) {
        this.name = name;
    }

    protected ExerciseEntity() {
    }

    void setId(UUID id) {
        this.id = id;
    }

    UUID getId() {
        return this.id;
    }

    String getName() {
        return this.name;
    }

    ExerciseResponse toDto() {
        return new ExerciseResponse(
                this.id,
                this.name
        );
    }
}