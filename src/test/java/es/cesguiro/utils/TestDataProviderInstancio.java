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
import org.instancio.InstancioApi;
import org.instancio.InstancioCollectionsApi;

import java.util.List;
import java.util.Random;

import static org.instancio.Select.field;

public final class TestDataProviderInstancio implements TestDataProvider {

    private static final String ISBN_PATTERN = "#d#d#d#d#d#d#d#d#d#d#d#d#d";
    private static final String SLUG_PATTERN = "#c#c#c-#c#c#c";



    @Override
    public List<PublisherEntity> createPublisherEntity(int size) {
        return Instancio.ofList(PublisherEntity.class)
                .size(size)
                .generate(field(PublisherEntity.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
                .create();
    }

    @Override
    public List<Publisher> createPublisher(int size) {
        return Instancio.ofList(Publisher.class)
                .size(size)
                .generate(field(Publisher.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
                .create();
    }

    @Override
    public List<PublisherDto> createPublisherDto(int size) {
        return Instancio.ofList(PublisherDto.class)
                .size(size)
                .generate(field(PublisherDto.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
                .create();
    }

    @Override
    public List<AuthorEntity> createAuthorEntity(int size) {
        return Instancio.ofList(AuthorEntity.class)
                .size(size)
                .generate(field(AuthorEntity.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
                .create();
    }

    @Override
    public List<Author> createAuthor(int size) {
        return Instancio.ofList(Author.class)
                .size(size)
                .generate(field(Author.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
                .create();
    }

    @Override
    public List<AuthorDto> createAuthorDto(int size) {
        return Instancio.ofList(AuthorDto.class)
                .size(size)
                .generate(field(AuthorDto.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
                .create();
    }

    @Override
    public List<BookEntity> createBookEntity(int size, boolean withRelations) {
        PublisherEntity publisher = null;
        List<AuthorEntity> authors = List.of();
        if (withRelations) {
            int authorCount = new Random().nextInt(1, 3);
            publisher = createPublisherEntity(1).getFirst();
            authors = createAuthorEntity(authorCount);
        }
        return Instancio.ofList(BookEntity.class)
                .size(size)
                .generate(field(BookEntity.class, "isbn"), gen -> gen.text().pattern(ISBN_PATTERN))
                .generate(field(BookEntity.class, "discountPercentage"), gen -> gen.doubles().range(0.0, 100.0))
                .generate(field(BookEntity.class, "publicationDate"), gen -> gen.temporal().localDate().past())
                .set(field(BookEntity::publisher), publisher)
                .set(field(BookEntity::authors), authors)
                .create();
    }

    @Override
    public List<Book> createBook(int size, boolean withRelations) {
        Publisher publisher = null;
        List<Author> authors = List.of();
        if (withRelations) {
            int authorCount = new Random().nextInt(1, 3);
            publisher = createPublisher(1).getFirst();
            authors = createAuthor(authorCount);
        }
        return Instancio.ofList(Book.class)
                .size(size)
                .generate(field(Book.class, "isbn"), gen -> gen.text().pattern(ISBN_PATTERN))
                .generate(field(Book.class, "discountPercentage"), gen -> gen.doubles().range(0.0, 100.0))
                .generate(field(Book.class, "publicationDate"), gen -> gen.temporal().localDate().past())
                .set(field(Book::getPublisher), publisher)
                .set(field(Book::getAuthors), List.of())
                .create();
    }

    @Override
    public List<BookDto> createBookDto(int size, boolean withRelations) {
        PublisherDto publisher = null;
        List<AuthorDto> authors = List.of();
        if (withRelations) {
            int authorCount = new Random().nextInt(1, 3);
            publisher = createPublisherDto(1).getFirst();
            authors = createAuthorDto(authorCount);
        }
        return Instancio.ofList(BookDto.class)
                .size(size)
                .generate(field(BookDto.class, "isbn"), gen -> gen.text().pattern(ISBN_PATTERN))
                .generate(field(BookDto.class, "discountPercentage"), gen -> gen.doubles().range(0.0, 100.0))
                .generate(field(BookDto.class, "publicationDate"), gen -> gen.temporal().localDate().past())
                .set(field(BookDto::publisher), publisher)
                .set(field(BookDto::authors), authors)
                .create();
    }
}
