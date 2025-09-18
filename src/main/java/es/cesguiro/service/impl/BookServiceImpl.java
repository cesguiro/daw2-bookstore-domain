package es.cesguiro.service.impl;

import es.cesguiro.exception.ResourceNotFoundException;
import es.cesguiro.mapper.BookMapper;
import es.cesguiro.model.Page;
import es.cesguiro.repository.entity.BookEntity;
import es.cesguiro.service.dto.BookDto;
import es.cesguiro.exception.BusinessException;
import es.cesguiro.repository.BookRepository;
import es.cesguiro.service.BookService;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
    public BookDto create(BookDto bookDto) {
        return null;
    }

    @Override
    public BookDto update(BookDto bookDto) {
        return null;
    }

    @Override
    public void delete(String isbn) {

    }
}
