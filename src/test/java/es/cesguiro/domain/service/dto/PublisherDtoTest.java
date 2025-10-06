package es.cesguiro.domain.service.dto;

import es.cesguiro.domain.exception.ValidationException;
import es.cesguiro.domain.validation.spring_validator.DtoValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PublisherDtoTest {



    @ParameterizedTest
    @DisplayName("Create publisherDto with invalid data should throw ValidationException")
    @CsvSource({
            "1L, '', 'valid-slug'",
            "1L, 'Valid Name', ''",
            "1L, '', ''",
            "1L, Valid Name, invalid slug",
            "1L, Valid Name, 'invalid_slug!'"
    })
    void createPublisherDto_WithNullName_ShouldThrowException() {
        PublisherDto publisherDto = new PublisherDto(1L, null, "slug");
        assertThrows(ValidationException.class, () -> DtoValidator.validate(publisherDto));

    }

}