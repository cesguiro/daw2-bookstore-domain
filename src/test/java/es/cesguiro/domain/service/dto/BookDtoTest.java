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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


class BookDtoTest {

    private static final BookDto VALID_BOOK = Instancio.of(BookDto.class)
            .generate(field(BookDto.class, "isbn"), gen -> gen.text().pattern("#d#d#d#d#d#d#d#d#d#d#d#d#d"))
            .generate(field(BookDto.class, "discountPercentage"), gen -> gen.doubles().range(0.0, 100.0))
            .generate(field(BookDto.class, "publicationDate"), gen -> gen.temporal().localDate().past())
            //.generate(field(BookDto::authors), gen -> gen.collection().size(3))
            //.setBlank(field(BookDto::publisher))
            .ignore(field(BookDto::publisher))
            .ignore(field(BookDto::authors))
            .create();

    @Test
    @DisplayName("Create BookDto with valid data should not throw ValidationException")
    void createBookDto_WithValidData_ShouldNotThrowException() {
        assertDoesNotThrow(() -> DtoValidator.validate(VALID_BOOK));
        System.out.println(VALID_BOOK);
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
                        VALID_BOOK.synopsisEs(), VALID_BOOK.synopsisEn(), VALID_BOOK.basePrice(), -5.0,
                        VALID_BOOK.price(), VALID_BOOK.cover(), VALID_BOOK.publicationDate(), null,
                        null),
                new BookDto(VALID_BOOK.id(), VALID_BOOK.isbn(), VALID_BOOK.titleEs(), VALID_BOOK.titleEn(),
                        VALID_BOOK.synopsisEs(), VALID_BOOK.synopsisEn(), VALID_BOOK.basePrice(), 105.0,
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