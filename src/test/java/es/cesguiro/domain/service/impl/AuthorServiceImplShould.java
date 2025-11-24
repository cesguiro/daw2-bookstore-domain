package es.cesguiro.domain.service.impl;

import es.cesguiro.domain.exception.ValidationException;
import es.cesguiro.domain.repository.AuthorRepository;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.service.dto.AuthorDto;
import es.cesguiro.util.InstancioModel;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.instancio.Select.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplShould {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorServiceImpl;


    @Test
    void return_created_AuthorDto_when_create_author_with_valid_data() {
        AuthorDto newAuthorDto = Instancio.of(InstancioModel.AUTHOR_DTO_MODEL)
                .withSeed(123)
                .ignore(field(AuthorDto::id))
                .create();
        AuthorEntity authorEntityCreated = Instancio.of(InstancioModel.AUTHOR_ENTITY_MODEL)
                .withSeed(123)
                .set(field(AuthorEntity::id), 99L)
                .create();

        when(authorRepository.save(any())).thenReturn(authorEntityCreated);
        when(authorRepository.findBySlug(any())).thenReturn(Optional.empty());

        AuthorDto createdAuthorDto = authorServiceImpl.create(newAuthorDto);

        assertThat(createdAuthorDto).isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(newAuthorDto);
    }

    @Test
    void throw_NullPointerException_when_create_author_with_null_AuthorDto() {
        assertThatThrownBy(() -> authorServiceImpl.create(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void throw_ValidationException_when_create_author_with_existing_slug() {
        AuthorDto newAuthorDto = Instancio.of(InstancioModel.AUTHOR_DTO_MODEL)
                .ignore(field(AuthorDto::id))
                .create();
        AuthorEntity existingAuthorEntity = Instancio.of(InstancioModel.AUTHOR_ENTITY_MODEL)
                .create();

        when(authorRepository.findBySlug(any())).thenReturn(Optional.of(existingAuthorEntity));

        assertThatThrownBy(() -> authorServiceImpl.create(newAuthorDto))
                .isInstanceOf(ValidationException.class);
    }

}