package es.cesguiro.domain.service.dto;

import es.cesguiro.domain.exception.ValidationException;
import es.cesguiro.domain.validation.spring_validator.DtoValidator;
import es.cesguiro.util.InstancioModel;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

class PublisherDtoShould {

    @Test
    void validate_successfully_with_valid_data() {
        PublisherDto dto = Instancio.of(InstancioModel.PUBLISHER_DTO_MODEL).create();
        assertDoesNotThrow(() -> DtoValidator.validate(dto));
    }

    @ParameterizedTest
    @MethodSource("invalidSlugs")
    void throw_exception_when_validate_with_invalid_slug(String invalidSlug) {
        PublisherDto dto = Instancio.of(PublisherDto.class)
                .set(field(PublisherDto::slug), invalidSlug)
                .create();

        assertThrows(ValidationException.class, () -> DtoValidator.validate(dto));
    }

    static Stream<String> invalidSlugs() {
        return Stream.of(null, "", "invalid slug!", "Invalid-Slug", "invalid_slug");
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void throw_exception_when_validate_with_invalid_name(String invalidName) {
        PublisherDto dto = Instancio.of(PublisherDto.class)
                .set(field(PublisherDto::name), invalidName)
                .create();

        assertThrows(ValidationException.class, () -> DtoValidator.validate(dto));
    }

    static Stream<String> invalidNames() {
        return Stream.of(null, "", "   ");
    }
}