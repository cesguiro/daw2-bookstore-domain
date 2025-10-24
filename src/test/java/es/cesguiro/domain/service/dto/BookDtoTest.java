package es.cesguiro.domain.service.dto;

import es.cesguiro.domain.exception.ValidationException;
import es.cesguiro.domain.validation.spring_validator.DtoValidator;
import es.cesguiro.utils.TestDataFactory;
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

    private final static TestDataFactory testDataFactory = new TestDataFactory();
    private static final int SEED_VALUE = 0;

    private static final BookDto VALID_BOOK = testDataFactory.createBook(BookDto.class, false, SEED_VALUE);

    @Test
    @DisplayName("Create BookDto with valid data should not throw ValidationException")
    void createBookDto_WithValidData_ShouldNotThrowException() {
        assertDoesNotThrow(() -> DtoValidator.validate(VALID_BOOK));
    }

    static Stream<BookDto> invalidBooks() {
        return Stream.of(
                new BookDto(VALID_BOOK.id(), null, VALID_BOOK.titleEs(), VALID_BOOK.titleEn(), VALID_BOOK.synopsisEs(),
                        VALID_BOOK.synopsisEn(), VALID_BOOK.basePrice(), VALID_BOOK.discountPercentage(),
                        VALID_BOOK.price(), VALID_BOOK.cover(), VALID_BOOK.publicationDate(), null,
                        null),
                new BookDto(VALID_BOOK.id(), "123", VALID_BOOK.titleEs(), VALID_BOOK.titleEn(), VALID_BOOK.synopsisEs(),
                        VALID_BOOK.synopsisEn(), VALID_BOOK.basePrice(), VALID_BOOK.discountPercentage(),
                        VALID_BOOK.price(), VALID_BOOK.cover(), VALID_BOOK.publicationDate(), null,
                        null),
                new BookDto(VALID_BOOK.id(), VALID_BOOK.isbn(), VALID_BOOK.titleEs(), VALID_BOOK.titleEn(),
                        VALID_BOOK.synopsisEs(), VALID_BOOK.synopsisEn(), null, VALID_BOOK.discountPercentage(),
                        VALID_BOOK.price(), VALID_BOOK.cover(), VALID_BOOK.publicationDate(), null,
                        null),
                new BookDto(VALID_BOOK.id(), VALID_BOOK.isbn(), VALID_BOOK.titleEs(), VALID_BOOK.titleEn(),
                        VALID_BOOK.synopsisEs(), VALID_BOOK.synopsisEn(), VALID_BOOK.basePrice(), new BigDecimal("-5.0"),
                        VALID_BOOK.price(), VALID_BOOK.cover(), VALID_BOOK.publicationDate(), null,
                        null),
                new BookDto(VALID_BOOK.id(), VALID_BOOK.isbn(), VALID_BOOK.titleEs(), VALID_BOOK.titleEn(),
                        VALID_BOOK.synopsisEs(), VALID_BOOK.synopsisEn(), VALID_BOOK.basePrice(), new BigDecimal("150.0"),
                        VALID_BOOK.price(), VALID_BOOK.cover(), VALID_BOOK.publicationDate().plusDays(1),
                        null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidBooks")
    @DisplayName("Create BookDto with invalid data should throw ValidationException")
    void shouldThrow_WhenBookDtoHasInvalidData(BookDto dto) {
        assertThrows(ValidationException.class, () -> DtoValidator.validate(dto));
    }
}