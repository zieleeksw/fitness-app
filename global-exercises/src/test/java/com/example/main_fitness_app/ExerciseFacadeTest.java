package com.example.main_fitness_app;

import com.example.main_fitness_app.exercises.ExerciseException;
import com.example.main_fitness_app.exercises.ExerciseFacade;
import com.example.main_fitness_app.exercises.InMemoryExerciseRepository;
import com.example.main_fitness_app.exercises.dto.ExerciseCandidate;
import com.example.main_fitness_app.exercises.dto.ExerciseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseFacadeTest {

    private ExerciseFacade exerciseFacade;

    @BeforeEach
    void setup() {
        InMemoryExerciseRepository inMemoryRepository = new InMemoryExerciseRepository();
        exerciseFacade = new ExerciseFacade(inMemoryRepository);
    }

    @Test
    void shouldAddNewExercise() {
        ExerciseCandidate candidate = new ExerciseCandidate("Push-up");

        ExerciseResponse response = exerciseFacade.add(candidate);

        assertNotNull(response);
        assertEquals("Push-up", response.name());
        assertNotNull(response.id());
    }

    @Test
    void shouldThrowExceptionWhenExerciseAlreadyExists() {
        ExerciseCandidate candidate = new ExerciseCandidate("Push-up");
        exerciseFacade.add(candidate);

        assertThrows(ExerciseException.class, () -> exerciseFacade.add(candidate));
    }


    @Test
    void shouldDeleteExercise() {
        ExerciseCandidate candidate = new ExerciseCandidate("Squat");
        ExerciseResponse response = exerciseFacade.add(candidate);
        UUID exerciseId = response.id();

        exerciseFacade.deleteById(exerciseId);

        assertThrows(ExerciseException.class, () -> exerciseFacade.deleteById(exerciseId));
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingExercise() {
        UUID exerciseId = UUID.randomUUID();

        assertThrows(ExerciseException.class, () -> exerciseFacade.deleteById(exerciseId));
    }

    @Test
    void shouldFindExerciseByNameContaining() {
        exerciseFacade.add(new ExerciseCandidate("Push-up"));
        exerciseFacade.add(new ExerciseCandidate("Pull-up"));
        exerciseFacade.add(new ExerciseCandidate("Squat"));

        Set<ExerciseResponse> results = exerciseFacade.findByNameContaining("up");

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(ex -> ex.name().equals("Push-up")));
        assertTrue(results.stream().anyMatch(ex -> ex.name().equals("Pull-up")));
        assertFalse(results.stream().anyMatch(ex -> ex.name().equals("Squat")));
    }

    @Test
    void shouldReturnEmptyListWhenNoExercisesMatch() {
        exerciseFacade.add(new ExerciseCandidate("Push-up"));
        exerciseFacade.add(new ExerciseCandidate("Pull-up"));

        Set<ExerciseResponse> results = exerciseFacade.findByNameContaining("Squat");

        assertTrue(results.isEmpty());
    }

    @Test
    void shouldFindExerciseByNameContainingCaseInsensitive() {
        exerciseFacade.add(new ExerciseCandidate("Push-up"));

        Set<ExerciseResponse> results = exerciseFacade.findByNameContaining("push");

        assertEquals(1, results.size());
        assertTrue(results.stream().anyMatch(ex -> ex.name().equals("Push-up")));
    }
}