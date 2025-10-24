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

    public TestDataFactory() {
        this(new TestDataProviderInstancio());
    }

    public TestDataFactory(TestDataProvider provider) {
        if (provider == null) {
            throw new IllegalArgumentException("TestDataProvider cannot be null");
        }
        this.provider = provider;
    }

    public <T> T createPublisher(Class<T> clazz, int seed) {
        if (clazz.equals(PublisherEntity.class)) {
            return clazz.cast(provider.createPublisherEntity(1, seed).getFirst());
        }
        if (clazz.equals(Publisher.class)) {
            return clazz.cast(provider.createPublisher(1, seed).getFirst());
        }
        if (clazz.equals(PublisherDto.class)) {
            return clazz.cast(provider.createPublisherDto(1, seed).getFirst());
        }
        throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
    }

    public <T> List<T> createPublisherList(Class<T> clazz, int size, int seed) {
        if (clazz.equals(PublisherEntity.class)) {
            return provider.createPublisherEntity(size, seed).stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(Publisher.class)) {
            return provider.createPublisher(size, seed).stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(PublisherDto.class)) {
            return provider.createPublisherDto(size, seed).stream()
                    .map(clazz::cast)
                    .toList();
        }
        throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
    }

    public <T> T createAuthor(Class<T> clazz, int seed) {
        if (clazz.equals(AuthorEntity.class)) {
            return clazz.cast(provider.createAuthorEntity(1, seed).getFirst());
        }
        if (clazz.equals(Author.class)) {
            return clazz.cast(provider.createAuthor(1, seed).getFirst());
        }
        if (clazz.equals(AuthorDto.class)) {
            return clazz.cast(provider.createAuthorDto(1, seed).getFirst());
        }
        throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
    }

    public <T> List<T> createAuthorList(Class<T> clazz, int size, int seed) {
        if (clazz.equals(AuthorEntity.class)) {
            return provider.createAuthorEntity(size, seed).stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(Author.class)) {
            return provider.createAuthor(size, seed).stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(AuthorDto.class)) {
            return provider.createAuthorDto(size, seed).stream()
                    .map(clazz::cast)
                    .toList();
        }
        throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
    }

    public <T> T createBook(Class<T> clazz, boolean withRelations, int seed) {
        if (clazz.equals(BookEntity.class)) {
            return clazz.cast(provider.createBookEntity(1, withRelations, seed).getFirst());
        }
        if (clazz.equals(Book.class)) {
            return clazz.cast(provider.createBook(1, withRelations, seed).getFirst());
        }
        if (clazz.equals(BookDto.class)) {
            return clazz.cast(provider.createBookDto(1, withRelations, seed).getFirst());
        }
        throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
    }

    public <T> List<T> createBookList(Class<T> clazz, int size, boolean withRelations, int seed) {
        if (clazz.equals(BookEntity.class)) {
            return provider.createBookEntity(size, withRelations, seed).stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(Book.class)) {
            return provider.createBook(size, withRelations, seed).stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(BookDto.class)) {
            return provider.createBookDto(size, withRelations, seed).stream()
                    .map(clazz::cast)
                    .toList();
        }
        throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
    }

}
