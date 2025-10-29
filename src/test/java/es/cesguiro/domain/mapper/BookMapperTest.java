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


    static Stream<Arguments> argumentsFromBookEntityToBook() {
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
    }


    @ParameterizedTest
    @MethodSource("argumentsFromBookEntityToBook")
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
                                .lenient()
                                .withSeed(100)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                                .set(field(BookDto::authors), List.of())
                                .ignore(field(BookDto::publisher))
                                .lenient()
                                .withSeed(100)
                                .create()
                ),
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .ignore(field(Book::getPublisher))
                                .lenient()
                                .withSeed(200)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                                .ignore(field(BookDto::publisher))
                                .lenient()
                                .withSeed(200)
                                .create()
                ),
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .set(field(Book::getAuthors), List.of())
                                .lenient()
                                .withSeed(300)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                                .set(field(BookDto::authors), List.of())
                                .lenient()
                                .withSeed(300)
                                .create()
                ),
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .withSeed(400)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                                .withSeed(400)
                                .create()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsFromBookToBookDto")
    @DisplayName("Mapping Book to BookDto should return correct BookDto")
    void fromBookToBookDtoTest(Book book, BookDto expected) {
        var result = BookMapper.getInstance().fromBookToBookDto(book);
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

    static Stream<Arguments> argumentsFromBookDtoToBook() {
        return Stream.of(
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                                .set(field(BookDto::authors), List.of())
                                .ignore(field(BookDto::publisher))
                                .lenient()
                                .withSeed(100)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .set(field(Book::getAuthors), List.of())
                                .ignore(field(Book::getPublisher))
                                .lenient()
                                .withSeed(100)
                                .create()
                ),
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                                .ignore(field(BookDto::publisher))
                                .lenient()
                                .withSeed(200)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .ignore(field(Book::getPublisher))
                                .lenient()
                                .withSeed(200)
                                .create()
                ),
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                                .set(field(BookDto::authors), List.of())
                                .lenient()
                                .withSeed(300)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .set(field(Book::getAuthors), List.of())
                                .lenient()
                                .withSeed(300)
                                .create()
                ),
                Arguments.of(
                        Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                                .withSeed(400)
                                .create(),
                        Instancio.of(InstancioModel.BOOK_MODEL)
                                .withSeed(400)
                                .create()
                )
        );
    }


    @ParameterizedTest
    @MethodSource("argumentsFromBookDtoToBook")
    @DisplayName("Map BookDto to Book should return correct Book")
    void fromBookDtoToBookTest(BookDto bookDto, Book expected) {
        var result = BookMapper.getInstance().fromBookDtoToBook(bookDto);
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


}