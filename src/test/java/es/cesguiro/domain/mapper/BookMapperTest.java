package es.cesguiro.domain.mapper;

import es.cesguiro.domain.model.Book;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.service.dto.AuthorDto;
import es.cesguiro.domain.service.dto.BookDto;
import es.cesguiro.domain.service.dto.PublisherDto;
import es.cesguiro.utils.InstancioModel;
import org.instancio.Instancio;
import org.instancio.Model;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    static Stream<Arguments> argumentsForMapperTests(Model<?> from, Model<?> to) {
        return Stream.of(
                Arguments.of(
                        Instancio.of(from)
                                .set(field(BookEntity::authors), List.of())
                                .ignore(field(BookEntity::publisher))
                                .lenient()
                                .withSeed(1)
                                .create(),
                        Instancio.of(to)
                                .set(field(Book::getAuthors), List.of())
                                .ignore(field(Book::getPublisher))
                                .ignore(field(Book::getPrice))
                                .lenient()
                                .withSeed(1)
                                .create()
                ),
                Arguments.of(
                        Instancio.of(from)
                                .ignore(field(BookEntity::publisher))
                                .lenient()
                                .withSeed(2)
                                .create(),
                        Instancio.of(to)
                                .ignore(field(Book::getPublisher))
                                .ignore(field(Book::getPrice))
                                .lenient()
                                .withSeed(2)
                                .create()
                ),
                Arguments.of(
                        Instancio.of(from)
                                .set(field(BookEntity::authors), List.of())
                                .lenient()
                                .withSeed(3)
                                .create(),
                        Instancio.of(to)
                                .set(field(Book::getAuthors), List.of())
                                .ignore(field(Book::getPrice))
                                .lenient()
                                .withSeed(3)
                                .create()
                ),
                Arguments.of(
                        Instancio.of(from)
                                .withSeed(4)
                                .create(),
                        Instancio.of(to)
                                .ignore(field(Book::getPrice))
                                .withSeed(4)
                                .create()
                )
        );
    }

    /*static Stream<Arguments> argumentsFromBookEntityToBook() {
        return Stream.of(
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                                .set(field(BookEntity::authors), List.of())
                                .ignore(field(BookEntity::publisher))
                                .lenient()
                                .withSeed(1)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .set(field(Book::getAuthors), List.of())
                                .ignore(field(Book::getPublisher))
                                .ignore(field(Book::getPrice))
                                .lenient()
                                .withSeed(1)
                                .create()
                ),
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                                .ignore(field(BookEntity::publisher))
                                .lenient()
                                .withSeed(2)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .ignore(field(Book::getPublisher))
                                .ignore(field(Book::getPrice))
                                .lenient()
                                .withSeed(2)
                                .create()
                ),
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                                .set(field(BookEntity::authors), List.of())
                                .lenient()
                                .withSeed(3)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .set(field(Book::getAuthors), List.of())
                                .ignore(field(Book::getPrice))
                                .lenient()
                                .withSeed(3)
                                .create()
                ),
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                                .withSeed(4)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .ignore(field(Book::getPrice))
                                .withSeed(4)
                                .create()
                )
        );
    }*/


    @ParameterizedTest
    @MethodSource("argumentsForMapperTests")
    @ArgumentsSource(InstancioModel.BOOK_ENTITY_MODEL, InstancioModel.BOOK_MODEL)
    @DisplayName("Map BookEntity to Book should return correct Book")
    void fromBookEntityToBookTest(BookEntity bookEntity, Book expected) {
        Book result = BookMapper.getInstance().fromBookEntityToBook(bookEntity);

        assertAll(
                () -> assertEquals(expected.getId(), result.getId(), "ID should match"),
                () -> assertEquals(expected.getIsbn(), result.getIsbn(), "ISBN should match"),
                () -> assertEquals(expected.getTitleEs(), result.getTitleEs(), "TitleEs should match"),
                () -> assertEquals(expected.getTitleEn(), result.getTitleEn(), "TitleEn should match"),
                () -> assertEquals(expected.getSynopsisEs(), result.getSynopsisEs(), "SynopsisEs should match"),
                () -> assertEquals(expected.getSynopsisEn(), result.getSynopsisEn(), "SynopsisEn should match"),
                () -> assertEquals(expected.getBasePrice(), result.getBasePrice(), "BasePrice should match"),
                () -> assertEquals(expected.getDiscountPercentage(), result.getDiscountPercentage(), "DiscountPercentage should match"),
                () -> assertEquals(expected.getCover(), result.getCover(), "Cover should match"),
                () -> assertEquals(expected.getPublicationDate(), result.getPublicationDate(), "PublicationDate should match"),
                () -> assertEquals(expected.getPublisher(), result.getPublisher(), "Publisher should match"),
                () -> assertEquals(expected.getAuthors(), result.getAuthors(), "Authors should match")
        );
    }

    static Stream<Arguments> argumentsFromBookToBookEntity() {
        return Stream.of(
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .set(field(Book::getAuthors), List.of())
                                .ignore(field(Book::getPublisher))
                                .ignore(field(Book::getPrice))
                                .lenient()
                                .withSeed(10)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                                .set(field(BookEntity::authors), List.of())
                                .ignore(field(BookEntity::publisher))
                                .lenient()
                                .withSeed(10)
                                .create()
                ),
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .ignore(field(Book::getPublisher))
                                .ignore(field(Book::getPrice))
                                .lenient()
                                .withSeed(20)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                                .ignore(field(BookEntity::publisher))
                                .lenient()
                                .withSeed(20)
                                .create()
                ),
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .set(field(Book::getAuthors), List.of())
                                .ignore(field(Book::getPrice))
                                .lenient()
                                .withSeed(30)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                                .set(field(BookEntity::authors), List.of())
                                .lenient()
                                .withSeed(30)
                                .create()
                ),
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .ignore(field(Book::getPrice))
                                .withSeed(40)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                                .withSeed(40)
                                .create()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsFromBookToBookEntity")
    @DisplayName("Map Book to BookEntity should return correct BookEntity")
    void fromBookToBookEntityTest(Book book, BookEntity expected) {
        var result = BookMapper.getInstance().fromBookToBookEntity(book);
        assertAll(
                () -> assertEquals(expected.id(), result.id(), "ID should match"),
                () -> assertEquals(expected.isbn(), result.isbn(), "ISBN should match"),
                () -> assertEquals(expected.titleEs(), result.titleEs(), "TitleEs should match"),
                () -> assertEquals(expected.titleEn(), result.titleEn(), "TitleEn should match"),
                () -> assertEquals(expected.synopsisEs(), result.synopsisEs(), "SynopsisEs should match"),
                () -> assertEquals(expected.synopsisEn(), result.synopsisEn(), "SynopsisEn should match"),
                () -> assertEquals(expected.basePrice(), result.basePrice(), "BasePrice should match"),
                () -> assertEquals(expected.discountPercentage(), result.discountPercentage(), "DiscountPercentage should match"),
                () -> assertEquals(expected.cover(), result.cover(), "Cover should match"),
                () -> assertEquals(expected.publicationDate(), result.publicationDate(), "PublicationDate should match"),
                () -> assertEquals(expected.publisher(), result.publisher(), "Publisher should match"),
                () -> assertEquals(expected.authors(), result.authors(), "Authors should match")
        );
    }


    static Stream<Arguments> argumentsFromBookToBookDto() {
        return Stream.of(
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .set(field(Book::getAuthors), List.of())
                                .ignore(field(Book::getPublisher))
                                .ignore(field(Book::getPrice))
                                .lenient()
                                .withSeed(100)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                                .set(field(BookEntity::authors), List.of())
                                .ignore(field(BookEntity::publisher))
                                .lenient()
                                .withSeed(100)
                                .create()
                ),
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .ignore(field(Book::getPublisher))
                                .ignore(field(Book::getPrice))
                                .lenient()
                                .withSeed(200)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                                .ignore(field(BookEntity::publisher))
                                .lenient()
                                .withSeed(200)
                                .create()
                ),
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .set(field(Book::getAuthors), List.of())
                                .ignore(field(Book::getPrice))
                                .lenient()
                                .withSeed(300)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                                .set(field(BookEntity::authors), List.of())
                                .lenient()
                                .withSeed(300)
                                .create()
                ),
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .ignore(field(Book::getPrice))
                                .withSeed(400)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                                .withSeed(400)
                                .create()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsFromBookToBookDto")
    @DisplayName("Mapping Book to BookDto should return correct BookDto")
    void fromBookToBookDtoTest(Book book, BookEntity ignored) {
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
        assertAll(
                () -> assertEquals(book.getAuthors().size(), result.authors().size(), "Authors size should match"),
                () -> {
                    for (int i = 0; i < book.getAuthors().size(); i++) {
                        assertEquals(book.getAuthors().get(i).getId(), result.authors().get(i).id(), "Author ID should match at index " + i);
                    }
                }
        );

    }

    /*private static BookDto buildBookDto(PublisherDto publisher, List<AuthorDto> authors) {
        BookDto base = testDataFactory.createBook(BookDto.class, false, SEED_VALUE);
        return new BookDto(
                base.id(), base.isbn(), base.titleEs(), base.titleEn(),
                base.synopsisEs(), base.synopsisEn(), base.basePrice(),
                base.discountPercentage(), base.price(),
                base.cover(), base.publicationDate(), publisher, authors);
    }

    static Stream<BookDto> validBookDtos() {
        return Stream.of(
                buildBookDto(null, List.of()),
                buildBookDto(testDataFactory.createPublisher(PublisherDto.class, SEED_VALUE), List.of()),
                buildBookDto(null, testDataFactory.createAuthorList(AuthorDto.class, 1, SEED_VALUE)),
                buildBookDto(testDataFactory.createPublisher(PublisherDto.class, SEED_VALUE), testDataFactory.createAuthorList(AuthorDto.class, 1, SEED_VALUE)),
                buildBookDto(testDataFactory.createPublisher(PublisherDto.class, SEED_VALUE), testDataFactory.createAuthorList(AuthorDto.class, 3, SEED_VALUE))
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
    }*/


}