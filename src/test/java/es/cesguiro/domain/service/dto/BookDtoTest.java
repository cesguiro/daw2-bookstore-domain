package es.cesguiro.domain.service.dto;

import es.cesguiro.domain.exception.ValidationException;
import es.cesguiro.domain.validation.spring_validator.DtoValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;


class BookDtoTest {

    static Stream<Arguments> provideInvalidDataArguments() {
        return Stream.of(
                Arguments.of("", new BigDecimal("10.00"), 5.0, LocalDate.now()),
                Arguments.of("123", new BigDecimal("10.00"), 5.0, LocalDate.now()),
                Arguments.of("12345678901234", new BigDecimal("10.00"), 5.0, LocalDate.now()),
                Arguments.of("ABCDFGERTYTGG", new BigDecimal("10.00"), 5.0, LocalDate.now()),
                Arguments.of("9999999999999", null, 5.0, LocalDate.now()),
                Arguments.of("9999999999999", new BigDecimal("-10.00"), 5.0, LocalDate.now()),
                Arguments.of("9999999999999", new BigDecimal("10.00"), -5.0, LocalDate.now()),
                Arguments.of("9999999999999", new BigDecimal("10.00"), 105.0, LocalDate.now()),
                Arguments.of("9999999999999", new BigDecimal("10.00"), null, LocalDate.now()),
                Arguments.of("9999999999999", new BigDecimal("10.00"), 5.0, LocalDate.now().plusDays(1))
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidDataArguments")
    @DisplayName("createBook should throw exception when data is invalid")
    void createBook_ShouldThrowException_WhenDataIsInvalid(String isbn, BigDecimal basePrice, Double discount, LocalDate publicationDate) {
        BookDto invalidBookDto = new BookDto(
                null,
                isbn, // empty ISBN
                "Book Title ES",
                "Book Title EN",
                "Book Synopsis ES",
                "Book Synopsis EN",
                basePrice, // negative price
                discount,
                null,
                "url", // invalid URL
                publicationDate,
                null,
                List.of()
        );


        assertThrows(ValidationException.class, () -> DtoValidator.validate(invalidBookDto));
    }

}