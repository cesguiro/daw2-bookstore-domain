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

import java.util.List;
import java.util.SequencedCollection;

public interface TestDataProvider {

    List<PublisherEntity> createPublisherEntity(int size, int seed);
    List<Publisher> createPublisher(int size, int seed);
    List<PublisherDto> createPublisherDto(int size, int seed);
    List<AuthorEntity> createAuthorEntity(int size, int seed);
    List<Author> createAuthor(int size, int seed);
    List<AuthorDto> createAuthorDto(int size, int seed);
    List<BookEntity> createBookEntity(int size, boolean withRelations, int seed);
    List<Book> createBook(int size, boolean withRelations, int seed);
    List<BookDto> createBookDto(int size, boolean withRelations, int seed);
}
