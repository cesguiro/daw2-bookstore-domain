package es.cesguiro.domain.service.dto;

import es.cesguiro.domain.exception.ValidationException;
import es.cesguiro.domain.validation.spring_validator.DtoValidator;
import es.cesguiro.util.InstancioModel;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

class AuthorDtoTest {

    @Test
    @DisplayName("Create AuthorDto with valid data should not throw ValidationException")
    void createAuthorDto_WithValidData_ShouldNotThrowException() {
        AuthorDto result = Instancio.of(InstancioModel.AUTHOR_DTO_MODEL).create();
        assertDoesNotThrow(() -> DtoValidator.validate(result));
    }

    static Stream<AuthorDto> invalidAuthors() {
        return Stream.of(
                Instancio.of(InstancioModel.AUTHOR_DTO_MODEL)
                        .ignore(field(AuthorDto::name))
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.AUTHOR_DTO_MODEL)
                        .set(field(AuthorDto::name), "")
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.AUTHOR_DTO_MODEL)
                        .set(field(AuthorDto::name), "   ")
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.AUTHOR_DTO_MODEL)
                        .ignore(field(AuthorDto::slug))
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.AUTHOR_DTO_MODEL)
                        .set(field(AuthorDto::slug), "")
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.AUTHOR_DTO_MODEL)
                        .set(field(AuthorDto::slug), "    ")
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.AUTHOR_DTO_MODEL)
                        .set(field(AuthorDto::slug), "invalid slug")
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.AUTHOR_DTO_MODEL)
                        .set(field(AuthorDto::slug), "invalid_slug!")
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.AUTHOR_DTO_MODEL)
                        .set(field(AuthorDto::slug), "aaa--bbb")
                        .lenient()
                        .create()
        );
    }

    @ParameterizedTest
    @MethodSource("invalidAuthors")
    @DisplayName("Create AuthorDto with invalid data should throw ValidationException")
    void shouldThrow_WhenAuthorDtoHasInvalidData(AuthorDto dto) {
        assertThrows(ValidationException.class, () -> DtoValidator.validate(dto));
    }
}