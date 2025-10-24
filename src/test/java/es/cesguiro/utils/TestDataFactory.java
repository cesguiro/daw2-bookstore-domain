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
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TestDataFactory {

    private final TestDataProvider provider;
    /*private final Map<Class<?>, BiFunction<TestDataProvider, Integer, List<?>>> creators = Map.of(
            PublisherEntity.class, TestDataProvider::createPublisherEntity,
            PublisherDto.class, TestDataProvider::createPublisherDto,
            Publisher.class, TestDataProvider::createPublisher,
            AuthorEntity.class, TestDataProvider::createAuthorEntity,
            AuthorDto.class, TestDataProvider::createAuthorDto,
            Author.class, TestDataProvider::createAuthor,
            BookEntity.class, TestDataProvider::createBookEntity,
            BookDto.class, TestDataProvider::createBookDto,
            Book.class, TestDataProvider::createBook
    );*/

    public TestDataFactory() {
        this(new TestDataProviderInstancio());
    }

    public TestDataFactory(TestDataProvider provider) {
        if (provider == null) {
            throw new IllegalArgumentException("TestDataProvider cannot be null");
        }
        this.provider = provider;
    }


   /* public <T> T of(Class<T> clazz) {
        BiFunction<TestDataProvider, Integer, List<?>> creator = creators.get(clazz);

        if(creator == null) {
            throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
        }
        List<?> sourceList = creator.apply(provider, 1);
        return clazz.cast(sourceList.getFirst());
    }

    public <T> List<T> ofList(Class<T> clazz, int size) {
        BiFunction<TestDataProvider, Integer, List<?>> creator = creators.get(clazz);

        if(creator == null) {
            throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
        }
        List<?> sourceList = creator.apply(provider, size);
        return sourceList.stream()
                .map(clazz::cast)
                .toList();
    }*/

    public <T> T createPublisher(Class<T> clazz) {
        if (clazz.equals(PublisherEntity.class)) {
            return clazz.cast(provider.createPublisherEntity(1).getFirst());
        }
        if (clazz.equals(Publisher.class)) {
            return clazz.cast(provider.createPublisher(1).getFirst());
        }
        if (clazz.equals(PublisherDto.class)) {
            return clazz.cast(provider.createPublisherDto(1).getFirst());
        }
        throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
    }

    public <T> List<T> createPublisherList(Class<T> clazz, int size) {
        if (clazz.equals(PublisherEntity.class)) {
            return provider.createPublisherEntity(size).stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(Publisher.class)) {
            return provider.createPublisher(size).stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(PublisherDto.class)) {
            return provider.createPublisherDto(size).stream()
                    .map(clazz::cast)
                    .toList();
        }
        throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
    }

    public <T> T createAuthor(Class<T> clazz) {
        if (clazz.equals(AuthorEntity.class)) {
            return clazz.cast(provider.createAuthorEntity(1).getFirst());
        }
        if (clazz.equals(Author.class)) {
            return clazz.cast(provider.createAuthor(1).getFirst());
        }
        if (clazz.equals(AuthorDto.class)) {
            return clazz.cast(provider.createAuthorDto(1).getFirst());
        }
        throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
    }

    public <T> List<T> createAuthorList(Class<T> clazz, int size) {
        if (clazz.equals(AuthorEntity.class)) {
            return provider.createAuthorEntity(size).stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(Author.class)) {
            return provider.createAuthor(size).stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(AuthorDto.class)) {
            return provider.createAuthorDto(size).stream()
                    .map(clazz::cast)
                    .toList();
        }
        throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
    }

    public <T> T createBook(Class<T> clazz, boolean withRelations) {
        if (clazz.equals(BookEntity.class)) {
            return clazz.cast(provider.createBookEntity(1, withRelations).getFirst());
        }
        if (clazz.equals(Book.class)) {
            return clazz.cast(provider.createBook(1, withRelations).getFirst());
        }
        if (clazz.equals(BookDto.class)) {
            return clazz.cast(provider.createBookDto(1, withRelations).getFirst());
        }
        throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
    }

    public <T> List<T> createBookList(Class<T> clazz, int size, boolean withRelations) {
        if (clazz.equals(BookEntity.class)) {
            return provider.createBookEntity(size, withRelations).stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(Book.class)) {
            return provider.createBook(size, withRelations).stream()
                    .map(clazz::cast)
                    .toList();
        }
        if (clazz.equals(BookDto.class)) {
            return provider.createBookDto(size, withRelations).stream()
                    .map(clazz::cast)
                    .toList();
        }
        throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
    }

}
