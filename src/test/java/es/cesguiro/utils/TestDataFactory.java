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

public class TestDataFactory {

    private final TestDataProvider provider;

    public TestDataFactory(TestDataProvider provider) {
        if (provider == null) {
            provider = new TestDataProviderInstancio();
        }
        this.provider = provider;
    }

    public <T> T of(Class<T> clazz) {
        if (clazz.equals(PublisherEntity.class)) {
            return clazz.cast(provider.createPublisherEntity(1).getFirst());
        }
        if (clazz.equals(PublisherDto.class)) {
            return clazz.cast(provider.createPublisherDto(1).getFirst());
        }
        if (clazz.equals(Publisher.class)) {
            return clazz.cast(provider.createPublisher(1).getFirst());
        }
        if (clazz.equals(AuthorEntity.class)) {
            return clazz.cast(new TestDataProviderInstancio().createAuthorEntity(1).getFirst());
        }
        if (clazz.equals(AuthorDto.class)) {
            return clazz.cast(new TestDataProviderInstancio().createAuthorDto(1).getFirst());
        }
        if (clazz.equals(Author.class)) {
            return clazz.cast(new TestDataProviderInstancio().createAuthor(1).getFirst());
        }
        if (clazz.equals(BookEntity.class)) {
            return clazz.cast(new TestDataProviderInstancio().createBookEntity(1).getFirst());
        }
        if (clazz.equals(BookDto.class)) {
            return clazz.cast(new TestDataProviderInstancio().createBookDto(1).getFirst());
        }
        if (clazz.equals(Book.class)) {
            return clazz.cast(new TestDataProviderInstancio().createBook(1).getFirst());
        }

        throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
    }

    public <T> List<T> ofList(Class<T> clazz, int size) {
        if (clazz.equals(PublisherEntity.class)) {
            List<PublisherEntity> sourceList = provider.createPublisherEntity(size);
            return sourceList.stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(PublisherDto.class)) {
            List<PublisherDto> sourceList = provider.createPublisherDto(size);
            return sourceList.stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(Publisher.class)) {
            List<Publisher> sourceList = provider.createPublisher(size);
            return sourceList.stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(AuthorEntity.class)) {
            List<AuthorEntity> sourceList = provider.createAuthorEntity(size);
            return sourceList.stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(AuthorDto.class)) {
            List<AuthorDto> sourceList = provider.createAuthorDto(size);
            return sourceList.stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(Author.class)) {
            List<Author> sourceList = provider.createAuthor(size);
            return sourceList.stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(BookEntity.class)) {
            List<BookEntity> sourceList = provider.createBookEntity(size);
            return sourceList.stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(BookDto.class)) {
            List<BookDto> sourceList = provider.createBookDto(size);
            return sourceList.stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(Book.class)) {
            List<Book> sourceList = provider.createBook(size);
            return sourceList.stream()
                    .map(clazz::cast)
                    .toList();
        }
        throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
    }

}
