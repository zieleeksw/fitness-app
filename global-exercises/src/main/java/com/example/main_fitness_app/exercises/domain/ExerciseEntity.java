package com.example.main_fitness_app.exercises.domain;

import com.example.main_fitness_app.exercises.dto.ExerciseCandidate;
import com.example.main_fitness_app.exercises.dto.ExerciseResponse;
import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "exercises")
class ExerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DifficultyLevel difficultyLevel;

    ExerciseEntity(String name, DifficultyLevel difficultyLevel) {
        this.name = name;
        this.difficultyLevel = difficultyLevel;
    }

    protected ExerciseEntity() {
    }

    static ExerciseEntity from(ExerciseCandidate candidate) {
        return new ExerciseEntity(
                candidate.name(),
                DifficultyLevel.valueOf(candidate.difficultyLevel()
                )
        );
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

    DifficultyLevel getDifficultyLevel() {
        return this.difficultyLevel;
    }

    ExerciseResponse toDto() {
        return new ExerciseResponse(
                this.id,
                this.name,
                this.difficultyLevel.name()
        );
    }
}