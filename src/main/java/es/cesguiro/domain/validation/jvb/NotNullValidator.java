package es.cesguiro.domain.validation.jvb;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotNullValidator implements ConstraintValidator<NotNull, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value != null;
    }
}
