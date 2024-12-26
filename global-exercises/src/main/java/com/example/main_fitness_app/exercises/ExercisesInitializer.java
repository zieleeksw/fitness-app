package com.example.main_fitness_app.exercises;

class ExercisesInitializer {

    private final ExerciseRepository repository;

    ExercisesInitializer(ExerciseRepository repository) {
        this.repository = repository;
    }

    void init() {
        if (repository.count() == 0) {
            repository.save(new ExerciseEntity("Push-up"));
            repository.save(new ExerciseEntity("Pull-up"));
            repository.save(new ExerciseEntity("Squat"));
            repository.save(new ExerciseEntity("Leg extension"));
        }
    }
}
