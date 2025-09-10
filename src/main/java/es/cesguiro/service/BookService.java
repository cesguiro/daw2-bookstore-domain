package es.cesguiro.service;

import es.cesguiro.service.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> findAll(int page, int size);

    BookDto findByIsbn(String isbn);

    BookDto create(BookDto bookDto);

    BookDto update(BookDto bookDto);

    void delete(String isbn);

}
