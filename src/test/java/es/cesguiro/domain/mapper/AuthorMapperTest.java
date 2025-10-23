package es.cesguiro.domain.mapper;

import es.cesguiro.data.loader.AuthorsDataLoader;
import es.cesguiro.domain.exception.BusinessException;
import es.cesguiro.domain.mapper.AuthorMapper;
import es.cesguiro.domain.model.Author;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.service.dto.AuthorDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

class AuthorMapperTest {

    @Test
    @DisplayName("Map AuthorEntity to Author should return correct Author")
    void fromAuthorEntityToAuthorTest() {
        AuthorEntity authorEntity = Instancio.of(AuthorEntity.class)
                .generate(field(AuthorEntity.class, "slug"), gen -> gen.text().pattern("#c#c#c-#c#c#c"))
                .create();
        var result = AuthorMapper.getInstance().fromAuthorEntityToAuthor(authorEntity);
        assertAll(
                () -> assertEquals(authorEntity.id(), result.getId(), "ID should match"),
                () -> assertEquals(authorEntity.name(), result.getName(), "Name should match"),
                () -> assertEquals(authorEntity.nationality(), result.getNationality(), "Nationality should match"),
                () -> assertEquals(authorEntity.biographyEs(), result.getBiographyEs(), "BiographyEs should match"),
                () -> assertEquals(authorEntity.biographyEn(), result.getBiographyEn(), "BiographyEn should match"),
                () -> assertEquals(authorEntity.birthYear(), result.getBirthYear(), "BirthYear should match"),
                () -> assertEquals(authorEntity.deathYear(), result.getDeathYear(), "DeathYear should match"),
                () -> assertEquals(authorEntity.slug(), result.getSlug(), "Slug should match")
        );
    }

    @Test
    @DisplayName("Map Author to AuthorEntity should return correct AuthorEntity")
    void fromAuthorToAuthorEntityTest() {
        Author author = Instancio.of(Author.class)
                .generate(field(Author.class, "slug"), gen -> gen.text().pattern("#c#c#c-#c#c#c"))
                .create();
        var result = AuthorMapper.getInstance().fromAuthorToAuthorEntity(author);
        assertAll(
                () -> assertEquals(author.getId(), result.id(), "ID should match"),
                () -> assertEquals(author.getName(), result.name(), "Name should match"),
                () -> assertEquals(author.getNationality(), result.nationality(), "Nationality should match"),
                () -> assertEquals(author.getBiographyEs(), result.biographyEs(), "BiographyEs should match"),
                () -> assertEquals(author.getBiographyEn(), result.biographyEn(), "BiographyEn should match"),
                () -> assertEquals(author.getBirthYear(), result.birthYear(), "BirthYear should match"),
                () -> assertEquals(author.getDeathYear(), result.deathYear(), "DeathYear should match"),
                () -> assertEquals(author.getSlug(), result.slug(), "Slug should match")
        );
    }

    @Test
    @DisplayName("Map Author to AuthorDto should return correct AuthorDto")
    void fromAuthortoAuthorDtoTest() {
        Author author = Instancio.of(Author.class)
                .generate(field(Author.class, "slug"), gen -> gen.text().pattern("#c#c#c-#c#c#c"))
                .create();
        var result = AuthorMapper.getInstance().fromAuthorToAuthorDto(author);
        assertAll(
                () -> assertEquals(author.getId(), result.id(), "ID should match"),
                () -> assertEquals(author.getName(), result.name(), "Name should match"),
                () -> assertEquals(author.getNationality(), result.nationality(), "Nationality should match"),
                () -> assertEquals(author.getBiographyEs(), result.biographyEs(), "BiographyEs should match"),
                () -> assertEquals(author.getBiographyEn(), result.biographyEn(), "BiographyEn should match"),
                () -> assertEquals(author.getBirthYear(), result.birthYear(), "BirthYear should match"),
                () -> assertEquals(author.getDeathYear(), result.deathYear(), "DeathYear should match"),
                () -> assertEquals(author.getSlug(), result.slug(), "Slug should match")
        );
    }

    @Test
    @DisplayName("Map AuthorDto to Author should return correct Author")
    void fromAuthorDtoToAuthorTest() {
        AuthorDto authorDto = Instancio.of(AuthorDto.class)
                .generate(field(AuthorDto.class, "slug"), gen -> gen.text().pattern("#c#c#c-#c#c#c"))
                .create();
        var result = AuthorMapper.getInstance().fromAuthorDtoToAuthor(authorDto);
        assertAll(
                () -> assertEquals(authorDto.id(), result.getId(), "ID should match"),
                () -> assertEquals(authorDto.name(), result.getName(), "Name should match"),
                () -> assertEquals(authorDto.nationality(), result.getNationality(), "Nationality should match"),
                () -> assertEquals(authorDto.biographyEs(), result.getBiographyEs(), "BiographyEs should match"),
                () -> assertEquals(authorDto.biographyEn(), result.getBiographyEn(), "BiographyEn should match"),
                () -> assertEquals(authorDto.birthYear(), result.getBirthYear(), "BirthYear should match"),
                () -> assertEquals(authorDto.deathYear(), result.getDeathYear(), "DeathYear should match"),
                () -> assertEquals(authorDto.slug(), result.getSlug(), "Slug should match")
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