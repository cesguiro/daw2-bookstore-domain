package es.cesguiro.domain.service.impl;

import es.cesguiro.data.loader.PublishersDataLoader;
import es.cesguiro.domain.exception.ResourceNotFoundException;
import es.cesguiro.domain.exception.ValidationException;
import es.cesguiro.domain.model.Publisher;
import es.cesguiro.domain.repository.PublisherRepository;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.domain.service.dto.PublisherDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublisherServiceImplTest {

    @Mock
    PublisherRepository publisherRepository;

    @InjectMocks
    PublisherServiceImpl publisherServiceImpl;

    private static List<Publisher> publishers;
    private static List<PublisherDto> publisherDtos;
    private static List<PublisherEntity> publisherEntities;

    @BeforeAll
    static void setUp() {
        PublishersDataLoader publishersDataLoader = new PublishersDataLoader();
        publishers = publishersDataLoader.loadPublishersFromCSV();
        publisherDtos = publishersDataLoader.loadPublisherDtosFromCSV();
        publisherEntities = publishersDataLoader.loadPublisherEntitiesFromCSV();
    }

    @Test
    @DisplayName("Update publisher")
    void updatePublisher() {
        PublisherDto publisherDto = publisherDtos.getFirst();
        PublisherEntity publisherEntity = publisherEntities.getFirst();
        PublisherEntity updatedPublisherEntity = new PublisherEntity(
                publisherEntity.id(),
                "Updated Name",
                "updated-slug");
        PublisherDto expected = new PublisherDto(
                publisherDto.id(),
                "Updated Name",
                "updated-slug");
        when(publisherRepository.findById(publisherDto.id())).thenReturn(Optional.of(publisherEntity));
        when(publisherRepository.save(publisherEntity)).thenReturn(updatedPublisherEntity);

        PublisherDto result = publisherServiceImpl.update(publisherDto);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(expected.id(), result.id()),
                () -> assertEquals(expected.name(), result.name()),
                () -> assertEquals(expected.slug(), result.slug())
        );

        verify(publisherRepository).save(publisherEntity);
    }

    @Test
    @DisplayName("Update publisher with non existing PublisherDto throws ResourceNotFoundException")
    void updatePublisher_WithNullPublisherDto_ThrowsValidationException() {
        PublisherDto publisherDto = new PublisherDto(
                999L,
                "Non Existing Publisher",
                "non-existing-publisher");
        when(publisherRepository.findById(publisherDto.id())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            publisherServiceImpl.update(publisherDto);
        });

        assertEquals("Publisher with id 999 not found", exception.getMessage());
    }



}