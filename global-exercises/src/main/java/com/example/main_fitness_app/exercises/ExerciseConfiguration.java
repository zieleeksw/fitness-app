package com.example.main_fitness_app.exercises;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ExerciseConfiguration {

    @Bean
    ExerciseFacade exerciseFacade(ExerciseRepository exerciseRepository) {
        return new ExerciseFacade(exerciseRepository);
    }

    ExerciseFacade exerciseFacade() {
        return exerciseFacade(new InMemoryExerciseRepository());
    }
}
