package es.cesguiro.domain.service.dto;

import es.cesguiro.domain.exception.ValidationException;
import es.cesguiro.domain.validation.spring_validator.DtoValidator;
import es.cesguiro.utils.TestDataFactory;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

class AuthorDtoTest {

    private static final TestDataFactory testDataFactory = new TestDataFactory();
    private static final int SEED_VALUE = 0;

    private static final AuthorDto VALID_AUTHOR = testDataFactory.createAuthor(AuthorDto.class, SEED_VALUE);

    @Test
    @DisplayName("Create AuthorDto with valid data should not throw ValidationException")
    void createAuthorDto_WithValidData_ShouldNotThrowException() {
        assertDoesNotThrow(() -> DtoValidator.validate(VALID_AUTHOR));
    }

    static Stream<AuthorDto> invalidAuthors() {
        return Stream.of(
                new AuthorDto(VALID_AUTHOR.id(), null, VALID_AUTHOR.nationality(),
                        VALID_AUTHOR.biographyEs(), VALID_AUTHOR.biographyEn(),
                        VALID_AUTHOR.birthYear(), VALID_AUTHOR.deathYear(),
                        VALID_AUTHOR.slug()),
                new AuthorDto(VALID_AUTHOR.id(), "", VALID_AUTHOR.nationality(),
                        VALID_AUTHOR.biographyEs(), VALID_AUTHOR.biographyEn(),
                        VALID_AUTHOR.birthYear(), VALID_AUTHOR.deathYear(),
                        VALID_AUTHOR.slug()),
                new AuthorDto(VALID_AUTHOR.id(), VALID_AUTHOR.name(), VALID_AUTHOR.nationality(),
                        VALID_AUTHOR.biographyEs(), VALID_AUTHOR.biographyEn(),
                        VALID_AUTHOR.birthYear(), VALID_AUTHOR.deathYear(),
                        null),
                new AuthorDto(VALID_AUTHOR.id(), VALID_AUTHOR.name(), VALID_AUTHOR.nationality(),
                        VALID_AUTHOR.biographyEs(), VALID_AUTHOR.biographyEn(),
                        VALID_AUTHOR.birthYear(), VALID_AUTHOR.deathYear(),
                        ""),
                new AuthorDto(VALID_AUTHOR.id(), VALID_AUTHOR.name(), VALID_AUTHOR.nationality(),
                        VALID_AUTHOR.biographyEs(), VALID_AUTHOR.biographyEn(),
                        VALID_AUTHOR.birthYear(), VALID_AUTHOR.deathYear(),
                        "    "),
                new AuthorDto(VALID_AUTHOR.id(), VALID_AUTHOR.name(), VALID_AUTHOR.nationality(),
                        VALID_AUTHOR.biographyEs(), VALID_AUTHOR.biographyEn(),
                        VALID_AUTHOR.birthYear(), VALID_AUTHOR.deathYear(),
                        "invalid slug"),
                new AuthorDto(VALID_AUTHOR.id(), VALID_AUTHOR.name(), VALID_AUTHOR.nationality(),
                        VALID_AUTHOR.biographyEs(), VALID_AUTHOR.biographyEn(),
                        VALID_AUTHOR.birthYear(), VALID_AUTHOR.deathYear(),
                        "invalid_slug!"),
                new AuthorDto(VALID_AUTHOR.id(), VALID_AUTHOR.name(), VALID_AUTHOR.nationality(),
                        VALID_AUTHOR.biographyEs(), VALID_AUTHOR.biographyEn(),
                        VALID_AUTHOR.birthYear(), VALID_AUTHOR.deathYear(),
                        "aa--bb")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidAuthors")
    @DisplayName("Create AuthorDto with invalid data should throw ValidationException")
    void shouldThrow_WhenAuthorDtoHasInvalidData(AuthorDto dto) {
        assertThrows(ValidationException.class, () -> DtoValidator.validate(dto));
    }
}