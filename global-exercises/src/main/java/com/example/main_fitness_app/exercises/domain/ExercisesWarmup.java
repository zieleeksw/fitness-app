package com.example.main_fitness_app.exercises.domain;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
class ExercisesWarmup implements ApplicationListener<ContextRefreshedEvent> {

    private final ExercisesInitializer initializer;

    ExercisesWarmup(
            @Qualifier("exerciseRepository") ExerciseRepository repository) {
        this.initializer = new ExercisesInitializer(repository);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initializer.init();
    }
}
