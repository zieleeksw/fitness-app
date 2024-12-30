package com.example.main_fitness_app.exercises.domain;

import com.example.main_fitness_app.exercises.dto.ExerciseCandidate;
import com.example.main_fitness_app.exercises.dto.ExerciseResponse;
import com.example.main_fitness_app.exercises.dto.MuscleUsageDto;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseFacadeTest {

    private final ExerciseFacade facade = new ExerciseConfiguration().exerciseFacade();

    @Test
    void shouldAddNewExercise() {
        Set<MuscleUsageDto> muscleUsages = Set.of(
                new MuscleUsageDto("PECTORAL_MAJOR", "HIGH"),
                new MuscleUsageDto("TRICEPS", "MEDIUM")
        );
        ExerciseCandidate candidate = new ExerciseCandidate(
                "Push-up",
                "Basic push-up instructions",
                "BEGINNER",
                muscleUsages
        );

        ExerciseResponse response = facade.add(candidate);

        assertNotNull(response);
        assertNotNull(response.id());
        assertEquals("Push-up", response.name());
        assertEquals(DifficultyLevel.BEGINNER, DifficultyLevel.valueOf(response.difficultyLevel()));
        assertEquals("Basic push-up instructions", response.instruction());
        assertTrue(response.muscleUsages().contains(new MuscleUsageDto("PECTORAL_MAJOR", "HIGH")));
        assertTrue(response.muscleUsages().contains(new MuscleUsageDto("TRICEPS", "MEDIUM")));
    }

    @Test
    void shouldThrowExceptionWhenExerciseAlreadyExists() {
        Set<MuscleUsageDto> muscleUsages = Set.of(
                new MuscleUsageDto("PECTORAL_MAJOR", "HIGH"),
                new MuscleUsageDto("TRICEPS", "MEDIUM")
        );
        ExerciseCandidate candidate = new ExerciseCandidate(
                "Push-up",
                "Basic push-up instructions",
                "BEGINNER", muscleUsages
        );

        facade.add(candidate);

        assertThrows(ExerciseException.class, () -> facade.add(candidate));
    }

    @Test
    void shouldDeleteExercise() {
        Set<MuscleUsageDto> muscleUsages = Set.of(
                new MuscleUsageDto("QUADRICEPS", "HIGH"),
                new MuscleUsageDto("GLUTES", "MEDIUM")
        );
        ExerciseCandidate candidate = new ExerciseCandidate(
                "Squat",
                "Perform a proper squat",
                "ADVANCED",
                muscleUsages
        );

        ExerciseResponse response = facade.add(candidate);

        UUID exerciseId = response.id();
        facade.deleteById(exerciseId);

        assertTrue(facade.findAll().isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingExercise() {
        UUID exerciseId = UUID.randomUUID();

        assertThrows(ExerciseException.class, () -> facade.deleteById(exerciseId));
    }

    @Test
    void shouldFindExerciseByNameContaining() {
        Set<MuscleUsageDto> muscleUsages = Set.of(
                new MuscleUsageDto("PECTORAL_MAJOR", "HIGH"),
                new MuscleUsageDto("TRICEPS", "MEDIUM")
        );

        facade.add(new ExerciseCandidate("Push-up", "Basic push-up instructions", "BEGINNER", muscleUsages));
        facade.add(new ExerciseCandidate("Pull-up", "Perform a pull-up", "INTERMEDIATE", muscleUsages));
        facade.add(new ExerciseCandidate("Squat", "Perform a proper squat", "ADVANCED", muscleUsages));

        Set<ExerciseResponse> results = facade.findByNameContaining("up");

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(ex -> ex.name().equals("Push-up")));
        assertTrue(results.stream().anyMatch(ex -> ex.name().equals("Pull-up")));
        assertFalse(results.stream().anyMatch(ex -> ex.name().equals("Squat")));
    }

    @Test
    void shouldReturnEmptyListWhenNoExercisesMatch() {
        Set<MuscleUsageDto> muscleUsages = Set.of(
                new MuscleUsageDto("PECTORAL_MAJOR", "HIGH")
        );

        facade.add(new ExerciseCandidate("Push-up", "Basic push-up instructions", "BEGINNER", muscleUsages));
        facade.add(new ExerciseCandidate("Pull-up", "Perform a pull-up", "INTERMEDIATE", muscleUsages));

        Set<ExerciseResponse> results = facade.findByNameContaining("Squat");

        assertTrue(results.isEmpty());
    }

    @Test
    void shouldFindExerciseByNameContainingCaseInsensitive() {
        Set<MuscleUsageDto> muscleUsages = Set.of(
                new MuscleUsageDto("PECTORAL_MAJOR", "HIGH")
        );

        facade.add(new ExerciseCandidate("Push-up", "Basic push-up instructions", "BEGINNER", muscleUsages));

        Set<ExerciseResponse> results = facade.findByNameContaining("push");

        assertEquals(1, results.size());
        assertTrue(results.stream().anyMatch(ex -> ex.name().equals("Push-up")));
    }

    @Test
    void shouldFindAllExercises() {
        Set<MuscleUsageDto> muscleUsages = Set.of(
                new MuscleUsageDto("PECTORAL_MAJOR", "HIGH")
        );

        facade.add(new ExerciseCandidate("Push-up", "Basic push-up instructions", "BEGINNER", muscleUsages));

        Set<ExerciseResponse> results = facade.findAll();

        assertEquals(1, results.size());
        assertTrue(results.stream().anyMatch(ex -> ex.name().equals("Push-up")));
    }

    @Test
    void shouldFindRandomExercise() {
        Set<MuscleUsageDto> muscleUsages = Set.of(
                new MuscleUsageDto("PECTORAL_MAJOR", "HIGH")
        );
        facade.add(new ExerciseCandidate("Push-up", "Basic push-up instructions", "BEGINNER", muscleUsages));
        facade.add(new ExerciseCandidate("Pull-up", "Perform a pull-up", "INTERMEDIATE", muscleUsages));
        facade.add(new ExerciseCandidate("Squat", "Perform a proper squat", "ADVANCED", muscleUsages));

        ExerciseResponse randomExercise = facade.findRandomExercise();

        assertNotNull(randomExercise);
        assertTrue(Set.of("Push-up", "Pull-up", "Squat").contains(randomExercise.name()));
    }

    @Test
    void shouldThrowExceptionWhenNoExercisesAvailable() {
        assertThrows(ExerciseException.class, facade::findRandomExercise);
    }

    @Test
    void shouldThrowExceptionWhenAddingExerciseWithInvalidDifficultyLevel() {
        Set<MuscleUsageDto> muscleUsages = Set.of(
                new MuscleUsageDto("PECTORAL_MAJOR", "HIGH")
        );
        ExerciseCandidate candidate = new ExerciseCandidate(
                "Push-up",
                "Instruction missing",
                "RANDOM",
                muscleUsages
        );

        assertThrows(IllegalArgumentException.class, () -> facade.add(candidate));
    }

    @Test
    void shouldThrowExceptionWhenAddingExerciseWithInvalidMuscle() {
        Set<MuscleUsageDto> muscleUsages = Set.of(
                new MuscleUsageDto("MUSCLE", "HIGH")
        );
        ExerciseCandidate candidate = new ExerciseCandidate(
                "Push-up",
                "Instruction missing",
                "INTERMEDIATE",
                muscleUsages
        );

        assertThrows(IllegalArgumentException.class, () -> facade.add(candidate));
    }

    @Test
    void shouldThrowExceptionWhenAddingExerciseWithInvalidIntensity() {
        Set<MuscleUsageDto> muscleUsages = Set.of(
                new MuscleUsageDto("PECTORAL_MAJOR", "VERY VERY HIGH")
        );
        ExerciseCandidate candidate = new ExerciseCandidate(
                "Push-up",
                "Instruction missing",
                "INTERMEDIATE",
                muscleUsages
        );

        assertThrows(IllegalArgumentException.class, () -> facade.add(candidate));
    }

    @Test
    void shouldReturnAllAvailableDifficultyLevels() {
        Set<String> availableIntensities = facade.findAvailableDifficultyLevels();

        assertEquals(EnumSet.allOf(DifficultyLevel.class).size(), availableIntensities.size());
        assertTrue(availableIntensities.contains("BEGINNER"));
        assertTrue(availableIntensities.contains("INTERMEDIATE"));
        assertTrue(availableIntensities.contains("ADVANCED"));
    }

    @Test
    void shouldReturnAllAvailableMuscles() {
        Set<String> availableMuscles = facade.findAvailableMuscles();

        assertEquals(EnumSet.allOf(Muscle.class).size(), availableMuscles.size());
        assertTrue(availableMuscles.contains("BICEPS"), "Zbiór nie zawiera mięśnia BICEPS");
        assertTrue(availableMuscles.contains("TRICEPS"), "Zbiór nie zawiera mięśnia TRICEPS");
        assertTrue(availableMuscles.contains("FOREARM"), "Zbiór nie zawiera mięśnia FOREARM");
        assertTrue(availableMuscles.contains("DELTOID_FRONT"), "Zbiór nie zawiera mięśnia DELTOID_FRONT");
        assertTrue(availableMuscles.contains("DELTOID_SIDE"), "Zbiór nie zawiera mięśnia DELTOID_SIDE");
        assertTrue(availableMuscles.contains("DELTOID_REAR"), "Zbiór nie zawiera mięśnia DELTOID_REAR");
        assertTrue(availableMuscles.contains("PECTORAL_MAJOR"), "Zbiór nie zawiera mięśnia PECTORAL_MAJOR");
        assertTrue(availableMuscles.contains("PECTORAL_MINOR"), "Zbiór nie zawiera mięśnia PECTORAL_MINOR");
        assertTrue(availableMuscles.contains("LATS"), "Zbiór nie zawiera mięśnia LATS");
        assertTrue(availableMuscles.contains("TRAPEZIUS"), "Zbiór nie zawiera mięśnia TRAPEZIUS");
        assertTrue(availableMuscles.contains("RHOMBOIDS"), "Zbiór nie zawiera mięśnia RHOMBOIDS");
        assertTrue(availableMuscles.contains("ERECTOR_SPINAE"), "Zbiór nie zawiera mięśnia ERECTOR_SPINAE");
        assertTrue(availableMuscles.contains("QUADRICEPS"), "Zbiór nie zawiera mięśnia QUADRICEPS");
        assertTrue(availableMuscles.contains("HAMSTRINGS"), "Zbiór nie zawiera mięśnia HAMSTRINGS");
        assertTrue(availableMuscles.contains("CALVES"), "Zbiór nie zawiera mięśnia CALVES");
        assertTrue(availableMuscles.contains("GLUTES"), "Zbiór nie zawiera mięśnia GLUTES");
        assertTrue(availableMuscles.contains("ABDOMINALS"), "Zbiór nie zawiera mięśnia ABDOMINALS");
        assertTrue(availableMuscles.contains("OBLIQUES"), "Zbiór nie zawiera mięśnia OBLIQUES");
    }

    @Test
    void shouldReturnAllAvailableIntensities() {
        Set<String> availableIntensities = facade.findAvailableIntensities();

        assertEquals(EnumSet.allOf(Intensity.class).size(), availableIntensities.size());
        assertTrue(availableIntensities.contains("VERY_LOW"));
        assertTrue(availableIntensities.contains("LOW"));
        assertTrue(availableIntensities.contains("MEDIUM"));
        assertTrue(availableIntensities.contains("HIGH"));
        assertTrue(availableIntensities.contains("VERY_HIGH"));
    }
}
