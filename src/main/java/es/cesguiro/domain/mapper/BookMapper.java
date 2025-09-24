package es.cesguiro.domain.mapper;

import es.cesguiro.domain.exception.BusinessException;
import es.cesguiro.domain.exception.ValidationException;
import es.cesguiro.domain.model.Author;
import es.cesguiro.domain.model.Book;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.service.dto.AuthorDto;
import es.cesguiro.domain.service.dto.BookDto;

import java.util.ArrayList;
import java.util.List;

public class BookMapper {

    private static BookMapper INSTANCE;

    private BookMapper() {
    }

    public static BookMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookMapper();
        }
        return INSTANCE;
    }

    public Book fromBookEntityToBook(BookEntity bookEntity) {
        if (bookEntity == null) {
            return null;
        }
        List<Author> authors = new ArrayList<>();
        if (bookEntity.authors() != null && !bookEntity.authors().isEmpty()) {
            authors = bookEntity.authors().stream().map(AuthorMapper.getInstance()::fromAuthorEntityToAuthor).toList();
        }
        try {
            return new Book(
                    bookEntity.id(),
                    bookEntity.isbn(),
                    bookEntity.titleEs(),
                    bookEntity.titleEn(),
                    bookEntity.synopsisEs(),
                    bookEntity.synopsisEn(),
                    bookEntity.basePrice(),
                    bookEntity.discountPercentage(),
                    bookEntity.cover(),
                    bookEntity.publicationDate(),
                    PublisherMapper.getInstance().fromPublisherEntityToPublisher(bookEntity.publisher()),
                    authors
            );
        } catch (ValidationException e) {
            //Añadir al log
            return null;
        }

    }

    public BookEntity fromBookToBookEntity(Book book) {
        if (book == null) {
            return null;
        }
        List<AuthorEntity> authors = null;
        if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            authors = book.getAuthors().stream().map(AuthorMapper.getInstance()::fromAuthorToAuthorEntity).toList();
        }
        return new BookEntity(
                book.getId(),
                book.getIsbn(),
                book.getTitleEs(),
                book.getTitleEn(),
                book.getSynopsisEs(),
                book.getSynopsisEn(),
                book.getBasePrice(),
                book.getDiscountPercentage(),
                book.getCover(),
                book.getPublicationDate(),
                PublisherMapper.getInstance().fromPublisherToPublisherEntity(book.getPublisher()),
                authors
        );
    }

    public BookDto fromBookToBookDto(Book book) {
        if (book == null) {
            return null;
        }
        List<AuthorDto> authors = null;
        if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            authors = book.getAuthors().stream().map(AuthorMapper.getInstance()::fromAuthorToAuthorDto).toList();
        }
        return new BookDto(
                book.getId(),
                book.getIsbn(),
                book.getTitleEs(),
                book.getTitleEn(),
                book.getSynopsisEs(),
                book.getSynopsisEn(),
                book.getBasePrice(),
                book.getDiscountPercentage(),
                book.calculateFinalPrice(),
                book.getCover(),
                book.getPublicationDate(),
                PublisherMapper.getInstance().fromPublisherToPublisherDto(book.getPublisher()),
                authors
        );
    }


    public Book fromBookDtoToBook(BookDto bookDto) {
        if (bookDto == null) {
            return null;
        }

        Book book = new Book(
                bookDto.id(),
                bookDto.isbn(),
                bookDto.titleEs(),
                bookDto.titleEn(),
                bookDto.synopsisEs(),
                bookDto.synopsisEn(),
                bookDto.basePrice(),
                bookDto.discountPercentage(),
                bookDto.cover(),
                bookDto.publicationDate(),
                PublisherMapper.getInstance().fromPublisherDtoToPublisher(bookDto.publisher()),
                null
        );
        if (bookDto.authors() != null && !bookDto.authors().isEmpty()) {
            book.setAuthors(bookDto.authors().stream().map(AuthorMapper.getInstance()::fromAuthorDtoToAuthor).toList());
        }
        return book;
    }
}
