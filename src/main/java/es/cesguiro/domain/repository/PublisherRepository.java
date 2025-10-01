package es.cesguiro.domain.repository;

import es.cesguiro.domain.repository.entity.PublisherEntity;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository {

    Optional<PublisherEntity> findById(Long id);
    Optional<PublisherEntity> findBySlug(String slug);
    PublisherEntity save(PublisherEntity publisherEntity);
}
