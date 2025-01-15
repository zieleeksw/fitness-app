package pl.global.exercises;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FindAvailableIntensitiesIntegrationTest extends BaseIntegration {

    @Test
    void shouldReturnAllAvailableIntensities() throws Exception {

        mockMvc.perform(get("/api/vi/exercises/intensities")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$").value(hasItems(
                        "VERY_LOW", "LOW", "MEDIUM", "HIGH", "VERY_HIGH"
                )));
    }
}
