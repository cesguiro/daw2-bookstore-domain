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

    List<PublisherEntity> createPublisherEntity(int size);
    List<Publisher> createPublisher(int size);
    List<PublisherDto> createPublisherDto(int size);
    List<AuthorEntity> createAuthorEntity(int size);
    List<Author> createAuthor(int size);
    List<AuthorDto> createAuthorDto(int size);
    List<BookEntity> createBookEntity(int size);
    List<Book> createBook(int size);
    List<BookDto> createBookDto(int size);
}
