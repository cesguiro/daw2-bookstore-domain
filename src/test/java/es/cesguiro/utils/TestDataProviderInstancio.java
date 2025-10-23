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

import java.util.List;

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
    public List<BookEntity> createBookEntity(int size) {
        return Instancio.ofList(BookEntity.class)
                .size(size)
                .generate(field(BookEntity.class, "isbn"), gen -> gen.text().pattern("#d#d#d#d#d#d#d#d#d#d#d#d#d"))
                .generate(field(BookEntity.class, "discountPercentage"), gen -> gen.doubles().range(0.0, 100.0))
                .generate(field(BookEntity.class, "publicationDate"), gen -> gen.temporal().localDate().past())
                .ignore(field(BookEntity::publisher))
                .ignore(field(BookEntity::authors))
                .create();
    }

    @Override
    public List<Book> createBook(int size) {
        return Instancio.ofList(Book.class)
                .size(size)
                .generate(field(Book.class, "isbn"), gen -> gen.text().pattern("#d#d#d#d#d#d#d#d#d#d#d#d#d"))
                .generate(field(Book.class, "discountPercentage"), gen -> gen.doubles().range(0.0, 100.0))
                .generate(field(Book.class, "publicationDate"), gen -> gen.temporal().localDate().past())
                .ignore(field(Book::getPublisher))
                .ignore(field(Book::getAuthors))
                .create();
    }

    @Override
    public List<BookDto> createBookDto(int size) {
        return Instancio.ofList(BookDto.class)
                .size(size)
                .generate(field(BookDto.class, "isbn"), gen -> gen.text().pattern("#d#d#d#d#d#d#d#d#d#d#d#d#d"))
                .generate(field(BookDto.class, "discountPercentage"), gen -> gen.doubles().range(0.0, 100.0))
                .generate(field(BookDto.class, "publicationDate"), gen -> gen.temporal().localDate().past())
                .ignore(field(BookDto::publisher))
                .ignore(field(BookDto::authors))
                .create();
    }
}
