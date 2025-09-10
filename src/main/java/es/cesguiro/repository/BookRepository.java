package es.cesguiro.repository;

import es.cesguiro.service.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<BookDto> findAll(int page, int size);

    Optional<BookDto> findByIsbn(String isbn);
}
