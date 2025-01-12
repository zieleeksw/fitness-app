package pl.global.exercises.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
class MuscleUsage {

    @Enumerated(EnumType.STRING)
    private Muscle muscle;
    @Enumerated(EnumType.STRING)
    private Intensity intensity;

    public MuscleUsage(Muscle muscle, Intensity intensity) {
        this.muscle = muscle;
        this.intensity = intensity;
    }

    protected MuscleUsage() {
    }

    public Muscle getMuscle() {
        return muscle;
    }

    public Intensity getIntensity() {
        return intensity;
    }
}
