package com.example.main_fitness_app.exercises.dto;

import jakarta.validation.constraints.NotBlank;

public record MuscleUsageDto(
        @NotBlank String muscle,
        @NotBlank String intensity
) {
}
