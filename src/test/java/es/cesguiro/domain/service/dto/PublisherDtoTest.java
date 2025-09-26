package es.cesguiro.domain.service.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PublisherDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp(){
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator())
                .buildValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Create publisherDto with null name should throw exception")
    void createPublisherDto_WithNullName_ShouldThrowException() {
        PublisherDto publisherDto = new PublisherDto(1L, null, "slug");

        Set<ConstraintViolation<PublisherDto>> violations = validator.validate(publisherDto);

        assertFalse(violations.isEmpty());


    }

}