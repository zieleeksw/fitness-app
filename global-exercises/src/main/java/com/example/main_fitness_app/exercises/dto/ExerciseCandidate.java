package com.example.main_fitness_app.exercises.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExerciseCandidate(
        @NotBlank @Size(max = 64) String name,
        @NotBlank @Size(max = 128) String instruction,
        // TODO: // Add an enum validator before integration tests
        @NotBlank String difficultyLevel
) {

}
