package es.cesguiro.domain.mapper;

import es.cesguiro.data.loader.BooksDataLoader;
import es.cesguiro.domain.exception.BusinessException;
import es.cesguiro.domain.mapper.BookMapper;
import es.cesguiro.domain.model.Book;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.service.dto.BookDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    private static List<Book> books;
    private static List<BookDto> bookDtos;
    private static List<BookEntity> bookEntities;

    @BeforeAll
    static void setUp() {
        BooksDataLoader booksDataLoader = new BooksDataLoader();
        books = booksDataLoader.loadBooksFromCSV();
        bookDtos = booksDataLoader.loadBookDtosFromCSV();
        bookEntities = booksDataLoader.loadBookEntitiesFromCSV();
    }

    @Test
    @DisplayName("Test map Book to BookDto")
    void toBookDto() {
        Book book = books.getFirst();
        BookDto result = BookMapper.getInstance().fromBookToBookDto(book);
        BookDto expected = bookDtos.getFirst();

        // Assert
        assertAll(
                () -> assertEquals(expected.isbn(), result.isbn(), "ISBN should match"),
                () -> assertEquals(expected.titleEs(), result.titleEs(), "TitleEs should match"),
                () -> assertEquals(expected.titleEn(), result.titleEn(), "TitleEn should match"),
                () -> assertEquals(expected.synopsisEs(), result.synopsisEs(), "SynopsisEs should match"),
                () -> assertEquals(expected.synopsisEn(), result.synopsisEn(), "SynopsisEn should match"),
                () -> assertEquals(expected.basePrice(), result.basePrice(), "BasePrice should match"),
                () -> assertEquals(expected.discountPercentage(), result.discountPercentage(), "DiscountPercentage should match"),
                () -> assertEquals(expected.cover(), result.cover(), "Cover should match"),
                () -> assertEquals(expected.publicationDate(), result.publicationDate(), "PublicationDate should match"),
                () -> assertNotNull(result.publisher(), "Publisher should not be null"),
                () -> assertEquals(expected.publisher().name(), result.publisher().name(), "Publisher name should match"),
                () -> assertEquals(expected.publisher().slug(), result.publisher().slug(), "Publisher slug should match"),
                () -> assertNotNull(result.authors(), "Authors should not be null"),
                () -> assertEquals(expected.authors().size(), result.authors().size(), "Authors size should match"),
                () -> assertEquals(expected.authors().getFirst().name(), result.authors().getFirst().name(), "First author name should match")
        );
    }

    @Test
    @DisplayName("Test map null Book to BookDto")
    void toBookDto_NullBook() {
        assertNull(BookMapper.getInstance().fromBookToBookDto(null), "Mapping null Book should return null BookDto");
    }

    @Test
    @DisplayName("Test map BookEntity to Book")
    void toBook() {
        // Arrange
        BookEntity bookEntity = bookEntities.getFirst();

        // Act
        Book result = BookMapper.getInstance().fromBookEntityToBook(bookEntity);
        Book expected = books.getFirst();

        // Assert
        assertAll(
                () -> assertEquals(expected.getIsbn(), result.getIsbn(), "ISBN should match"),
                () -> assertEquals(expected.getTitleEs(), result.getTitleEs(), "TitleEs should match"),
                () -> assertEquals(expected.getTitleEn(), result.getTitleEn(), "TitleEn should match"),
                () -> assertEquals(expected.getSynopsisEs(), result.getSynopsisEs(), "SynopsisEs should match"),
                () -> assertEquals(expected.getSynopsisEn(), result.getSynopsisEn(), "SynopsisEn should match"),
                () -> assertEquals(expected.getBasePrice(), result.getBasePrice(), "BasePrice should match"),
                () -> assertEquals(expected.getPrice(), result.getPrice(), "FinalPrice should match"),
                () -> assertEquals(expected.getDiscountPercentage(), result.getDiscountPercentage(), "DiscountPercentage should match"),
                () -> assertEquals(expected.getCover(), result.getCover(), "Cover should match"),
                () -> assertEquals(expected.getPublicationDate(), result.getPublicationDate(), "PublicationDate should match"),
                () -> assertNotNull(result.getPublisher(), "Publisher should not be null"),
                () -> assertEquals(expected.getPublisher().getName(), result.getPublisher().getName(), "Publisher name should match"),
                () -> assertEquals(expected.getPublisher().getSlug(), result.getPublisher().getSlug(), "Publisher slug should match"),
                () -> assertNotNull(result.getAuthors(), "Authors should not be null"),
                () -> assertEquals(expected.getAuthors().size(), result.getAuthors().size(), "Authors size should match"),
                () -> assertEquals(expected.getAuthors().getFirst().getName(), result.getAuthors().getFirst().getName(), "First author name should match")
        );
    }

}