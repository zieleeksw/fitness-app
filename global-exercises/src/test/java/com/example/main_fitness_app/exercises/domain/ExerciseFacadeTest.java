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
        ExerciseCandidate candidate = new ExerciseCandidate("Push-up");

        ExerciseResponse response = facade.add(candidate);

        assertNotNull(response);
        assertEquals("Push-up", response.name());
        assertNotNull(response.id());
    }

    @Test
    void shouldThrowExceptionWhenExerciseAlreadyExists() {
        ExerciseCandidate candidate = new ExerciseCandidate("Push-up");
        facade.add(candidate);

        assertThrows(ExerciseException.class, () -> facade.add(candidate));
    }


    @Test
    void shouldDeleteExercise() {
        ExerciseCandidate candidate = new ExerciseCandidate("Squat");
        ExerciseResponse response = facade.add(candidate);
        UUID exerciseId = response.id();

        facade.deleteById(exerciseId);

        assertEquals(true, facade.findAll().isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingExercise() {
        UUID exerciseId = UUID.randomUUID();

        assertThrows(ExerciseException.class, () -> facade.deleteById(exerciseId));
    }

    @Test
    void shouldFindExerciseByNameContaining() {
        facade.add(new ExerciseCandidate("Push-up"));
        facade.add(new ExerciseCandidate("Pull-up"));
        facade.add(new ExerciseCandidate("Squat"));

        Set<ExerciseResponse> results = facade.findByNameContaining("up");

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(ex -> ex.name().equals("Push-up")));
        assertTrue(results.stream().anyMatch(ex -> ex.name().equals("Pull-up")));
        assertFalse(results.stream().anyMatch(ex -> ex.name().equals("Squat")));
    }

    @Test
    void shouldReturnEmptyListWhenNoExercisesMatch() {
        facade.add(new ExerciseCandidate("Push-up"));
        facade.add(new ExerciseCandidate("Pull-up"));

        Set<ExerciseResponse> results = facade.findByNameContaining("Squat");

        assertTrue(results.isEmpty());
    }

    @Test
    void shouldFindExerciseByNameContainingCaseInsensitive() {
        facade.add(new ExerciseCandidate("Push-up"));

        Set<ExerciseResponse> results = facade.findByNameContaining("push");

        assertEquals(1, results.size());
        assertTrue(results.stream().anyMatch(ex -> ex.name().equals("Push-up")));
    }

    @Test
    void shouldFindAllExercises() {
        facade.add(new ExerciseCandidate("Push-up"));

        Set<ExerciseResponse> results = facade.findAll();

        assertEquals(1, results.size());
        assertTrue(results.stream().anyMatch(ex -> ex.name().equals("Push-up")));
    }

    @Test
    void shouldFindRandomExercise() {
        facade.add(new ExerciseCandidate("Push-up"));
        facade.add(new ExerciseCandidate("Pull-up"));
        facade.add(new ExerciseCandidate("Squat"));

        ExerciseResponse randomExercise = facade.findRandomExercise();

        assertNotNull(randomExercise);
        assertTrue(Set.of("Push-up", "Pull-up", "Squat").contains(randomExercise.name()));
    }

    @Test
    void shouldThrowExceptionWhenNoExercisesAvailable() {
        assertThrows(ExerciseException.class, () -> facade.findRandomExercise());
    }
}
