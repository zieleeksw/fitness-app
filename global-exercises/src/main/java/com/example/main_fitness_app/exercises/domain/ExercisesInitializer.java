package com.example.main_fitness_app.exercises.domain;

class ExercisesInitializer {

    private final ExerciseRepository repository;

    ExercisesInitializer(ExerciseRepository repository) {
        this.repository = repository;
    }

    void init() {
        if (repository.count() == 0) {
            repository.save(new ExerciseEntity("Push-up", DifficultyLevel.BEGINNER));
            repository.save(new ExerciseEntity("Pull-up", DifficultyLevel.INTERMEDIATE));
            repository.save(new ExerciseEntity("Squat", DifficultyLevel.ADVANCED));
            repository.save(new ExerciseEntity("Leg extension", DifficultyLevel.INTERMEDIATE));
        }
    }
}
