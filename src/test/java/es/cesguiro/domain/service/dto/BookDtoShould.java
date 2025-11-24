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


class BookDtoShould {


    @Test
    void validate_successfully_with_valid_data() {
        BookDto dto = Instancio.create(InstancioModel.BOOK_DTO_MODEL);
        assertDoesNotThrow(() -> DtoValidator.validate(dto));
    }

    @ParameterizedTest
    @MethodSource("invalidIsbn")
    void throw_exception_when_validate_with_invalid_isbn(String invalidIsbn) {
        BookDto dto = Instancio.of(BookDto.class)
                .set(field(BookDto::isbn), invalidIsbn)
                .create();

        assertThrows(ValidationException.class, () -> DtoValidator.validate(dto));
    }

    static Stream<String> invalidIsbn() {
        return Stream.of(null, "", "   ", "123", "invalid-isbn", "123456789012");
    }

    @ParameterizedTest
    @MethodSource("invalidBasePrices")
    void throw_exception_when_validate_with_invalid_base_price(BigDecimal invalidBasePrice) {
        BookDto dto = Instancio.of(BookDto.class)
                .set(field(BookDto::basePrice), invalidBasePrice)
                .create();

        assertThrows(ValidationException.class, () -> DtoValidator.validate(dto));
    }

    static Stream<BigDecimal> invalidBasePrices() {
        return Stream.of(null, new BigDecimal("-1.0"), new BigDecimal("-0.01"));
    }

    @ParameterizedTest
    @MethodSource("invalidDiscountPercentages")
    void throw_exception_when_validate_with_invalid_discount_percentage(BigDecimal invalidDiscountPercentage) {
        BookDto dto = Instancio.of(BookDto.class)
                .set(field(BookDto::discountPercentage), invalidDiscountPercentage)
                .create();

        assertThrows(ValidationException.class, () -> DtoValidator.validate(dto));
    }

    static Stream<BigDecimal> invalidDiscountPercentages() {
        return Stream.of(null, new BigDecimal("-1.0"), new BigDecimal("101.0"));
    }

    @ParameterizedTest
    @MethodSource("invalidPublicationDates")
    void throw_exception_when_validate_with_invalid_publication_date(java.time.LocalDate invalidPublicationDate) {
        BookDto dto = Instancio.of(BookDto.class)
                .set(field(BookDto::publicationDate), invalidPublicationDate)
                .create();

        assertThrows(ValidationException.class, () -> DtoValidator.validate(dto));
    }

    static Stream<java.time.LocalDate> invalidPublicationDates() {
        return Stream.of(java.time.LocalDate.now().plusDays(1), java.time.LocalDate.now().plusYears(1));
    }
}