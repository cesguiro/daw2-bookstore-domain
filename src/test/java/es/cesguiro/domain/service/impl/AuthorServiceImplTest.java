package es.cesguiro.domain.service.impl;

import es.cesguiro.domain.exception.BusinessException;
import es.cesguiro.domain.repository.AuthorRepository;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.service.dto.AuthorDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorServiceImpl;


    @Test
    @DisplayName("create should return created AuthorDto")
    void create_ShouldReturnCreatedAuthorDto() {
        // Arrange
        AuthorDto authorDto = new AuthorDto(
                null,
                "author1",
                "nationality1",
                "BioEs",
                "BioEn",
                1970,
                null,
                "slug1"
        );

        // Mock repository behavior
        when(authorRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        AuthorDto createdAuthorDto = authorServiceImpl.create(authorDto);
        // Assert
        assertAll(
                () -> assertNotNull(createdAuthorDto, "Created AuthorDto should not be null"),
                () -> assertEquals(authorDto.name(), createdAuthorDto.name(), "Names should match"),
                () -> assertEquals(authorDto.nationality(), createdAuthorDto.nationality())
        );
    }

    // Test create Author with null AuthorDto

    // Test create Author with existing slug
    @Test
    @DisplayName("create should throw BusinessException when slug already exists")
    void create_ShouldThrowBusinessException_WhenSlugAlreadyExists() {
        // Arrange
        AuthorDto authorDto = new AuthorDto(
                null,
                "author1",
                "nationality1",
                "BioEs",
                "BioEn",
                1970,
                null,
                "existing-slug"
        );
        AuthorEntity existingAuthor = new AuthorEntity(
                1L,
                "author1",
                "nationality1",
                "BioEs",
                "BioEn",
                1970,
                null,
                "existing-slug"
        );

        // Mock repository behavior to simulate existing slug
        when(authorRepository.findBySlug("existing-slug")).thenReturn(Optional.of(existingAuthor));

        // Act & Assert
        assertThrows(BusinessException.class, () -> {authorServiceImpl.create(authorDto);});
    }


}