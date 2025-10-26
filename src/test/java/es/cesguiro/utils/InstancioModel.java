package es.cesguiro.utils;

import es.cesguiro.domain.model.Author;
import es.cesguiro.domain.model.Book;
import es.cesguiro.domain.model.Publisher;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.domain.service.dto.AuthorDto;
import es.cesguiro.domain.service.dto.BookDto;
import es.cesguiro.domain.service.dto.PublisherDto;
import org.instancio.Instancio;
import org.instancio.Model;

import java.math.BigDecimal;
import java.util.List;

import static org.instancio.Select.field;

public class InstancioModel {

    private static final String ISBN_PATTERN = "#d#d#d#d#d#d#d#d#d#d#d#d#d";
    private static final String SLUG_PATTERN = "#c#c#c-#c#c#c";

    /*******************************************************************************
     * Modelos de Publisher
     **************************************************************************/
    public static final Model<PublisherEntity> PUBLISHER_ENTITY_MODEL = Instancio.of(PublisherEntity.class)
            .generate(field(PublisherEntity.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            .toModel();
    public static final Model<Publisher> PUBLISHER_MODEL = Instancio.of(Publisher.class)
            .generate(field(Publisher.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            .toModel();
    public static final Model<PublisherDto> PUBLISHER_DTO_MODEL = Instancio.of(PublisherDto.class)
            .generate(field(PublisherDto.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            .toModel();
    /*******************************************************************************
     * Modelos de Author
     **************************************************************************/
    public static final Model<AuthorEntity> AUTHOR_ENTITY_MODEL = Instancio.of(AuthorEntity.class)
            .generate(field(AuthorEntity.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            .toModel();
    public static final Model<Author> AUTHOR_MODEL = Instancio.of(Author.class)
            .generate(field(Author.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            .toModel();
    public static final Model<AuthorDto> AUTHOR_DTO_MODEL = Instancio.of(AuthorDto.class)
            .generate(field(AuthorDto.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            .toModel();

    public static Model<List<AuthorEntity>> AUTHOR_ENTITY_LIST_MODEL = Instancio.ofList(AUTHOR_ENTITY_MODEL)
            .toModel();
    public static Model<List<Author>> AUTHOR_LIST_MODEL = Instancio.ofList(AUTHOR_MODEL)
            .toModel();
    public static Model<List<AuthorDto>> AUTHOR_DTO_LIST_MODEL = Instancio.ofList(AUTHOR_DTO_MODEL)
            .toModel();

    /*******************************************************************************
     * Modelos de Book
     **************************************************************************/

    public static final Model<BookEntity> BOOK_ENTITY_MODEL = Instancio.of(BookEntity.class)
            .generate(field(BookEntity::isbn), gen -> gen.text().pattern(ISBN_PATTERN))
            .generate(field(BookEntity::discountPercentage), gen -> gen.math().bigDecimal().range(new BigDecimal("0.00"), new BigDecimal("100.00")))
            .generate(field(BookEntity::publicationDate), gen -> gen.temporal().localDate().past())
            .setModel(field(BookEntity::publisher), PUBLISHER_ENTITY_MODEL)
            .setModel(field(BookEntity::authors), AUTHOR_ENTITY_LIST_MODEL)
            .toModel();
    public static final Model<Book> BOOK_MODEL = Instancio.of(Book.class)
            .generate(field(Book::getIsbn), gen -> gen.text().pattern(ISBN_PATTERN))
            .generate(field(Book::getDiscountPercentage), gen -> gen.math().bigDecimal().range(new BigDecimal("0.00"), new BigDecimal("100.00")))
            .generate(field(Book::getPublicationDate), gen -> gen.temporal().localDate().past())
            .setModel(field(Book::getPublisher), PUBLISHER_MODEL)
            .setModel(field(Book::getAuthors), AUTHOR_LIST_MODEL)
            .toModel();
    public static final Model<BookDto> BOOK_DTO_MODEL = Instancio.of(BookDto.class)
            .generate(field(BookDto::isbn), gen -> gen.text().pattern(ISBN_PATTERN))
            .generate(field(BookDto::discountPercentage), gen -> gen.math().bigDecimal().range(new BigDecimal("0.00"), new BigDecimal("100.00")))
            .generate(field(BookDto::publicationDate), gen -> gen.temporal().localDate().past())
            .set(field(BookDto.class, "price"), 0L)
            .setModel(field(BookDto::publisher), PUBLISHER_DTO_MODEL)
            .setModel(field(BookDto::authors), AUTHOR_DTO_LIST_MODEL)
            .toModel();

}
