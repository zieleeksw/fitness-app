package pl.global.exercises;

import pl.global.exercises.domain.ExerciseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExerciseException.class)
    public ResponseEntity<String> handleExerciseException(ExerciseException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
