package es.cesguiro.domain.repository;

import es.cesguiro.domain.model.Page;
import es.cesguiro.domain.repository.entity.BookEntity;

import java.util.Optional;

public interface BookRepository {

    Page<BookEntity> findAll(int page, int size);
    Optional<BookEntity> findByIsbn(String isbn);
}
