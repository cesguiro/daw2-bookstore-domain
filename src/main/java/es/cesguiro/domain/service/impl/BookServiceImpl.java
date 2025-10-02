package es.cesguiro.domain.service.impl;

import es.cesguiro.domain.exception.BusinessException;
import es.cesguiro.domain.exception.ResourceNotFoundException;
import es.cesguiro.domain.exception.ValidationException;
import es.cesguiro.domain.mapper.BookMapper;
import es.cesguiro.domain.model.Book;
import es.cesguiro.domain.model.Page;
import es.cesguiro.domain.repository.AuthorRepository;
import es.cesguiro.domain.repository.PublisherRepository;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.service.dto.BookDto;
import es.cesguiro.domain.repository.BookRepository;
import es.cesguiro.domain.service.BookService;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, PublisherRepository publisherRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Page<BookDto> getAll(int page, int size) {
            Page<BookEntity> bookEntityPage =  bookRepository
                    .findAll(page, size);
            List<BookDto> itemsDto = bookEntityPage.data()
                    .stream()
                    .map(BookMapper.getInstance()::fromBookEntityToBook)
                    .map(BookMapper.getInstance()::fromBookToBookDto)
                    .toList();
            return new Page<>(
                    itemsDto,
                    bookEntityPage.pageNumber(),
                    bookEntityPage.pageSize(),
                    bookEntityPage.totalElements()
            );
    }

    @Override
    public BookDto getByIsbn(String isbn) {
        return bookRepository
                .findByIsbn(isbn)
                .map(BookMapper.getInstance()::fromBookEntityToBook)
                .map(BookMapper.getInstance()::fromBookToBookDto)
                .orElseThrow(() -> new ResourceNotFoundException("Book with isbn " + isbn + " not found"));
    }

    @Override
    public Optional<BookDto> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(BookMapper.getInstance()::fromBookEntityToBook)
                .map(BookMapper.getInstance()::fromBookToBookDto);
    }

    @Override
    @Transactional
    public BookDto create(BookDto bookDto) {
        Optional<BookDto> existingBookDto = findByIsbn(bookDto.isbn());

        if (existingBookDto.isPresent()) {
            throw new BusinessException("Book with isbn " + bookDto.isbn() + " already exists");
        }

        BookEntity newBookEntity = BookMapper.getInstance().fromBookToBookEntity(
                BookMapper.getInstance().fromBookDtoToBook(bookDto)
        );

        if(bookDto.publisher() != null  &&
                publisherRepository.findById(bookDto.publisher().id()).isEmpty()) {
            throw new ResourceNotFoundException("Publisher with id " + bookDto.publisher().id() + " does not exist");
        }

        if(bookDto.authors() != null) {
            bookDto.authors().forEach(author -> {
                if (authorRepository.findById(author.id()).isEmpty()) {
                    throw new ResourceNotFoundException("Author with id " + author.id() + " does not exist");
                }
            });
        }

        return BookMapper.getInstance().fromBookToBookDto(
                BookMapper.getInstance().fromBookEntityToBook(
                        bookRepository.save(newBookEntity)
                )
        );
    }

    @Override
    public BookDto update(BookDto bookDto) {
        return null;
    }

    @Override
    public void delete(String isbn) {

    }
}
