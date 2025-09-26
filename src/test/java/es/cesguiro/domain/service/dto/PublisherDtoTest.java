package es.cesguiro.domain.service.dto;

import es.cesguiro.domain.validation.hibernate_validator.DtoValidator;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PublisherDtoTest {

    @Test
    @DisplayName("Create publisherDto with null name should throw exception")
    void createPublisherDto_WithNullName_ShouldThrowException() {
        PublisherDto publisherDto = new PublisherDto(1L, null, "slug");
        assertThrows(ConstraintViolationException.class, () -> DtoValidator.validate(publisherDto));

    }

}