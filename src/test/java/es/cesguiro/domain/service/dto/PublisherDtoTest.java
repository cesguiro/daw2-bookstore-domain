package es.cesguiro.domain.service.dto;

import es.cesguiro.domain.exception.ValidationException;
import es.cesguiro.domain.validation.spring_validator.DtoValidator;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

class PublisherDtoTest {

    private static final PublisherDto VALID_PUBLISHER = Instancio.of(PublisherDto.class)
            .generate(field(PublisherDto.class,"slug"), gen -> gen.text().pattern("#c#c#c-#c#c#c"))
            .create();

    @Test
    @DisplayName("Create publisherDto with valid data should not throw ValidationException")
    void createPublisherDto_WithValidData_ShouldNotThrowException() {
        assertDoesNotThrow(() -> DtoValidator.validate(VALID_PUBLISHER));
    }

    static Stream<PublisherDto> invalidPublishers() {
        return Stream.of(
                new PublisherDto(VALID_PUBLISHER.id(), null, VALID_PUBLISHER.slug()),
                new PublisherDto(VALID_PUBLISHER.id(), "", VALID_PUBLISHER.slug()),
                new PublisherDto(VALID_PUBLISHER.id(), VALID_PUBLISHER.name(), null),
                new PublisherDto(VALID_PUBLISHER.id(), VALID_PUBLISHER.name(), ""),
                new PublisherDto(VALID_PUBLISHER.id(), VALID_PUBLISHER.name(), "  "),
                new PublisherDto(VALID_PUBLISHER.id(), VALID_PUBLISHER.name(), "invalid slug"),
                new PublisherDto(VALID_PUBLISHER.id(), VALID_PUBLISHER.name(), "invalid_slug!"),
                new PublisherDto(VALID_PUBLISHER.id(), VALID_PUBLISHER.name(), "sss--sss")
        );
    }
    @ParameterizedTest
    @MethodSource("invalidPublishers")
    @DisplayName("Create publisherDto with invalid data should throw ValidationException")
    void shouldThrow_WhenPublisherDtoHasInvalidData(PublisherDto dto) {
        assertThrows(ValidationException.class, () -> DtoValidator.validate(dto));
    }

}