package com.example.main_fitness_app.exercises.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExerciseCandidate(
        @NotBlank @Size(max = 100) String name,
        @NotBlank String difficultyLevel
) {

}
