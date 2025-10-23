package es.cesguiro.domain.mapper;

import es.cesguiro.domain.model.Author;
import es.cesguiro.domain.model.Book;
import es.cesguiro.domain.model.Publisher;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.domain.service.dto.AuthorDto;
import es.cesguiro.domain.service.dto.BookDto;
import es.cesguiro.domain.service.dto.PublisherDto;
import es.cesguiro.utils.TestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    private final static TestDataFactory testDataFactory = new TestDataFactory(null);

    private static BookEntity buildBookEntity(PublisherEntity publisher, List<AuthorEntity> authors) {
        BookEntity base = testDataFactory.of(BookEntity.class);
        return new BookEntity(
                base.id(), base.isbn(), base.titleEs(), base.titleEn(),
                base.synopsisEs(), base.synopsisEn(), base.basePrice(),
                base.discountPercentage(), base.cover(),
                base.publicationDate(), publisher, authors);
    }

    static Stream<BookEntity> validBookEntities() {
        return Stream.of(
                testDataFactory.of(BookEntity.class),
                buildBookEntity(null, null),
                buildBookEntity(testDataFactory.of(PublisherEntity.class), null),
                buildBookEntity(null, testDataFactory.ofList(AuthorEntity.class, 1)),
                buildBookEntity(testDataFactory.of(PublisherEntity.class), testDataFactory.ofList(AuthorEntity.class, 1)),
                buildBookEntity(testDataFactory.of(PublisherEntity.class), testDataFactory.ofList(AuthorEntity.class, 3))
        );
    }


    @ParameterizedTest
    @MethodSource("validBookEntities")
    @DisplayName("Map BookEntity to Book should return correct Book")
    void fromBookEntityToBookTest(BookEntity bookEntity) {
        var result = BookMapper.getInstance().fromBookEntityToBook(bookEntity);

        assertAll(
                () -> assertEquals(bookEntity.id(), result.getId(), "ID should match"),
                () -> assertEquals(bookEntity.isbn(), result.getIsbn(), "ISBN should match"),
                () -> assertEquals(bookEntity.titleEs(), result.getTitleEs(), "TitleEs should match"),
                () -> assertEquals(bookEntity.titleEn(), result.getTitleEn(), "TitleEn should match"),
                () -> assertEquals(bookEntity.synopsisEs(), result.getSynopsisEs(), "SynopsisEs should match"),
                () -> assertEquals(bookEntity.synopsisEn(), result.getSynopsisEn(), "SynopsisEn should match"),
                () -> assertEquals(bookEntity.basePrice(), result.getBasePrice(), "BasePrice should match"),
                () -> assertEquals(bookEntity.discountPercentage(), result.getDiscountPercentage(), "DiscountPercentage should match"),
                () -> assertEquals(bookEntity.cover(), result.getCover(), "Cover should match"),
                () -> assertEquals(bookEntity.publicationDate(), result.getPublicationDate(), "PublicationDate should match")
        );
        if (bookEntity.publisher() != null) {
            assertEquals(bookEntity.publisher().id(), result.getPublisher().getId(), "Publisher ID should match");
        } else {
            assertNull(result.getPublisher(), "Publisher should be null");
        }
        if (bookEntity.authors() != null) {
            assertAll(
                    () -> assertEquals(bookEntity.authors().size(), result.getAuthors().size(), "Authors size should match"),
                    () -> {
                        for (int i = 0; i < bookEntity.authors().size(); i++) {
                            assertEquals(bookEntity.authors().get(i).id(), result.getAuthors().get(i).getId(), "Author ID should match at index " + i);
                        }
                    }
            );
        } else {
            assertEquals(List.of(), result.getAuthors(), "Authors should be empty");
        }
    }

    private static Book buildBook(Publisher publisher, List<Author> authors) {
        Book base = testDataFactory.of(Book.class);
        return new Book(
                base.getId(), base.getIsbn(), base.getTitleEs(), base.getTitleEn(),
                base.getSynopsisEs(), base.getSynopsisEn(), base.getBasePrice(),
                base.getDiscountPercentage(), base.getCover(),
                base.getPublicationDate(), publisher, authors);
    }

    static Stream<Book> validBooks() {
        return Stream.of(
                buildBook(null, null),
                buildBook(testDataFactory.of(Publisher.class), null),
                buildBook(null, testDataFactory.ofList(Author.class, 1)),
                buildBook(testDataFactory.of(Publisher.class), testDataFactory.ofList(Author.class, 1)),
                buildBook(testDataFactory.of(Publisher.class), testDataFactory.ofList(Author.class, 3))
        );
    }

    @ParameterizedTest
    @MethodSource("validBooks")
    @DisplayName("Map Book to BookEntity should return correct BookEntity")
    void fromBookToBookEntityTest(Book book) {
        var result = BookMapper.getInstance().fromBookToBookEntity(book);
        assertAll(
                () -> assertEquals(book.getId(), result.id(), "ID should match"),
                () -> assertEquals(book.getIsbn(), result.isbn(), "ISBN should match"),
                () -> assertEquals(book.getTitleEs(), result.titleEs(), "TitleEs should match"),
                () -> assertEquals(book.getTitleEn(), result.titleEn(), "TitleEn should match"),
                () -> assertEquals(book.getSynopsisEs(), result.synopsisEs(), "SynopsisEs should match"),
                () -> assertEquals(book.getSynopsisEn(), result.synopsisEn(), "SynopsisEn should match"),
                () -> assertEquals(book.getBasePrice(), result.basePrice(), "BasePrice should match"),
                () -> assertEquals(book.getDiscountPercentage(), result.discountPercentage(), "DiscountPercentage should match"),
                () -> assertEquals(book.getCover(), result.cover(), "Cover should match"),
                () -> assertEquals(book.getPublicationDate(), result.publicationDate(), "PublicationDate should match")
        );
        if (book.getPublisher() != null) {
            assertEquals(book.getPublisher().getId(), result.publisher().id(), "Publisher ID should match");
        } else {
            assertNull(result.publisher(), "Publisher should be null");
        }
        if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            assertAll(
                    () -> assertEquals(book.getAuthors().size(), result.authors().size(), "Authors size should match"),
                    () -> {
                        for (int i = 0; i < book.getAuthors().size(); i++) {
                            assertEquals(book.getAuthors().get(i).getId(), result.authors().get(i).id(), "Author ID should match at index " + i);
                        }
                    }
            );
        } else {
            assertNull(result.authors(), "Authors should be null");
        }
    }

    @ParameterizedTest
    @MethodSource("validBooks")
    @DisplayName("Mapping Book to BookDto should return correct BookDto")
    void fromBookToBookDtoTest(Book book) {
        var result = BookMapper.getInstance().fromBookToBookDto(book);
        assertAll(
                () -> assertEquals(book.getId(), result.id(), "ID should match"),
                () -> assertEquals(book.getIsbn(), result.isbn(), "ISBN should match"),
                () -> assertEquals(book.getTitleEs(), result.titleEs(), "TitleEs should match"),
                () -> assertEquals(book.getTitleEn(), result.titleEn(), "TitleEn should match"),
                () -> assertEquals(book.getSynopsisEs(), result.synopsisEs(), "SynopsisEs should match"),
                () -> assertEquals(book.getSynopsisEn(), result.synopsisEn(), "SynopsisEn should match"),
                () -> assertEquals(book.getBasePrice(), result.basePrice(), "BasePrice should match"),
                () -> assertEquals(book.getDiscountPercentage(), result.discountPercentage(), "DiscountPercentage should match"),
                () -> assertEquals(book.calculateFinalPrice(), result.price(), "Price should match"),
                () -> assertEquals(book.getCover(), result.cover(), "Cover should match"),
                () -> assertEquals(book.getPublicationDate(), result.publicationDate(), "PublicationDate should match")
        );
        if (book.getPublisher() != null) {
            assertEquals(book.getPublisher().getId(), result.publisher().id(), "Publisher ID should match");
        } else {
            assertNull(result.publisher(), "Publisher should be null");
        }
        if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            assertAll(
                    () -> assertEquals(book.getAuthors().size(), result.authors().size(), "Authors size should match"),
                    () -> {
                        for (int i = 0; i < book.getAuthors().size(); i++) {
                            assertEquals(book.getAuthors().get(i).getId(), result.authors().get(i).id(), "Author ID should match at index " + i);
                        }
                    }
            );
        } else {
            assertEquals(List.of(), result.authors(), "Authors should be empty");
        }
    }

    private static BookDto buildBookDto(PublisherDto publisher, List<AuthorDto> authors) {
        BookDto base = testDataFactory.of(BookDto.class);
        return new BookDto(
                base.id(), base.isbn(), base.titleEs(), base.titleEn(),
                base.synopsisEs(), base.synopsisEn(), base.basePrice(),
                base.discountPercentage(), base.price(),
                base.cover(), base.publicationDate(), publisher, authors);
    }

    static Stream<BookDto> validBookDtos() {
        return Stream.of(
                buildBookDto(null, null),
                buildBookDto(testDataFactory.of(PublisherDto.class), null),
                buildBookDto(null, testDataFactory.ofList(AuthorDto.class, 1)),
                buildBookDto(testDataFactory.of(PublisherDto.class), testDataFactory.ofList(AuthorDto.class, 1)),
                buildBookDto(testDataFactory.of(PublisherDto.class), testDataFactory.ofList(AuthorDto.class, 3))
        );
    }

    @ParameterizedTest
    @MethodSource("validBookDtos")
    @DisplayName("Map BookDto to Book should return correct Book")
    void fromBookDtoToBookTest(BookDto bookDto) {
        var result = BookMapper.getInstance().fromBookDtoToBook(bookDto);
        assertAll(
                () -> assertEquals(bookDto.id(), result.getId(), "ID should match"),
                () -> assertEquals(bookDto.isbn(), result.getIsbn(), "ISBN should match"),
                () -> assertEquals(bookDto.titleEs(), result.getTitleEs(), "TitleEs should match"),
                () -> assertEquals(bookDto.titleEn(), result.getTitleEn(), "TitleEn should match"),
                () -> assertEquals(bookDto.synopsisEs(), result.getSynopsisEs(), "SynopsisEs should match"),
                () -> assertEquals(bookDto.synopsisEn(), result.getSynopsisEn(), "SynopsisEn should match"),
                () -> assertEquals(bookDto.basePrice(), result.getBasePrice(), "BasePrice should match"),
                () -> assertEquals(bookDto.discountPercentage(), result.getDiscountPercentage(), "DiscountPercentage should match"),
                () -> assertEquals(bookDto.cover(), result.getCover(), "Cover should match"),
                () -> assertEquals(bookDto.publicationDate(), result.getPublicationDate(), "PublicationDate should match")
        );
        if (bookDto.publisher() != null) {
            assertEquals(bookDto.publisher().id(), result.getPublisher().getId(), "Publisher ID should match");
        } else {
            assertNull(result.getPublisher(), "Publisher should be null");
        }
        if (!bookDto.authors().isEmpty()) {
            assertAll(
                    () -> assertEquals(bookDto.authors().size(), result.getAuthors().size(), "Authors size should match"),
                    () -> {
                        for (int i = 0; i < bookDto.authors().size(); i++) {
                            assertEquals(bookDto.authors().get(i).id(), result.getAuthors().get(i).getId(), "Author ID should match at index " + i);
                        }
                    }
            );
        } else {
            assertEquals(List.of(), result.getAuthors(), "Authors should be empty");
        }
    }


}