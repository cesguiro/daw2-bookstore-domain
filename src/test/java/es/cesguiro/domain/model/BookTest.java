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

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(InstancioExtension.class)
class BookTest {

    @ParameterizedTest(name = "{index} => basePrice={0}, discountPercentage={1}, expectedPrice={2}")
    @DisplayName("Calculate final price with various discounts")
    @CsvSource({
            "100.00, 15.0, 85.00",
            "50.00, 0.0, 50.00",
            "75.00, 100.0, 0.00"
    })
    void calculateFinalPrice(String basePrice, String discountPercentage, String expectedPrice) {
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
        Instancio.fill(book);
        BigDecimal expected = new BigDecimal(expectedPrice).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, book.getPrice());

    }

    @Test
    @DisplayName("Test add Author to Book")
    void testAddAuthorToBook() {
        List<Author> authors = new ArrayList<>();
        authors.add(Instancio.of(InstancioModel.AUTHOR_MODEL).withSeed(39).create());
        Book book = Instancio.of(InstancioModel.BOOK_MODEL)
                .lenient() //para ignorar reglas de creación de modelos
                .set(field(Book::getAuthors), authors)
                .create();
        Author nonExistingAuthor = Instancio.of(InstancioModel.AUTHOR_MODEL).withSeed(48).create();
        book.addAuthor(nonExistingAuthor);
        assertTrue(book.getAuthors().contains(nonExistingAuthor), "Book should contain the added author");
    }

    @Test
    @DisplayName("Add existing Author to Book")
    void addExistingAuthorToBook() {
        List<Author> authors = new ArrayList<>();
        authors.add(Instancio.of(InstancioModel.AUTHOR_MODEL).withSeed(39).create());
        Book book = Instancio.of(InstancioModel.BOOK_MODEL)
                .lenient() //para ignorar reglas de creación de modelos
                .set(field(Book::getAuthors), authors)
                .create();
        Author existingAuthor = Instancio.of(InstancioModel.AUTHOR_MODEL).withSeed(39).create();
        assertThrows(BusinessException.class, () -> book.addAuthor(existingAuthor), "Adding an existing author should throw BusinessException");
    }

}