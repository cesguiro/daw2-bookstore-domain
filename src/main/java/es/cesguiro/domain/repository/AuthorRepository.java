package es.cesguiro.domain.repository;

import es.cesguiro.domain.repository.entity.AuthorEntity;

import java.util.Optional;

public interface AuthorRepository {

    AuthorEntity save(AuthorEntity authorEntity);
    Optional<AuthorEntity> findById(Long id);
}
