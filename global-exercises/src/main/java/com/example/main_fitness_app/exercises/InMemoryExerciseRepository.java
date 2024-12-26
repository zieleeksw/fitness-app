package com.example.main_fitness_app.exercises;

import java.util.*;
import java.util.stream.Collectors;

class InMemoryExerciseRepository implements ExerciseRepository {

    private final Map<UUID, ExerciseEntity> database = new HashMap<>();

    @Override
    public Optional<ExerciseEntity> findByName(String name) {
        return database.values().stream()
                .filter(entity -> entity.getName().equals(name))
                .findFirst();
    }

    @Override
    public ExerciseEntity save(ExerciseEntity entity) {
        UUID id = entity.getId() != null ? entity.getId() : UUID.randomUUID();
        ExerciseEntity savedEntity = new ExerciseEntity(entity.getName());
        savedEntity.setId(id);
        database.put(id, savedEntity);
        return savedEntity;
    }

    @Override
    public Optional<ExerciseEntity> findById(UUID id) {
        return Optional.ofNullable(database.get(id));
    }


    @Override
    public void deleteById(UUID id) {
        database.remove(id);
    }

    @Override
    public Set<ExerciseEntity> findByNameContaining(String partialName) {
        return database.values().stream()
                .filter(entity -> entity.getName().toLowerCase().contains(partialName.toLowerCase()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ExerciseEntity> findAll() {
        return new HashSet<>(database.values());
    }

    @Override
    public Integer count() {
        return database.size();
    }
}