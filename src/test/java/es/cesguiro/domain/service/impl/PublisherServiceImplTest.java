package es.cesguiro.domain.service.impl;

import es.cesguiro.domain.exception.ResourceNotFoundException;
import es.cesguiro.domain.repository.PublisherRepository;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.domain.service.dto.PublisherDto;
import es.cesguiro.util.InstancioModel;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublisherServiceImplTest {

    @Mock
    PublisherRepository publisherRepository;

    @InjectMocks
    PublisherServiceImpl publisherServiceImpl;

    @Test
    @DisplayName("Update publisher")
    void updatePublisher() {
        PublisherEntity existing = Instancio.of(InstancioModel.PUBLISHER_ENTITY_MODEL)
                .create();

        PublisherDto publisherDtoToUpdate = new PublisherDto(
                existing.id(),
                "Updated Name",
                existing.slug()
        );
        PublisherEntity updatedPublisherEntity = new PublisherEntity(
                existing.id(),
                publisherDtoToUpdate.name(),
                publisherDtoToUpdate.slug()
        );

        when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(existing));
        when(publisherRepository.save(any())).thenReturn(updatedPublisherEntity);

        PublisherDto result = publisherServiceImpl.update(publisherDtoToUpdate);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(publisherDtoToUpdate.id(), result.id(), "Publisher IDs should match"),
                () -> assertEquals(publisherDtoToUpdate.name(), result.name(), "Publisher names should match"),
                () -> assertEquals(publisherDtoToUpdate.slug(), result.slug(), "Publisher slugs should match")
        );

        verify(publisherRepository).save(any());
    }

    @Test
    @DisplayName("Update publisher with non existing PublisherDto throws ResourceNotFoundException")
    void updatePublisher_WithNullPublisherDto_ThrowsValidationException() {
        PublisherDto publisherDto = new PublisherDto(
                999L,
                "Non Existing Publisher",
                "non-existing-publisher");

        when(publisherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {publisherServiceImpl.update(publisherDto);});

    }



}