package pl.global.exercises.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

class NoSpecialCharactersOnlyValidator implements ConstraintValidator<NoSpecialCharactersOnly, String> {

    private static final String SPECIAL_CHARACTERS_REGEX = "^[^a-zA-Z0-9]*$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }

        return !value.matches(SPECIAL_CHARACTERS_REGEX);
    }
}
