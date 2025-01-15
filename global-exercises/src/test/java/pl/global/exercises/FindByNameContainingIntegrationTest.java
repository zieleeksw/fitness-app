package pl.global.exercises;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import pl.global.exercises.dto.ExerciseCandidate;
import pl.global.exercises.dto.MuscleUsageDto;

import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FindByNameContainingIntegrationTest extends BaseIntegration {

    @Test
    void shouldReturnEmptySetWhenNoExercisesFound() throws Exception {
        String nonExistentName = "NonExistentExercise";

        mockMvc.perform(get("/api/vi/exercises/search")
                        .param("name", nonExistentName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldReturnBadRequestWhenNameIsBlank() throws Exception {
        mockMvc.perform(get("/api/vi/exercises/search")
                        .param("name", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenNameExceedsMaxLength() throws Exception {
        String longName = "a".repeat(65);

        mockMvc.perform(get("/api/vi/exercises/search")
                        .param("name", longName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnMatchingExercises() throws Exception {
        var muscleUsages = Set.of(
                new MuscleUsageDto("PECTORAL_MAJOR", "HIGH"),
                new MuscleUsageDto("TRICEPS", "MEDIUM")
        );

        ExerciseCandidate candidate1 = new ExerciseCandidate(
                "Push-up",
                "Basic push-up instructions",
                "BEGINNER",
                muscleUsages
        );

        ExerciseCandidate candidate2 = new ExerciseCandidate(
                "Pull-up",
                "Basic pull-up instructions",
                "INTERMEDIATE",
                muscleUsages
        );

        facade.add(candidate1);
        facade.add(candidate2);

        mockMvc.perform(get("/api/vi/exercises/search")
                        .param("name", "Push")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Push-up"));
    }
}

