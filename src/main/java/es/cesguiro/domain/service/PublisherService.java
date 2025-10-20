package es.cesguiro.domain.service;

import es.cesguiro.domain.service.dto.PublisherDto;

import java.util.List;

public interface PublisherService {

    List<PublisherDto> getAll(int page, int size);
    PublisherDto getBySlug(String slug);
    PublisherDto create(PublisherDto publisherDto);
    PublisherDto update(PublisherDto publisherDto);
    PublisherDto delete(String slug);
}
