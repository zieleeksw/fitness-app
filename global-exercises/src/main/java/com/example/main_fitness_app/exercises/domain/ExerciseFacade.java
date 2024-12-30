package com.example.main_fitness_app.exercises.domain;

import com.example.main_fitness_app.exercises.dto.ExerciseCandidate;
import com.example.main_fitness_app.exercises.dto.ExerciseResponse;

import java.util.*;
import java.util.stream.Collectors;

public class ExerciseFacade {

    private final ExerciseRepository repository;

    public ExerciseFacade(ExerciseRepository repository) {
        this.repository = repository;
    }

    public ExerciseResponse add(ExerciseCandidate candidate) {
        if (repository.findByName(candidate.name()).isPresent()) {
            throw new ExerciseException("Exercise already exists");
        }

        ExerciseEntity entity = ExerciseEntity.from(candidate);
        ExerciseEntity savedEntity = repository.save(entity);
        return savedEntity.toDto();
    }

    public void deleteById(UUID id) {
        repository.findById(id)
                .orElseThrow(() -> new ExerciseException("Cannot find exercise with id: " + id));
        repository.deleteById(id);
    }

    public Set<ExerciseResponse> findByNameContaining(String partialName) {
        return repository.findByNameContaining(partialName).stream()
                .map(ExerciseEntity::toDto)
                .collect(Collectors.toSet());
    }

    public Set<ExerciseResponse> findAll() {
        return repository.findAll().stream()
                .map(ExerciseEntity::toDto)
                .collect(Collectors.toSet());
    }

    public ExerciseResponse findRandomExercise() {
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

    public Set<String> findAvailableDifficultyLevels() {
        return EnumSet.allOf(DifficultyLevel.class).stream()
                .map(Enum::toString)
                .collect(Collectors.toSet());
    }

    public Set<String> findAvailableMuscles() {
        return EnumSet.allOf(Muscle.class).stream()
                .map(Enum::toString)
                .collect(Collectors.toSet());
    }

    public Set<String> findAvailableIntensities() {
        return EnumSet.allOf(Intensity.class).stream()
                .map(Enum::toString)
                .collect(Collectors.toSet());
    }
}
