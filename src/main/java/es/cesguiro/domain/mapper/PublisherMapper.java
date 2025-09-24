package es.cesguiro.domain.mapper;

import es.cesguiro.domain.exception.BusinessException;
import es.cesguiro.domain.model.Publisher;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.domain.service.dto.PublisherDto;

public class PublisherMapper {

    private static PublisherMapper INSTANCE;

    private PublisherMapper() {
    }

    public static PublisherMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PublisherMapper();
        }
        return INSTANCE;
    }

    public Publisher fromPublisherEntityToPublisher(PublisherEntity publisherEntity) {
        if (publisherEntity == null) {
            return null;
        }
        return new Publisher(
                publisherEntity.id(),
                publisherEntity.name(),
                publisherEntity.slug()
        );
    }

    public PublisherEntity fromPublisherToPublisherEntity(Publisher publisher) {
        if (publisher == null) {
            return null;
        }
        return new PublisherEntity(
                publisher.getId(),
                publisher.getName(),
                publisher.getSlug()
        );
    }

    public PublisherDto fromPublisherToPublisherDto(Publisher publisher) {
        if (publisher == null) {
            return null;
        }
        return new PublisherDto(
                publisher.getId(),
                publisher.getName(),
                publisher.getSlug()
        );
    }

    public Publisher fromPublisherDtoToPublisher(PublisherDto publisherDto) {
        if (publisherDto == null) {
            return null;
        }
        return new Publisher(
                publisherDto.id(),
                publisherDto.name(),
                publisherDto.slug()
        );
    }
}
