package es.cesguiro.domain.service.impl;

import es.cesguiro.domain.exception.ResourceNotFoundException;
import es.cesguiro.domain.mapper.PublisherMapper;
import es.cesguiro.domain.repository.PublisherRepository;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.domain.service.dto.PublisherDto;
import es.cesguiro.domain.service.PublisherService;

import java.util.List;

public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<PublisherDto> getAll() {
        return null;
    }

    @Override
    public PublisherDto getBySlug(String slug) {
        return publisherRepository.findBySlug(slug)
                .map(PublisherMapper.getInstance()::fromPublisherEntityToPublisher)
                .map(PublisherMapper.getInstance()::fromPublisherToPublisherDto)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher with slug " + slug + " not found"));
    }

    @Override
    public PublisherDto create(PublisherDto publisherDto) {
        return null;
    }

    @Override
    public PublisherDto update(PublisherDto publisherDto) {
        PublisherEntity existingPublisher = publisherRepository.findById(publisherDto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Publisher with id " + publisherDto.id() + " not found"));
        PublisherEntity updatedPublisher = publisherRepository.save(
                PublisherMapper.getInstance().fromPublisherToPublisherEntity(
                    PublisherMapper.getInstance().fromPublisherDtoToPublisher(publisherDto)
                )
        );
        return PublisherMapper.getInstance().fromPublisherToPublisherDto(
                PublisherMapper.getInstance().fromPublisherEntityToPublisher(updatedPublisher)
        );
    }

    @Override
    public PublisherDto delete(String slug) {
        return null;
    }
}
