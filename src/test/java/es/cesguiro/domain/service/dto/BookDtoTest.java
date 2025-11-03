package es.cesguiro.domain.service.dto;

import es.cesguiro.domain.exception.ValidationException;
import es.cesguiro.domain.validation.spring_validator.DtoValidator;
import es.cesguiro.util.InstancioModel;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


class BookDtoTest {


    @Test
    @DisplayName("Create BookDto with valid data should not throw ValidationException")
    void createBookDto_WithValidData_ShouldNotThrowException() {
        BookDto result = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .create();
        assertDoesNotThrow(() -> DtoValidator.validate(result));
    }

    static Stream<BookDto> invalidBooks() {
        return Stream.of(
                Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                        .ignore(field(BookDto::isbn))
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                        .set(field(BookDto::isbn), "123")
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                        .ignore(field(BookDto::basePrice))
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                        .set(field(BookDto::basePrice), new BigDecimal("-10.0"))
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                        .set(field(BookDto::discountPercentage), new BigDecimal("-5.0"))
                        .lenient()
                        .create(),
                Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                        .set(field(BookDto::discountPercentage), new BigDecimal("150.0"))
                        .lenient()
                        .create()
        );
    }

    @ParameterizedTest
    @MethodSource("invalidBooks")
    @DisplayName("Create BookDto with invalid data should throw ValidationException")
    void shouldThrow_WhenBookDtoHasInvalidData(BookDto dto) {
        assertThrows(ValidationException.class, () -> DtoValidator.validate(dto));
    }
}