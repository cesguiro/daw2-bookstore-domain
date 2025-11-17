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
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

class BookMapperShould {


    @ParameterizedTest
    @CsvSource({
            "true, true",
            "false, true",
            "true, false",
            "false, false"
    })
    void map_BookEntity_to_Book(boolean withPublisher, boolean withAuthors) {
        BookEntity bookEntity = createBookEntity(withPublisher, withAuthors);
        Book expected = createBook(withPublisher, withAuthors);

        Book result = BookMapper.getInstance().fromBookEntityToBook(bookEntity);

        assertThat(result)
                .usingRecursiveComparison()
                .withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "true, true",
            "false, true",
            "true, false",
            "false, false"
    })
    void map_Book_to_BookEntity(boolean withPublisher, boolean withAuthors) {
        Book book = createBook(withPublisher, withAuthors);
        BookEntity expected = createBookEntity(withPublisher, withAuthors);

        BookEntity result = BookMapper.getInstance().fromBookToBookEntity(book);

        assertThat(result)
                .usingRecursiveComparison()
                .withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "true, true",
            "false, true",
            "true, false",
            "false, false"
    })
    void map_Book_to_BookDto(boolean withPublisher, boolean withAuthors) {
        Book book = createBook(withPublisher, withAuthors);
        BookDto expected = createBookDto(withPublisher, withAuthors);

        BookDto result = BookMapper.getInstance().fromBookToBookDto(book);

        assertThat(result)
                .usingRecursiveComparison()
                .withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "true, true",
            "false, true",
            "true, false",
            "false, false"
    })
    void map_BookDto_to_Book(boolean withPublisher, boolean withAuthors) {
        BookDto bookDto = createBookDto(withPublisher, withAuthors);
        Book expected = createBook(withPublisher, withAuthors);

        Book result = BookMapper.getInstance().fromBookDtoToBook(bookDto);

        assertThat(result)
                .usingRecursiveComparison()
                .withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                .isEqualTo(expected);
    }

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
                .set(field(BookDto::basePrice), BigDecimal.valueOf(50.00))
                .set(field(BookDto::discountPercentage), BigDecimal.valueOf(0.00))
                .set(field(BookDto::price), BigDecimal.valueOf(50.00))
                .lenient();
        if(!withPublisher) {
            base.ignore(field(BookDto::publisher));
        }
        if(!withAuthors) {
            base.set(field(BookDto::authors), List.of());
        }

        return base.create();
    }

    @Test
    void return_null_when_model_to_map_is_null() {
        assertThat(BookMapper.getInstance().fromBookEntityToBook(null)).isNull();
        assertThat(BookMapper.getInstance().fromBookToBookEntity(null)).isNull();
        assertThat(BookMapper.getInstance().fromBookToBookDto(null)).isNull();
        assertThat(BookMapper.getInstance().fromBookDtoToBook(null)).isNull();
    }
}