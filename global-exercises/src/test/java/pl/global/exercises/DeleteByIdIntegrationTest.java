package pl.global.exercises;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import pl.global.exercises.dto.ExerciseCandidate;
import pl.global.exercises.dto.ExerciseResponse;
import pl.global.exercises.dto.MuscleUsageDto;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteByIdIntegrationTest extends BaseIntegration {

    @Test
    void shouldReturnBadRequestOnNotExistingByUuidExercise() throws Exception {
        UUID uuid = UUID.randomUUID();

        String expectedError = String.format("Cannot find exercise with id: %s", uuid);

        mockMvc.perform(delete("/api/vi/exercises/{uuid}", uuid.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(expectedError));
    }

    @Test
    void shouldReturnStatusOkOnValidInput() throws Exception {
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

        UUID id = all.iterator().next().id();

        mockMvc.perform(delete("/api/vi/exercises/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertTrue(facade.findAll().isEmpty());
    }
}
