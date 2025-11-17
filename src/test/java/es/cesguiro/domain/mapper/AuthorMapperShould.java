package es.cesguiro.domain.mapper;

import es.cesguiro.domain.model.Author;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.service.dto.AuthorDto;
import es.cesguiro.util.InstancioModel;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorMapperShould {


    @Test
    void map_AuthorEntity_to_Author() {
        AuthorEntity authorEntity = Instancio.of(InstancioModel.AUTHOR_ENTITY_MODEL).withSeed(39).create();
        Author expected = Instancio.of(InstancioModel.AUTHOR_MODEL).withSeed(39).create();

        Author result = AuthorMapper.getInstance().fromAuthorEntityToAuthor(authorEntity);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void map_Author_to_AuthorEntity() {
        Author author = Instancio.of(InstancioModel.AUTHOR_MODEL).withSeed(39).create();
        AuthorEntity expected = Instancio.of(InstancioModel.AUTHOR_ENTITY_MODEL).withSeed(39).create();

        AuthorEntity result = AuthorMapper.getInstance().fromAuthorToAuthorEntity(author);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void map_Author_to_AuthorDto() {
        Author author = Instancio.of(InstancioModel.AUTHOR_MODEL).withSeed(39).create();
        AuthorDto expected = Instancio.of(InstancioModel.AUTHOR_DTO_MODEL).withSeed(39).create();

        AuthorDto result = AuthorMapper.getInstance().fromAuthorToAuthorDto(author);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void map_AuthorDto_to_Author() {
        AuthorDto authorDto = Instancio.of(InstancioModel.AUTHOR_DTO_MODEL).withSeed(39).create();
        Author expected = Instancio.of(InstancioModel.AUTHOR_MODEL).withSeed(39).create();

        Author result = AuthorMapper.getInstance().fromAuthorDtoToAuthor(authorDto);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void return_null_when_model_to_map_is_null() {
        assertThat(AuthorMapper.getInstance().fromAuthorEntityToAuthor(null)).isNull();
        assertThat(AuthorMapper.getInstance().fromAuthorToAuthorEntity(null)).isNull();
        assertThat(AuthorMapper.getInstance().fromAuthorToAuthorDto(null)).isNull();
        assertThat(AuthorMapper.getInstance().fromAuthorDtoToAuthor(null)).isNull();
    }


}