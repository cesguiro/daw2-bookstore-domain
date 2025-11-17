package es.cesguiro.domain.mapper;

import es.cesguiro.domain.model.Book;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.service.dto.BookDto;
import es.cesguiro.util.InstancioModel;
import org.instancio.Instancio;
import org.instancio.InstancioApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

class BookMapperShould {

    private BookEntity createBookEntity(boolean withPublisher, boolean withAuthors) {
        InstancioApi<BookEntity> base = Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                .withSeed(4)
                .set(field(BookEntity::basePrice), BigDecimal.valueOf(50.00))
                .set(field(BookEntity::discountPercentage), BigDecimal.valueOf(0.00))
                .lenient();
        if(!withPublisher) {
            base.ignore(field(BookEntity::publisher));
        }
        if(!withAuthors) {
            base.set(field(BookEntity::authors), List.of());
        }

        return base.create();
    }

    private Book createBook(boolean withPublisher, boolean withAuthors) {
        InstancioApi<Book> base =  Instancio.of(InstancioModel.BOOK_MODEL)
                .withSeed(4)
                .set(field(Book::getBasePrice), BigDecimal.valueOf(50.00))
                .set(field(Book::getDiscountPercentage), BigDecimal.valueOf(0.00))
                .set(field(Book::getPrice), BigDecimal.valueOf(50.00))
                .lenient();
        if(!withPublisher) {
            base.ignore(field(Book::getPublisher));
        }
        if(!withAuthors) {
            base.set(field(Book::getAuthors), List.of());
        }

        return base.create();
    }

    private BookDto createBookDto(boolean withPublisher, boolean withAuthors) {
        InstancioApi<BookDto> base = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .withSeed(4)
                .set(field(Book::getBasePrice), BigDecimal.valueOf(50.00))
                .set(field(Book::getDiscountPercentage), BigDecimal.valueOf(0.00))
                .set(field(Book::getPrice), BigDecimal.valueOf(50.00))
                .lenient();
        if(!withPublisher) {
            base.ignore(field(BookDto::publisher));
        }
        if(!withAuthors) {
            base.set(field(BookDto::authors), List.of());
        }

        return base.create();
    }

    @Nested
    public class MapBookEntityToBook {

        @Test
        void map_BookEntity_to_Book() {
            BookEntity bookEntity = createBookEntity(true, true);
            Book expected = createBook(true, true);

            Book result = BookMapper.getInstance().fromBookEntityToBook(bookEntity);

            assertThat(result)
                    .usingRecursiveComparison()
                    .withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                    .isEqualTo(expected);
        }

        @Test
        void map_BookEntity_to_Book_with_null_Publisher() {
            BookEntity bookEntity = createBookEntity(false, true);
            Book expected = createBook(false, true);

            Book result = BookMapper.getInstance().fromBookEntityToBook(bookEntity);

            assertThat(result)
                    .usingRecursiveComparison()
                    .withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                    .isEqualTo(expected);
        }

        @Test
        void map_BookEntity_to_Book_with_empty_list_Authors() {
            BookEntity bookEntity = createBookEntity(true, false);
            Book expected = createBook(true, false);

            Book result = BookMapper.getInstance().fromBookEntityToBook(bookEntity);

            assertThat(result)
                    .usingRecursiveComparison()
                    .withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                    .isEqualTo(expected);
        }

        @Test
        void map_BookEntity_to_Book_with_null_Publisher_and_empty_list_Authors(){
            BookEntity bookEntity = createBookEntity(false, false);
            Book expected = createBook(false, false);

            Book result = BookMapper.getInstance().fromBookEntityToBook(bookEntity);

            assertThat(result)
                    .usingRecursiveComparison()
                    .withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                    .isEqualTo(expected);
        }

    }

    @Nested
    public class MapBookToBookEntity {

        @Test
        void map_Book_to_BookEntity() {
            Book book = createBook(true, true);
            BookEntity expected = createBookEntity(true, true);

            BookEntity result = BookMapper.getInstance().fromBookToBookEntity(book);

            assertThat(result)
                    .usingRecursiveComparison()
                    .withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                    .isEqualTo(expected);
        }

        @Test
        void map_Book_to_BookEntity_with_null_Publisher() {
            Book book = createBook(false, true);
            BookEntity expected = createBookEntity(false, true);

            BookEntity result = BookMapper.getInstance().fromBookToBookEntity(book);

            assertThat(result)
                    .usingRecursiveComparison()
                    .withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                    .isEqualTo(expected);
        }

        @Test
        void map_Book_to_BookEntity_with_empty_list_Authors() {
            Book book = createBook(true, false);
            BookEntity expected = createBookEntity(true, false);

            BookEntity result = BookMapper.getInstance().fromBookToBookEntity(book);

            assertThat(result)
                    .usingRecursiveComparison()
                    .withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                    .isEqualTo(expected);
        }

        @Test
        void map_Book_to_BookEntity_with_null_Publisher_and_empty_list_Authors(){
            Book book = createBook(false, false);
            BookEntity expected = createBookEntity(false, false);

            BookEntity result = BookMapper.getInstance().fromBookToBookEntity(book);

            assertThat(result)
                    .usingRecursiveComparison()
                    .withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                    .isEqualTo(expected);
        }

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