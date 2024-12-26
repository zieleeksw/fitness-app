package com.example.main_fitness_app.exercises;

import com.example.main_fitness_app.exercises.dto.ExerciseCandidate;
import com.example.main_fitness_app.exercises.dto.ExerciseResponse;

import java.util.*;
import java.util.stream.Collectors;

class ExerciseFacade {

    private final ExerciseRepository repository;

    ExerciseFacade(ExerciseRepository repository) {
        this.repository = repository;
    }

    ExerciseResponse add(ExerciseCandidate candidate) {
        if (repository.findByName(candidate.name()).isPresent()) {
            throw new ExerciseException("Exercise already exists");
        }

        ExerciseEntity entity = new ExerciseEntity(candidate.name());
        ExerciseEntity savedEntity = repository.save(entity);
        return savedEntity.toDto();
    }

    void deleteById(UUID id) {
        repository.findById(id)
                .orElseThrow(() -> new ExerciseException("Cannot find exercise with id: " + id));
        repository.deleteById(id);
    }

    Set<ExerciseResponse> findByNameContaining(String partialName) {
        return repository.findByNameContaining(partialName).stream()
                .map(ExerciseEntity::toDto)
                .collect(Collectors.toSet());
    }

    Set<ExerciseResponse> findAll() {
        return repository.findAll().stream()
                .map(ExerciseEntity::toDto)
                .collect(Collectors.toSet());
    }

    ExerciseResponse findRandomExercise() {
        List<ExerciseEntity> exercises = new ArrayList<>(repository.findAll());
        if (exercises.isEmpty()) {
            throw new ExerciseException("Something went wrong. Cannot find any exercises");
        }

        Random random = new Random();
        int exercisesSize = exercises.size();
        int randomIndex = random.nextInt(exercisesSize);
        return exercises
                .get(randomIndex)
                .toDto();
    }
}
