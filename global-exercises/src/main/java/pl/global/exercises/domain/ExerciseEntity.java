package pl.global.exercises.domain;

import jakarta.persistence.*;
import pl.global.exercises.dto.DifficultyLevelDto;
import pl.global.exercises.dto.ExerciseCandidate;
import pl.global.exercises.dto.ExerciseResponse;
import pl.global.exercises.dto.MuscleUsageDto;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity(name = "exercises")
class ExerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String instruction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DifficultyLevel difficultyLevel;

    @ElementCollection
    @CollectionTable(
            name = "exercise_muscle_usage",
            joinColumns = @JoinColumn(name = "exercise_id")
    )
    private Set<MuscleUsage> muscleUsages = new HashSet<>();

    ExerciseEntity(String name, String instruction, DifficultyLevel difficultyLevel, Set<MuscleUsage> muscleUsages) {
        this.name = name;
        this.difficultyLevel = difficultyLevel;
        this.instruction = instruction;
        this.muscleUsages = muscleUsages;
    }

    protected ExerciseEntity() {
    }

    static ExerciseEntity from(ExerciseCandidate candidate) {
        Set<MuscleUsage> muscleUsages = candidate.muscleUsages().stream()
                .map(mu -> new MuscleUsage(Muscle.valueOf(mu.muscle()), Intensity.valueOf(mu.intensity())))
                .collect(Collectors.toSet());

        return new ExerciseEntity(
                candidate.name(),
                candidate.instruction(),
                DifficultyLevel.valueOf(String.valueOf(candidate.difficultyLevel())),
                muscleUsages
        );
    }

    UUID getId() {
        return this.id;
    }

    void setId(UUID id) {
        this.id = id;
    }

    String getName() {
        return this.name;
    }

    String getInstruction() {
        return this.instruction;
    }

    DifficultyLevel getDifficultyLevel() {
        return this.difficultyLevel;
    }

    Set<MuscleUsage> getMuscleUsages() {
        return Collections.unmodifiableSet(this.muscleUsages);
    }

    ExerciseResponse toDto() {
        Set<MuscleUsageDto> muscleUsageDto = this.muscleUsages.stream()
                .map(mu -> new MuscleUsageDto(mu.getMuscle().toString(), mu.getIntensity().toString()))
                .collect(Collectors.toSet());

        return new ExerciseResponse(
                this.id,
                this.name,
                this.instruction,
                DifficultyLevelDto.valueOf(this.difficultyLevel.name()),
                muscleUsageDto
        );
    }
}