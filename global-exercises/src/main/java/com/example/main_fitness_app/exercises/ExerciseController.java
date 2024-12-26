package com.example.main_fitness_app.exercises;


import com.example.main_fitness_app.exercises.dto.ExerciseCandidate;
import com.example.main_fitness_app.exercises.dto.ExerciseResponse;
import jakarta.validation.Valid;
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
    ResponseEntity<ExerciseResponse> add(@RequestBody @Valid ExerciseCandidate candidate) {
        ExerciseResponse exercise = facade.add(candidate);
        return ResponseEntity.ok(exercise);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        facade.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    ResponseEntity<Set<ExerciseResponse>> findByNameContaining(@RequestParam String name) {
        Set<ExerciseResponse> exercises = facade.findByNameContaining(name);
        return ResponseEntity.ok(exercises);
    }
}
