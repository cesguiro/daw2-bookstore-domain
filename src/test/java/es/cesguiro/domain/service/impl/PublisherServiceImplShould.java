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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublisherServiceImplShould {

    @Mock
    PublisherRepository publisherRepository;

    @InjectMocks
    PublisherServiceImpl publisherServiceImpl;

    @Test
    void return_publisher_dto_when_get_by_slug_given_existing_slug() {
        // Given
        String slug = "existing-slug";
        PublisherEntity publisherEntity = Instancio.create(InstancioModel.PUBLISHER_ENTITY_MODEL);
        when(publisherRepository.findBySlug(any())).thenReturn(Optional.of(publisherEntity));

        // When
        PublisherDto result = publisherServiceImpl.getBySlug(slug);

        // Then
        assertThat(result).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(publisherEntity);
        verify(publisherRepository).findBySlug(slug);
    }

    @Test
    void throw_resource_not_found_exception_when_get_by_slug_given_non_existing_slug() {
        // Given
        String slug = "non-existing-slug";
        when(publisherRepository.findBySlug(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {publisherServiceImpl.getBySlug(slug);});
    }

    @Test
    void return_updated_publisher_dto_when_update_given_existing_publisher_dto() {
        // Given
        PublisherDto publisherDto = Instancio.create(InstancioModel.PUBLISHER_DTO_MODEL);
        PublisherEntity existingPublisherEntity = Instancio.create(InstancioModel.PUBLISHER_ENTITY_MODEL);
        when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(existingPublisherEntity));
        when(publisherRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        PublisherDto result = publisherServiceImpl.update(publisherDto);

        // Then
        assertThat(result).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(publisherDto);
        verify(publisherRepository).findById(publisherDto.id());
        verify(publisherRepository).save(any());
    }

    @Test
    void throw_resource_not_found_exception_when_update_given_non_existing_publisher_dto() {
        // Given
        PublisherDto publisherDto = Instancio.create(InstancioModel.PUBLISHER_DTO_MODEL);
        when(publisherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {publisherServiceImpl.update(publisherDto);});
        verify(publisherRepository, never()).save(any());
    }


}