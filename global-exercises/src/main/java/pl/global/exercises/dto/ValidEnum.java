package pl.global.exercises.dto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidEnumValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotBlank(message = "Must not be blank.")
@interface ValidEnum {

    Class<? extends Enum<?>> enumClazz();

    String message() default "Value is not valid. Allowed values: {allowedValues}.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

