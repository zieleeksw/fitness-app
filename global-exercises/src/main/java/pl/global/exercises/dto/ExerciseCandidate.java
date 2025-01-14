package pl.global.exercises.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record ExerciseCandidate(
        @ValidName String name,
        @ValidInstruction String instruction,
        @ValidEnum(enumClazz = DifficultyLevelDto.class) String difficultyLevel,
        @NotEmpty(message = "Must not be empty.") @UniqueMuscle @Valid Set<MuscleUsageDto> muscleUsages
) {
}
