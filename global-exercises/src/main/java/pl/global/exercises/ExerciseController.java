package pl.global.exercises;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.global.exercises.domain.ExerciseFacade;
import pl.global.exercises.dto.ExerciseCandidate;
import pl.global.exercises.dto.ExerciseResponse;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/vi/exercises")
class ExerciseController {

    private final ExerciseFacade facade;

    ExerciseController(ExerciseFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    ExerciseResponse add(
            @RequestBody @Valid ExerciseCandidate candidate) {
        return facade.add(candidate);
    }

    @DeleteMapping("/{id}")
    @Transactional
    void deleteById(
            @PathVariable UUID id) {
        facade.deleteById(id);
    }

    @GetMapping("/search")
    Set<ExerciseResponse> findByNameContaining(
            @RequestParam @NotBlank @Size(max = 64) String name) {
        return facade.findByNameContaining(name);
    }

    @GetMapping
    Set<ExerciseResponse> findAll() {
        return facade.findAll();
    }

    @GetMapping("/random")
    ResponseEntity<ExerciseResponse> findRandom() {
        ExerciseResponse random = facade.findRandomExercise();
        return ResponseEntity.ok(random);
    }

    @GetMapping("/difficulty-levels")
    ResponseEntity<Set<String>> findAvailableDifficultyLevels() {
        Set<String> difficultyLevels = facade.findAvailableDifficultyLevels();
        return ResponseEntity.ok(difficultyLevels);
    }

    @GetMapping("/muscles")
    ResponseEntity<Set<String>> findAvailableMuscles() {
        Set<String> difficultyLevels = facade.findAvailableMuscles();
        return ResponseEntity.ok(difficultyLevels);
    }

    @GetMapping("/intensities")
    ResponseEntity<Set<String>> findAvailableIntensities() {
        Set<String> difficultyLevels = facade.findAvailableIntensities();
        return ResponseEntity.ok(difficultyLevels);
    }
}
