package es.cesguiro.domain.mapper;

import es.cesguiro.domain.model.Author;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.service.dto.AuthorDto;
import es.cesguiro.utils.InstancioModel;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

class AuthorMapperTest {


    @Test
    @DisplayName("Map AuthorEntity to Author should return correct Author")
    void fromAuthorEntityToAuthorTest() {
        AuthorEntity authorEntity = Instancio.of(InstancioModel.AUTHOR_ENTITY_MODEL).withSeed(39).create();
        Author expected = Instancio.of(InstancioModel.AUTHOR_MODEL).withSeed(39).create();
        Author result = AuthorMapper.getInstance().fromAuthorEntityToAuthor(authorEntity);
        assertAll(
                () -> assertEquals(expected.getId(), result.getId(), "ID should match"),
                () -> assertEquals(expected.getName(), result.getName(), "Name should match"),
                () -> assertEquals(expected.getNationality(), result.getNationality(), "Nationality should match"),
                () -> assertEquals(expected.getBiographyEs(), result.getBiographyEs(), "BiographyEs should match"),
                () -> assertEquals(expected.getBiographyEn(), result.getBiographyEn(), "BiographyEn should match"),
                () -> assertEquals(expected.getBirthYear(), result.getBirthYear(), "BirthYear should match"),
                () -> assertEquals(expected.getDeathYear(), result.getDeathYear(), "DeathYear should match"),
                () -> assertEquals(expected.getSlug(), result.getSlug(), "Slug should match")
        );
    }

    @Test
    @DisplayName("Map Author to AuthorEntity should return correct AuthorEntity")
    void fromAuthorToAuthorEntityTest() {
        Author author = Instancio.of(InstancioModel.AUTHOR_MODEL).withSeed(39).create();
        AuthorEntity expected = Instancio.of(InstancioModel.AUTHOR_ENTITY_MODEL).withSeed(39).create();
        AuthorEntity result = AuthorMapper.getInstance().fromAuthorToAuthorEntity(author);
        assertAll(
                () -> assertEquals(expected.id(), result.id(), "ID should match"),
                () -> assertEquals(expected.name(), result.name(), "Name should match"),
                () -> assertEquals(expected.nationality(), result.nationality(), "Nationality should match"),
                () -> assertEquals(expected.biographyEs(), result.biographyEs(), "BiographyEs should match"),
                () -> assertEquals(expected.biographyEn(), result.biographyEn(), "BiographyEn should match"),
                () -> assertEquals(expected.birthYear(), result.birthYear(), "BirthYear should match"),
                () -> assertEquals(expected.deathYear(), result.deathYear(), "DeathYear should match"),
                () -> assertEquals(expected.slug(), result.slug(), "Slug should match")
        );
    }

    @Test
    @DisplayName("Map Author to AuthorDto should return correct AuthorDto")
    void fromAuthorToAuthorDtoTest() {
        Author author = Instancio.of(InstancioModel.AUTHOR_MODEL).withSeed(39).create();
        AuthorDto expected = Instancio.of(InstancioModel.AUTHOR_DTO_MODEL).withSeed(39).create();
        AuthorDto result = AuthorMapper.getInstance().fromAuthorToAuthorDto(author);
        assertAll(
                () -> assertEquals(expected.id(), result.id(), "ID should match"),
                () -> assertEquals(expected.name(), result.name(), "Name should match"),
                () -> assertEquals(expected.nationality(), result.nationality(), "Nationality should match"),
                () -> assertEquals(expected.biographyEs(), result.biographyEs(), "BiographyEs should match"),
                () -> assertEquals(expected.biographyEn(), result.biographyEn(), "BiographyEn should match"),
                () -> assertEquals(expected.birthYear(), result.birthYear(), "BirthYear should match"),
                () -> assertEquals(expected.deathYear(), result.deathYear(), "DeathYear should match"),
                () -> assertEquals(expected.slug(), result.slug(), "Slug should match")
        );
    }

    @Test
    @DisplayName("Map AuthorDto to Author should return correct Author")
    void fromAuthorDtoToAuthorTest() {
        AuthorDto authorDto = Instancio.of(InstancioModel.AUTHOR_DTO_MODEL).withSeed(39).create();
        Author expected = Instancio.of(InstancioModel.AUTHOR_MODEL).withSeed(39).create();
        Author result = AuthorMapper.getInstance().fromAuthorDtoToAuthor(authorDto);
        assertAll(
                () -> assertEquals(expected.getId(), result.getId(), "ID should match"),
                () -> assertEquals(expected.getName(), result.getName(), "Name should match"),
                () -> assertEquals(expected.getNationality(), result.getNationality(), "Nationality should match"),
                () -> assertEquals(expected.getBiographyEs(), result.getBiographyEs(), "BiographyEs should match"),
                () -> assertEquals(expected.getBiographyEn(), result.getBiographyEn(), "BiographyEn should match"),
                () -> assertEquals(expected.getBirthYear(), result.getBirthYear(), "BirthYear should match"),
                () -> assertEquals(expected.getDeathYear(), result.getDeathYear(), "DeathYear should match"),
                () -> assertEquals(expected.getSlug(), result.getSlug(), "Slug should match")
        );
    }

    @Test
    @DisplayName("Mapping null Author should return null")
    void fromAuthorToAuthorEntity_NullAuthor_ShouldReturnNull() {
        assertNull(AuthorMapper.getInstance().fromAuthorEntityToAuthor(null), "Mapping null Author should return null AuthorEntity");
        assertNull(AuthorMapper.getInstance().fromAuthorToAuthorEntity(null), "Mapping null Author should return null AuthorEntity");
        assertNull(AuthorMapper.getInstance().fromAuthorToAuthorDto(null), "Mapping null Author should return null AuthorDto");
        assertNull(AuthorMapper.getInstance().fromAuthorDtoToAuthor(null), "Mapping null AuthorDto should return null Author");
    }


}