package pl.global.exercises;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FindAvailableMusclesIntegrationTest extends BaseIntegration {

    @Test
    void shouldReturnAllAvailableMuscles() throws Exception {
        mockMvc.perform(get("/api/vi/exercises/muscles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(19))
                .andExpect(jsonPath("$").value(hasItems(
                        "BICEPS", "TRICEPS", "FOREARM",
                        "DELTOID_FRONT", "DELTOID_SIDE", "DELTOID_REAR",
                        "PECTORAL_MAJOR", "PECTORAL_MINOR",
                        "LATS", "TRAPEZIUS", "RHOMBOIDS", "ERECTOR_SPINAE",
                        "QUADRICEPS", "HAMSTRINGS", "CALVES", "GLUTES",
                        "ABDOMINALS", "OBLIQUES", "TRANSVERSUS_ABDOMINIS"
                )));
    }
}
