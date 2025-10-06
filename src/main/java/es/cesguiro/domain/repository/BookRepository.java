package es.cesguiro.domain.repository;

import es.cesguiro.domain.model.Page;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.service.dto.BookDto;

import java.util.Optional;

public interface BookRepository {

    Page<BookEntity> findAll(int page, int size);
    Optional<BookEntity> findByIsbn(String isbn);
    BookEntity save(BookEntity bookEntity);
    Optional<BookEntity> findById(Long id);
    void deleteByIsbn(String isbn);
}
