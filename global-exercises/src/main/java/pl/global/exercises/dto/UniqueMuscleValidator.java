package pl.global.exercises.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class UniqueMuscleValidator implements ConstraintValidator<UniqueMuscle, Set<MuscleUsageDto>> {

    @Override
    public boolean isValid(Set<MuscleUsageDto> muscleUsages, ConstraintValidatorContext context) {
        if (muscleUsages == null || muscleUsages.isEmpty()) {
            return true;
        }

        Map<String, Long> muscleCounts = muscleUsages.stream()
                .collect(Collectors.groupingBy(MuscleUsageDto::muscle, Collectors.counting()));

        Set<String> duplicates = muscleCounts.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        if (!duplicates.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Duplicate muscles detected: " + String.join(", ", duplicates)
            ).addConstraintViolation();
            return false;
        }

        return true;
    }
}