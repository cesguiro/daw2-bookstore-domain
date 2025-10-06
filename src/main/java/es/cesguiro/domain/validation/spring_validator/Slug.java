package es.cesguiro.domain.validation.spring_validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SlugValidator.class)
public @interface Slug {
    String message() default "Valor inválido, debe ser URL-friendly (minúsculas, números y guiones)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
