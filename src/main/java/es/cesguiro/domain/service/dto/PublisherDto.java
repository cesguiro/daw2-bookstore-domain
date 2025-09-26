package es.cesguiro.domain.service.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PublisherDto(
        Long id,
        @NotNull
        String name,
        @NotNull
        @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "Slug inválido, debe ser URL-friendly (minúsculas, números y guiones)")
        String slug
) {
        public PublisherDto{
                ValidatorFactory factory = Validation.byDefaultProvider()
                        .configure()
                        .messageInterpolator(new org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator())
                        .buildValidatorFactory();
                Validator validator = factory.getValidator();

        }

}
