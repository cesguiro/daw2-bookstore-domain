package es.cesguiro.domain.service.dto;

import es.cesguiro.domain.exception.ValidationException;
import es.cesguiro.domain.validation.spring_validator.DtoValidator;
import es.cesguiro.utils.InstancioModel;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

class PublisherDtoTest {


    @Test
    @DisplayName("Create publisherDto with valid data should not throw ValidationException")
    void createPublisherDto_WithValidData_ShouldNotThrowException() {
        PublisherDto result = Instancio.of(InstancioModel.PUBLISHER_DTO_MODEL).create();
        assertDoesNotThrow(() -> DtoValidator.validate(result));
    }

    static Stream<PublisherDto> invalidPublishers() {
        return Stream.of(
                Instancio.of(InstancioModel.PUBLISHER_DTO_MODEL)
                        .ignore(field(PublisherDto::name))
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.PUBLISHER_DTO_MODEL)
                        .set(field(PublisherDto::name), "")
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.PUBLISHER_DTO_MODEL)
                        .set(field(PublisherDto::name), "   ")
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.PUBLISHER_DTO_MODEL)
                        .ignore(field(PublisherDto::slug))
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.PUBLISHER_DTO_MODEL)
                        .set(field(PublisherDto::slug), "")
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.PUBLISHER_DTO_MODEL)
                        .set(field(PublisherDto::slug), "    ")
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.PUBLISHER_DTO_MODEL)
                        .set(field(PublisherDto::slug), "invalid slug")
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.PUBLISHER_DTO_MODEL)
                        .set(field(PublisherDto::slug), "invalid_slug!")
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.PUBLISHER_DTO_MODEL)
                        .set(field(PublisherDto::slug), "sss--sss")
                        .lenient()
                        .create()
        );
    }
    @ParameterizedTest
    @MethodSource("invalidPublishers")
    @DisplayName("Create publisherDto with invalid data should throw ValidationException")
    void shouldThrow_WhenPublisherDtoHasInvalidData(PublisherDto dto) {
        assertThrows(ValidationException.class, () -> DtoValidator.validate(dto));
    }

}