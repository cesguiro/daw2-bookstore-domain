package es.cesguiro.domain.model;

import es.cesguiro.data.loader.AuthorsDataLoader;
import es.cesguiro.data.loader.BooksDataLoader;
import es.cesguiro.domain.exception.BusinessException;
import es.cesguiro.domain.model.Author;
import es.cesguiro.domain.model.Book;
import es.cesguiro.utils.TestDataFactory;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {


    private static final TestDataFactory testDataFactory = new TestDataFactory();

    @ParameterizedTest(name = "{index} => basePrice={0}, discountPercentage={1}, expectedPrice={2}")
    @DisplayName("Calculate final price with various discounts")
    @CsvSource({
            "100.00, 15.0, 85.00",
            "50.00, 0.0, 50.00",
            "75.00, 100.0, 0.00"
    })
    void calculateFinalPrice(String basePrice, double discountPercentage, String expectedPrice) {
        Book book = new Book(
                1L,
                "9999999999999",
                "Título en Español",
                "Title in English",
                "Sinopsis en Español",
                "Synopsis in English",
                new BigDecimal(basePrice),
                discountPercentage,
                "cover.jpg",
                LocalDate.of(2023, 1, 1),
                null,
                List.of()
        );
        BigDecimal expected = new BigDecimal(expectedPrice).setScale(2, java.math.RoundingMode.HALF_UP);
        assertEquals(expected, book.getPrice());
    }

    private Book createBookWithAuthors(Author author) {
        Book base = testDataFactory.createBook(Book.class, false);
        return new Book(
                base.getId(),
                base.getIsbn(),
                base.getTitleEs(),
                base.getTitleEn(),
                base.getSynopsisEs(),
                base.getSynopsisEn(),
                base.getBasePrice(),
                base.getDiscountPercentage(),
                base.getCover(),
                base.getPublicationDate(),
                null,
                List.of(author)
        );
    }

    @Test
    @DisplayName("Test add Author to Book")
    void testAddAuthorToBook() {
        Author author1 = testDataFactory.createAuthor(Author.class);
        Book book = createBookWithAuthors(author1);
        Author author2 = testDataFactory.createAuthor(Author.class);
        book.addAuthor(author2);
        assertTrue(book.getAuthors().contains(author2), "Book should contain the added author");
    }

    @Test
    @DisplayName("Add existing Author to Book")
    void addExistingAuthorToBook() {
        Author author = testDataFactory.createAuthor(Author.class);
        Book book = createBookWithAuthors(author);
        assertThrows(BusinessException.class, () -> book.addAuthor(author));
    }

}