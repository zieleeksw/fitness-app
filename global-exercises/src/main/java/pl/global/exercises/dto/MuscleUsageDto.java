package pl.global.exercises.dto;

import jakarta.validation.constraints.NotBlank;

public record MuscleUsageDto(
        @NotBlank String muscle,
        @NotBlank String intensity
) {
}
