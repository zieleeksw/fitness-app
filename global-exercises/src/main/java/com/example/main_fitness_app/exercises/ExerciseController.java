package com.example.main_fitness_app.exercises;


import com.example.main_fitness_app.exercises.domain.ExerciseFacade;
import com.example.main_fitness_app.exercises.dto.ExerciseCandidate;
import com.example.main_fitness_app.exercises.dto.ExerciseResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<ExerciseResponse> add(
            @RequestBody @Valid ExerciseCandidate candidate) {
        ExerciseResponse exercise = facade.add(candidate);
        return ResponseEntity.ok(exercise);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(
            @PathVariable @NotBlank UUID id) {
        facade.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    ResponseEntity<Set<ExerciseResponse>> findByNameContaining(
            @RequestParam @NotBlank @Size(max = 10) String name) {
        Set<ExerciseResponse> exercises = facade.findByNameContaining(name);
        return ResponseEntity.ok(exercises);
    }

    @GetMapping
    ResponseEntity<Set<ExerciseResponse>> findAll() {
        Set<ExerciseResponse> exercises = facade.findAll();
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/random")
    ResponseEntity<ExerciseResponse> findRandom() {
        ExerciseResponse random = facade.findRandomExercise();
        return ResponseEntity.ok(random);
    }

    // TODO: Repair the path and method name in facade and add a test for this one.
    @GetMapping("difficulty-levels")
    ResponseEntity<Set<String>> findDifficultyLevels() {
        Set<String> difficultyLevels = facade.findDifficultyLevels();
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
