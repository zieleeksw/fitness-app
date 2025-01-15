package pl.global.exercises;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FindAvailableDifficultyLevelsIntegrationTest extends BaseIntegration {

    @Test
    void shouldReturnAllDifficultyLevels() throws Exception {

        mockMvc.perform(get("/api/vi/exercises/difficulty-levels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$").value(hasItems("BEGINNER", "INTERMEDIATE", "ADVANCED")));
    }

}
