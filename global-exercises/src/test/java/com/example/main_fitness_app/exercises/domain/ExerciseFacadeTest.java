package com.example.main_fitness_app.exercises.domain;

import com.example.main_fitness_app.exercises.dto.ExerciseCandidate;
import com.example.main_fitness_app.exercises.dto.ExerciseResponse;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseFacadeTest {

    private final ExerciseFacade facade = new ExerciseConfiguration().exerciseFacade();

    @Test
    void shouldAddNewExercise() {
        ExerciseCandidate candidate = new ExerciseCandidate("Push-up", "BEGINNER");

        ExerciseResponse response = facade.add(candidate);

        assertNotNull(response);
        assertNotNull(response.id());
        assertEquals("Push-up", response.name());
        assertEquals(DifficultyLevel.BEGINNER, DifficultyLevel.valueOf(response.difficultyLevel()));
    }

    @Test
    void shouldThrowExceptionWhenExerciseAlreadyExists() {
        ExerciseCandidate candidate = new ExerciseCandidate("Push-up", "BEGINNER");
        facade.add(candidate);

        assertThrows(ExerciseException.class, () -> facade.add(candidate));
    }


    @Test
    void shouldDeleteExercise() {
        ExerciseCandidate candidate = new ExerciseCandidate("Squat", "ADVANCED");
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
        facade.add(new ExerciseCandidate("Push-up", "BEGINNER"));
        facade.add(new ExerciseCandidate("Pull-up", "INTERMEDIATE"));
        facade.add(new ExerciseCandidate("Squat", "ADVANCED"));

        Set<ExerciseResponse> results = facade.findByNameContaining("up");

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(ex -> ex.name().equals("Push-up")));
        assertTrue(results.stream().anyMatch(ex -> ex.name().equals("Pull-up")));
        assertFalse(results.stream().anyMatch(ex -> ex.name().equals("Squat")));
    }

    @Test
    void shouldReturnEmptyListWhenNoExercisesMatch() {
        facade.add(new ExerciseCandidate("Push-up", "BEGINNER"));
        facade.add(new ExerciseCandidate("Pull-up", "INTERMEDIATE"));

        Set<ExerciseResponse> results = facade.findByNameContaining("Squat");

        assertTrue(results.isEmpty());
    }

    @Test
    void shouldFindExerciseByNameContainingCaseInsensitive() {
        facade.add(new ExerciseCandidate("Push-up", "BEGINNER"));

        Set<ExerciseResponse> results = facade.findByNameContaining("push");

        assertEquals(1, results.size());
        assertTrue(results.stream().anyMatch(ex -> ex.name().equals("Push-up")));
    }

    @Test
    void shouldFindAllExercises() {
        facade.add(new ExerciseCandidate("Push-up", "BEGINNER"));

        Set<ExerciseResponse> results = facade.findAll();

        assertEquals(1, results.size());
        assertTrue(results.stream().anyMatch(ex -> ex.name().equals("Push-up")));
    }

    @Test
    void shouldFindRandomExercise() {
        facade.add(new ExerciseCandidate("Push-up", "BEGINNER"));
        facade.add(new ExerciseCandidate("Pull-up", "INTERMEDIATE"));
        facade.add(new ExerciseCandidate("Squat", "ADVANCED"));

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
        ExerciseCandidate candidate = new ExerciseCandidate("Push-up", "RANDOM");

        assertThrows(IllegalArgumentException.class, () -> facade.add(candidate));
    }
}
