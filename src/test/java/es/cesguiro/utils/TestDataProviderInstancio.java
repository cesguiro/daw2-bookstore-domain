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
import org.instancio.Assign;
import org.instancio.Instancio;
import org.instancio.InstancioApi;
import org.instancio.Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.instancio.Select.field;

public final class TestDataProviderInstancio implements TestDataProvider {

    private static final String ISBN_PATTERN = "#d#d#d#d#d#d#d#d#d#d#d#d#d";
    private static final String SLUG_PATTERN = "#c#c#c-#c#c#c";

    /*******************************************************************************
     * Modelos de Publisher
     **************************************************************************/
    private static final Model<PublisherEntity> PUBLISHER_ENTITY_MODEL = Instancio.of(PublisherEntity.class)
            .generate(field(PublisherEntity.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            .toModel();
    private static final Model<Publisher> PUBLISHER_MODEL = Instancio.of(Publisher.class)
            .generate(field(Publisher.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            .toModel();
    private static final Model<PublisherDto> PUBLISHER_DTO_MODEL = Instancio.of(PublisherDto.class)
            .generate(field(PublisherDto.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            .toModel();
    /*******************************************************************************
     * Modelos de Author
     **************************************************************************/
    private static final Model<AuthorEntity> AUTHOR_ENTITY_MODEL = Instancio.of(AuthorEntity.class)
            .generate(field(AuthorEntity.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            .toModel();
    private static final Model<Author> AUTHOR_MODEL = Instancio.of(Author.class)
            .generate(field(Author.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            .toModel();
    private static final Model<AuthorDto> AUTHOR_DTO_MODEL = Instancio.of(AuthorDto.class)
            .generate(field(AuthorDto.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            .toModel();
    /*******************************************************************************
     * Modelos de Book
     **************************************************************************/
    private static final Model<BookEntity> BOOK_ENTITY_MODEL = Instancio.of(BookEntity.class)
            .generate(field(BookEntity.class, "isbn"), gen -> gen.text().pattern(ISBN_PATTERN))
            .generate(field(BookEntity.class, "discountPercentage"), gen -> gen.doubles().range(0.0, 100.0))
            .generate(field(BookEntity.class, "publicationDate"), gen -> gen.temporal().localDate().past())
            .toModel();
    private static final Model<Book> BOOK_MODEL = Instancio.of(Book.class)
            .generate(field(Book.class, "isbn"), gen -> gen.text().pattern(ISBN_PATTERN))
            .generate(field(Book.class, "discountPercentage"), gen -> gen.doubles().range(0.0, 100.0))
            .generate(field(Book.class, "publicationDate"), gen -> gen.temporal().localDate().past())
            .toModel();
    private static final Model<BookDto> BOOK_DTO_MODEL = Instancio.of(BookDto.class)
            .generate(field(BookDto.class, "isbn"), gen -> gen.text().pattern(ISBN_PATTERN))
            .generate(field(BookDto.class, "discountPercentage"), gen -> gen.doubles().range(0.0, 100.0))
            .generate(field(BookDto.class, "publicationDate"), gen -> gen.temporal().localDate().past())
            .set(field(BookDto.class, "price"), 0L)
            .toModel();



    @Override
    public List<PublisherEntity> createPublisherEntity(int size, int seed) {
        InstancioApi<List<PublisherEntity>> api = Instancio.ofList(PUBLISHER_ENTITY_MODEL)
                .size(size);
        return (seed !=0) ? api.withSeed(seed).create() : api.create();
    }

    @Override
    public List<Publisher> createPublisher(int size, int seed) {
        InstancioApi<List<Publisher>> api = Instancio.ofList(PUBLISHER_MODEL)
                .size(size);
        return (seed !=0) ? api.withSeed(seed).create() : api.create();
    }

    @Override
    public List<PublisherDto> createPublisherDto(int size, int seed) {
        InstancioApi<List<PublisherDto>> api = Instancio.ofList(PUBLISHER_DTO_MODEL)
                .size(size);
        return  (seed !=0) ? api.withSeed(seed).create() : api.create();
    }

    @Override
    public List<AuthorEntity> createAuthorEntity(int size, int seed) {
        InstancioApi<List<AuthorEntity>> api = Instancio.ofList(AUTHOR_ENTITY_MODEL)
                .size(size);
        return (seed !=0) ? api.withSeed(seed).create() : api.create();
    }

    @Override
    public List<Author> createAuthor(int size, int seed) {
        InstancioApi<List<Author>> api = Instancio.ofList(AUTHOR_MODEL)
                .size(size);
        return (seed !=0) ? api.withSeed(seed).create() : api.create();
    }

    @Override
    public List<AuthorDto> createAuthorDto(int size, int seed) {
        InstancioApi<List<AuthorDto>> api = Instancio.ofList(AUTHOR_DTO_MODEL)
                .size(size);
        return (seed !=0) ? api.withSeed(seed).create() : api.create();
    }

    @Override
    public List<BookEntity> createBookEntity(int size, boolean withRelations, int seed) {
        InstancioApi<List<BookEntity>> api = Instancio.ofList(BOOK_ENTITY_MODEL)
                .size(size);
        if (!withRelations) {
            api = api.ignore(field(BookEntity.class, "publisher"))
                    .ignore(field(BookEntity.class, "authors"));
        }
        return (seed !=0) ? api.withSeed(seed).create() : api.create();
    }

    @Override
    public List<Book> createBook(int size, boolean withRelations, int seed) {
        InstancioApi<List<Book>> api =Instancio.ofList(BOOK_MODEL)
                .size(size);
        if (!withRelations) {
            api = api.ignore(field(Book.class, "publisher"))
                    .ignore(field(Book.class, "authors"));
        }
        return (seed !=0) ? api.withSeed(seed).create() : api.create();
    }

    @Override
    public List<BookDto> createBookDto(int size, boolean withRelations, int seed) {
        InstancioApi<List<BookDto>> api = Instancio.ofList(BOOK_DTO_MODEL)
                .size(size);
        if (!withRelations) {
            api = api.ignore(field(BookDto.class, "publisher"))
                    .ignore(field(BookDto.class, "authors"));
        }
        return (seed !=0) ? api.withSeed(seed).create() : api.create();
    }
}
