package pl.global.exercises;


import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import pl.global.exercises.dto.ExerciseCandidate;
import pl.global.exercises.dto.MuscleUsageDto;

import java.util.Set;

import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class FindRandomExerciseIntegrationTest extends BaseIntegration {

    @Test
    void shouldReturnRandomExercise() throws Exception {
        Set<MuscleUsageDto> muscleUsages = Set.of(
                new MuscleUsageDto("PECTORAL_MAJOR", "HIGH"),
                new MuscleUsageDto("TRICEPS", "MEDIUM")
        );
        ExerciseCandidate candidate = new ExerciseCandidate(
                "Push-up",
                "Basic push-up instructions",
                "BEGINNER",
                muscleUsages
        );

        facade.add(candidate);

        ExerciseCandidate candidate2 = new ExerciseCandidate(
                "Pull-up",
                "Pull-up instructions",
                "INTERMEDIATE",
                Set.of(new MuscleUsageDto("PECTORAL_MAJOR", "HIGH"))
        );
        facade.add(candidate2);

        mockMvc.perform(get("/api/vi/exercises/random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.instruction").isNotEmpty())
                .andExpect(jsonPath("$.difficultyLevel").isNotEmpty())
                .andExpect(jsonPath("$.muscleUsages.size()").value(greaterThan(0)));
    }

    @Test
    void shouldReturnSameExerciseWhenOnlyOneExerciseAvailable() throws Exception {
        Set<MuscleUsageDto> muscleUsages = Set.of(
                new MuscleUsageDto("PECTORAL_MAJOR", "HIGH")
        );
        ExerciseCandidate candidate = new ExerciseCandidate(
                "Push-up",
                "Basic push-up instructions",
                "BEGINNER",
                muscleUsages
        );

        facade.add(candidate);

        mockMvc.perform(get("/api/vi/exercises/random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Push-up"))
                .andExpect(jsonPath("$.instruction").value("Basic push-up instructions"))
                .andExpect(jsonPath("$.difficultyLevel").value("BEGINNER"))
                .andExpect(jsonPath("$.muscleUsages[0].muscle").value("PECTORAL_MAJOR"))
                .andExpect(jsonPath("$.muscleUsages[0].intensity").value("HIGH"));
    }

    @Test
    void shouldReturnBadRequestWhenNoExercisesAvailable() throws Exception {
        mockMvc.perform(get("/api/vi/exercises/random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Something went wrong. Cannot find any exercises"));
    }
}
