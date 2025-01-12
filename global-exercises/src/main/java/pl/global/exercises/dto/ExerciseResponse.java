package pl.global.exercises.dto;

import java.util.Set;
import java.util.UUID;

public record ExerciseResponse(
        UUID id,
        String name,
        String instruction,
        String difficultyLevel,
        Set<MuscleUsageDto> muscleUsages
) {

}
