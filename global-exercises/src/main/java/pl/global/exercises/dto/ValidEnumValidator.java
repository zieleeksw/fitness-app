package pl.global.exercises.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

class ValidEnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Set<String> allowedValues;
    private String message;

    @Override
    public void initialize(ValidEnum annotation) {
        allowedValues = Arrays.stream(annotation.enumClazz().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
        message = annotation.message().replace("{allowedValues}", String.join(", ", allowedValues));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        boolean isValid = allowedValues.contains(value);
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }
        return isValid;
    }
}