package es.cesguiro.service.impl;

import es.cesguiro.service.dto.BookDto;
import es.cesguiro.exception.BusinessException;
import es.cesguiro.repository.BookRepository;
import es.cesguiro.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDto> findAll(int page, int size) {
            return bookRepository.findAll(page, size);
    }

    @Override
    public BookDto findByIsbn(String isbn) {
        return bookRepository
                .findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("Book with isbn " + isbn + " not found"));
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
