package es.cesguiro.domain.model;

import es.cesguiro.domain.exception.BusinessException;
import es.cesguiro.util.InstancioModel;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(InstancioExtension.class)
class BookShould {

    @ParameterizedTest
    @CsvSource({
            "100.00, 15.0, 85.00",
            "50.00, 0.0, 50.00",
            "75.00, 100.0, 0.00"
    })
    void calculate_price_according_discount(String basePrice, String discountPercentage, String expectedPrice) {
        Book book = new Book(
                1L,
                null,
                null,
                null,
                null,
                null,
                new BigDecimal(basePrice),
                new BigDecimal(discountPercentage),
                null,
                null,
                null,
                List.of()
        );
        BigDecimal expected = new BigDecimal(expectedPrice).setScale(2, RoundingMode.HALF_UP);

        assertThat(book.getPrice()).isEqualByComparingTo(expected);
    }

    @Test
    void throw_exception_when_add_exist_author() {
        Book book = Instancio.of(InstancioModel.BOOK_MODEL).create();
        Author existingAuthor = book.getAuthors().getFirst();

        assertThrows(BusinessException.class, () -> book.addAuthor(existingAuthor), "Adding an existing author should throw BusinessException");
    }

}