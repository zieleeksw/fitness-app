package pl.global.exercises;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SaveExerciseIntegrationTest extends BaseIntegration {

    @Test
    void shouldReturnBadRequestWhenNameIsMissing() throws Exception {
        String candidateJson = """
                {
                    "instruction": "Basic push-up instructions",
                    "difficultyLevel": "BEGINNER",
                    "muscleUsages": [
                        {"muscle": "TRICEPS", "intensity": "MEDIUM"},
                        {"muscle": "PECTORAL_MAJOR", "intensity": "HIGH"}
                    ]
                }
                """;

        mockMvc.perform(post("/api/vi/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Must not be blank."));
    }

    @Test
    void shouldReturnBadRequestWhenNameExceedsMaxLength() throws Exception {
        String longName = "A".repeat(65);

        String candidateJson = String.format("""
                {
                    "name": "%s",
                    "instruction": "Basic push-up instructions",
                    "difficultyLevel": "BEGINNER",
                    "muscleUsages": [
                        {"muscle": "TRICEPS", "intensity": "MEDIUM"},
                        {"muscle": "PECTORAL_MAJOR", "intensity": "HIGH"}
                    ]
                }
                """, longName);

        mockMvc.perform(post("/api/vi/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Must be at most 64 characters long."));
    }

    @Test
    void shouldReturnBadRequestWhenNameContainsOnlySpecialCharacters() throws Exception {
        String candidateJson = """
                {
                    "name": "@#$%^",
                    "instruction": "Basic push-up instructions",
                    "difficultyLevel": "BEGINNER",
                    "muscleUsages": [
                        {"muscle": "TRICEPS", "intensity": "MEDIUM"},
                        {"muscle": "PECTORAL_MAJOR", "intensity": "HIGH"}
                    ]
                }
                """;

        mockMvc.perform(post("/api/vi/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Cannot contain only special characters."));
    }

    @Test
    void shouldReturnBadRequestWhenInstructionIsMissing() throws Exception {
        String candidateJson = """
                {
                    "name": "Push-up",
                    "difficultyLevel": "BEGINNER",
                    "muscleUsages": [
                        {"muscle": "TRICEPS", "intensity": "MEDIUM"},
                        {"muscle": "PECTORAL_MAJOR", "intensity": "HIGH"}
                    ]
                }
                """;

        mockMvc.perform(post("/api/vi/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.instruction").value("Must not be blank."));
    }

    @Test
    void shouldReturnBadRequestWhenInstructionExceedsMaxLength() throws Exception {
        String longInstruction = "A".repeat(129);
        String candidateJson = String.format("""
                {
                    "name": "Push-up",
                    "instruction": "%s",
                    "difficultyLevel": "BEGINNER",
                    "muscleUsages": [
                        {"muscle": "TRICEPS", "intensity": "MEDIUM"},
                        {"muscle": "PECTORAL_MAJOR", "intensity": "HIGH"}
                    ]
                }
                """, longInstruction);

        mockMvc.perform(post("/api/vi/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.instruction").value("Must be at most 128 characters long."));
    }

    @Test
    void shouldReturnBadRequestWhenInstructionContainsOnlySpecialCharacters() throws Exception {
        String candidateJson = """
                {
                    "name": "Push-up",
                    "instruction": "@#$%^&*",
                    "difficultyLevel": "BEGINNER",
                    "muscleUsages": [
                        {"muscle": "TRICEPS", "intensity": "MEDIUM"},
                        {"muscle": "PECTORAL_MAJOR", "intensity": "HIGH"}
                    ]
                }
                """;

        mockMvc.perform(post("/api/vi/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.instruction").value("Cannot contain only special characters."));
    }

    @Test
    void shouldReturnBadRequestWhenDifficultyLevelIsMissing() throws Exception {
        String candidateJson = """
                {
                    "name": "Push-up",
                    "instruction": "Basic push-up instructions",
                    "muscleUsages": [
                        {"muscle": "TRICEPS", "intensity": "MEDIUM"},
                        {"muscle": "PECTORAL_MAJOR", "intensity": "HIGH"}
                    ]
                }
                """;

        mockMvc.perform(post("/api/vi/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.difficultyLevel").value("Must not be blank."));
    }

    @Test
    void shouldReturnBadRequestWhenDifficultyLevelIsInvalid() throws Exception {
        String invalidLevel = "EXPERT";
        String candidateJson = String.format("""
                {
                    "name": "Push-up",
                    "instruction": "Basic push-up instructions",
                    "difficultyLevel": "%s",
                    "muscleUsages": [
                        {"muscle": "TRICEPS", "intensity": "MEDIUM"},
                        {"muscle": "PECTORAL_MAJOR", "intensity": "HIGH"}
                    ]
                }
                """, invalidLevel);

        mockMvc.perform(post("/api/vi/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.difficultyLevel").value("Value is not valid. Allowed values: INTERMEDIATE, BEGINNER, ADVANCED."));
    }

    @Test
    void shouldReturnBadRequestWhenMuscleIsInvalid() throws Exception {
        String candidateJson = """
                {
                    "name": "Push-up",
                    "instruction": "Basic push-up instructions",
                    "difficultyLevel": "BEGINNER",
                    "muscleUsages": [
                        {"muscle": "INVALID_MUSCLE", "intensity": "MEDIUM"}
                    ]
                }
                """;

        mockMvc.perform(post("/api/vi/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$['muscleUsages[].muscle']").value(
                        "Value is not valid. Allowed values: FOREARM, DELTOID_REAR, TRAPEZIUS, TRANSVERSUS_ABDOMINIS, BICEPS, HAMSTRINGS, GLUTES, TRICEPS, DELTOID_FRONT, RHOMBOIDS, DELTOID_SIDE, OBLIQUES, PECTORAL_MAJOR, PECTORAL_MINOR, ERECTOR_SPINAE, ABDOMINALS, LATS, QUADRICEPS, CALVES."
                ));
    }

    @Test
    void shouldReturnBadRequestWhenIntensityIsInvalid() throws Exception {
        String candidateJson = """
                {
                    "name": "Push-up",
                    "instruction": "Basic push-up instructions",
                    "difficultyLevel": "BEGINNER",
                    "muscleUsages": [
                        {"muscle": "TRICEPS", "intensity": "INVALID_INTENSITY"}
                    ]
                }
                """;
        mockMvc.perform(post("/api/vi/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$['muscleUsages[].intensity']").value(
                        "Value is not valid. Allowed values: VERY_HIGH, HIGH, MEDIUM, LOW, VERY_LOW."
                ));
    }

    @Test
    void shouldReturnBadRequestWhenMuscleUsageListIsEmpty() throws Exception {
        String candidateJson = """
                {
                    "name": "Push-up",
                    "instruction": "Basic push-up instructions",
                    "difficultyLevel": "BEGINNER",
                    "muscleUsages": []
                }
                """;

        mockMvc.perform(post("/api/vi/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.muscleUsages").value("Must not be empty."));
    }

    @Test
    void shouldReturnBadRequestOnAlreadyExistingExercise() throws Exception {
        String candidateJson = """
                {
                    "name": "Push-up",
                    "instruction": "Basic push-up instructions",
                    "difficultyLevel": "BEGINNER",
                    "muscleUsages": [
                        {"muscle": "TRICEPS", "intensity": "MEDIUM"},
                        {"muscle": "PECTORAL_MAJOR", "intensity": "HIGH"}
                    ]
                }
                """;
        mockMvc.perform(post("/api/vi/exercises")
                .contentType(MediaType.APPLICATION_JSON)
                .content(candidateJson));

        mockMvc.perform(post("/api/vi/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Exercise already exists"));
    }

    @Test
    void shouldReturnStatusOkOnValidInput() throws Exception {
        String candidateJson = """
                {
                    "name": "Push-up",
                    "instruction": "Basic push-up instructions",
                    "difficultyLevel": "BEGINNER",
                    "muscleUsages": [
                        {"muscle": "TRICEPS", "intensity": "MEDIUM"},
                        {"muscle": "PECTORAL_MAJOR", "intensity": "HIGH"}
                    ]
                }
                """;
        mockMvc.perform(post("/api/vi/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Push-up"))
                .andExpect(jsonPath("$.instruction").value("Basic push-up instructions"))
                .andExpect(jsonPath("$.difficultyLevel").value("BEGINNER"))
                .andExpect(jsonPath("$.muscleUsages[0].muscle").value("TRICEPS"))
                .andExpect(jsonPath("$.muscleUsages[0].intensity").value("MEDIUM"))
                .andExpect(jsonPath("$.muscleUsages[0].muscle").value("PECTORAL_MAJOR"))
                .andExpect(jsonPath("$.muscleUsages[0].intensity").value("HIGH"));
    }
}
