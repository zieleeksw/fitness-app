package pl.global.exercises.dto;

public record MuscleUsageDto(
        @ValidEnum(enumClazz = MuscleDto.class) String muscle,
        @ValidEnum(enumClazz = IntensityDto.class) String intensity
) {
}