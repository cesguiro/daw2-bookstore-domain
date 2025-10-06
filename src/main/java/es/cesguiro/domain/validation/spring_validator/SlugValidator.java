package es.cesguiro.domain.validation.spring_validator;

import jakarta.validation.ConstraintValidator;

public class SlugValidator implements ConstraintValidator<Slug, String> {

    private static final String SLUG_PATTERN = "^[a-z0-9]+(?:-[a-z0-9]+)*$";

    @Override
    public boolean isValid(String value, jakarta.validation.ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return value.matches(SLUG_PATTERN);
    }
}
