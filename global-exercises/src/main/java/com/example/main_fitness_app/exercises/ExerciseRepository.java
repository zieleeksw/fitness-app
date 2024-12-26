package com.example.main_fitness_app.exercises;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

interface ExerciseRepository extends Repository<ExerciseEntity, UUID> {

    Optional<ExerciseEntity> findByName(String name);

    ExerciseEntity save(ExerciseEntity exerciseEntity);

    Optional<ExerciseEntity> findById(UUID id);

    void deleteById(UUID id);

    @Query("SELECT e FROM ExerciseEntity e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :partialName, '%'))")
    Set<ExerciseEntity> findByNameContaining(String partialName);

}
