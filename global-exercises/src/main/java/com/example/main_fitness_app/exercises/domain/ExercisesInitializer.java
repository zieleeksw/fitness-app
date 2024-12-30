package com.example.main_fitness_app.exercises.domain;

class ExercisesInitializer {

    private final ExerciseRepository repository;

    ExercisesInitializer(ExerciseRepository repository) {
        this.repository = repository;
    }

    void init() {
        if (repository.count() == 0) {
            repository.save(new ExerciseEntity("Push-up", "Basic push-up instructions", DifficultyLevel.BEGINNER));
            repository.save(new ExerciseEntity("Pull-up", "Perform a pull-up", DifficultyLevel.INTERMEDIATE));
            repository.save(new ExerciseEntity("Squat", "Perform a proper squat", DifficultyLevel.ADVANCED));
            repository.save(new ExerciseEntity("Leg extension", "Do best extensions!", DifficultyLevel.INTERMEDIATE));
        }
    }
}
