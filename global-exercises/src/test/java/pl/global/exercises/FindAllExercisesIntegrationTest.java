package pl.global.exercises;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import pl.global.exercises.dto.ExerciseCandidate;
import pl.global.exercises.dto.ExerciseResponse;
import pl.global.exercises.dto.MuscleUsageDto;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FindAllExercisesIntegrationTest extends BaseIntegration {

    @Test
    void shouldReturnAllExercises() throws Exception {
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

        Set<ExerciseResponse> all = facade.findAll();
        assertEquals(1, all.size());

        mockMvc.perform(get("/api/vi/exercises")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(all.size()))
                .andExpect(jsonPath("$[0].name").value("Push-up"))
                .andExpect(jsonPath("$[0].instruction").value("Basic push-up instructions"))
                .andExpect(jsonPath("$[0].difficultyLevel").value("BEGINNER"))
                .andExpect(jsonPath("$[0].muscleUsages[0].muscle").value("TRICEPS"))
                .andExpect(jsonPath("$[0].muscleUsages[0].intensity").value("MEDIUM"))
                .andExpect(jsonPath("$[0].muscleUsages[1].muscle").value("PECTORAL_MAJOR"))
                .andExpect(jsonPath("$[0].muscleUsages[1].intensity").value("HIGH"));
    }

    @Test
    void shouldReturnEmptyListWhenNoExercisesAvailable() throws Exception {
        mockMvc.perform(get("/api/vi/exercises")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
