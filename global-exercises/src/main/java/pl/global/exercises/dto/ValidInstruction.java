package pl.global.exercises.dto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidInstructionValidator.class)

@NotBlank(message = "Must not be blank.")
@Size(message = "Must be at most 128 characters long.", max = 64)
@NoSpecialCharactersOnly
@interface ValidInstruction {
    String message() default "Something went wrong with exercise instruction. Please try again.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}