package pl.global.exercises.domain;

import java.util.Set;

class ExercisesInitializer {

    private final ExerciseRepository repository;

    ExercisesInitializer(ExerciseRepository repository) {
        this.repository = repository;
    }

    void init() {
        if (repository.count() == 0) {
            repository.save(new ExerciseEntity(
                    "Push-up",
                    "Basic push-up instructions",
                    DifficultyLevel.BEGINNER,
                    Set.of(
                            new MuscleUsage(Muscle.PECTORAL_MAJOR, Intensity.HIGH),
                            new MuscleUsage(Muscle.TRICEPS, Intensity.MEDIUM),
                            new MuscleUsage(Muscle.DELTOID_FRONT, Intensity.MEDIUM)
                    )
            ));

            repository.save(new ExerciseEntity(
                    "Pull-up",
                    "Perform a pull-up",
                    DifficultyLevel.INTERMEDIATE,
                    Set.of(
                            new MuscleUsage(Muscle.LATS, Intensity.HIGH),
                            new MuscleUsage(Muscle.BICEPS, Intensity.MEDIUM),
                            new MuscleUsage(Muscle.RHOMBOIDS, Intensity.HIGH)
                    )
            ));

            repository.save(new ExerciseEntity(
                    "Squat",
                    "Perform a proper squat",
                    DifficultyLevel.ADVANCED,
                    Set.of(
                            new MuscleUsage(Muscle.QUADRICEPS, Intensity.HIGH),
                            new MuscleUsage(Muscle.GLUTES, Intensity.HIGH),
                            new MuscleUsage(Muscle.HAMSTRINGS, Intensity.MEDIUM),
                            new MuscleUsage(Muscle.CALVES, Intensity.LOW)
                    )
            ));

            repository.save(new ExerciseEntity(
                    "Leg extension",
                    "Do best extensions!",
                    DifficultyLevel.INTERMEDIATE,
                    Set.of(
                            new MuscleUsage(Muscle.QUADRICEPS, Intensity.HIGH)
                    )
            ));
        }
    }
}
