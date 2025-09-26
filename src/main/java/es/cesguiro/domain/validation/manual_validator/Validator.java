package es.cesguiro.domain.validation.manual_validator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class Validator<T> {

    private List<String> errors = new ArrayList<>();

    public abstract List<String> validate(T t);

    protected void addError(String error) {
        errors.add(error);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }


    protected void notNull(T instance, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        Object value  = field.get(instance);
        if (value == null) {
            addError(field.getName() + " must not be null");
        }
    }


    // Other validation methods like notEmpty, maxLength, minLength, pattern, between can be added here
}
