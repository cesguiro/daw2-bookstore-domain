package es.cesguiro.domain.mapper;

import es.cesguiro.data.loader.AuthorsDataLoader;
import es.cesguiro.domain.exception.BusinessException;
import es.cesguiro.domain.mapper.AuthorMapper;
import es.cesguiro.domain.model.Author;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.service.dto.AuthorDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorMapperTest {

    private static List<Author> authors;
    private static List<AuthorEntity> authorEntities;
    private static List<AuthorDto> authorDtos;

    @BeforeAll
    static void setUp() {
        var authorsDataLoader = new AuthorsDataLoader();
        authors = authorsDataLoader.loadAuthorsFromCSV();
        authorEntities = authorsDataLoader.loadAuthorEntitiesFromCSV();
        authorDtos = authorsDataLoader.loadAuthorDtosFromCSV();
    }

    @Test
    @DisplayName("Test map AuthorEntity to Author")
    void toAuthor() {
        // Arrange
        AuthorEntity authorEntity = authorEntities.getFirst();

        // Act
        Author result = AuthorMapper.getInstance().fromAuthorEntityToAuthor(authorEntity);
        Author expected = authors.getFirst();

        // Assert
        assertAll(
                () -> assertEquals(expected.getName(), result.getName(), "Names should match"),
                () -> assertEquals(expected.getNationality(), result.getNationality(), "Nationalities should match"),
                () -> assertEquals(expected.getBiographyEs(), result.getBiographyEs(), "BiographyEs should match"),
                () -> assertEquals(expected.getBiographyEn(), result.getBiographyEn(), "BiographyEn should match"),
                () -> assertEquals(expected.getBirthYear(), result.getBirthYear(), "Birth years should match"),
                () -> assertEquals(expected.getDeathYear(), result.getDeathYear(), "Death years should match"),
                () -> assertEquals(expected.getSlug(), result.getSlug(), "Slugs should match")
        );
    }

    @Test
    @DisplayName("Test map null AuthorEntity to Author")
    void toAuthor_NullAuthorEntity() {
        assertNull(AuthorMapper.getInstance().fromAuthorEntityToAuthor(null), "Mapping null AuthorEntity should return null Author");
    }

    @Test
    @DisplayName("Test map List<AuthorEntity> to Author")
    void toAuthorList() {
        // Arrange
        List<AuthorEntity> authorEntityList = List.of(
                authorEntities.getFirst(),
                authorEntities.get(1)
        );

        // Act
        List<Author> result = authorEntityList.stream().map(AuthorMapper.getInstance()::fromAuthorEntityToAuthor).toList();
        List<Author> expected = List.of(
                authors.getFirst(),
                authors.get(1)
        );

        // Assert
        assertAll(
                () -> assertEquals(expected.size(), result.size(), "List sizes should match"),
                () -> assertEquals(expected.getFirst().getName(), result.getFirst().getName(), "First author names should match"),
                () -> assertEquals(expected.get(1).getName(), result.get(1).getName(), "Second author names should match")
        );

    }

    @Test
    @DisplayName("Test map AuthorDto to Author with only id")
    void toAuthor_FromAuthorDtoWithOnlyId() {
        // Arrange
        AuthorDto authorDto = new AuthorDto(authorDtos.getFirst().id(), null, null, null, null, null, null, null);

        // Act
        Author result = AuthorMapper.getInstance().fromAuthorDtoToAuthor(authorDto);

        // Assert
        assertAll(
                () -> assertNotNull(result, "Resulting Author should not be null"),
                () -> assertEquals(authorDto.id(), result.getId(), "IDs should match"),
                () -> assertNull(result.getName(), "Name should be null"),
                () -> assertNull(result.getNationality(), "Nationality should be null"),
                () -> assertNull(result.getBiographyEs(), "BiographyEs should be null"),
                () -> assertNull(result.getBiographyEn(), "BiographyEn should be null"),
                () -> assertNull(result.getBirthYear(), "BirthYear should be null"),
                () -> assertNull(result.getDeathYear(), "DeathYear should be null"),
                () -> assertNull(result.getSlug(), "Slug should be null")
        );
    }

    @Test
    @DisplayName("Test map Author to AuthorEntity with only id")
    void toAuthorEntity_FromAuthorWithOnlyId() {
        // Arrange
        Author author = new Author(authorEntities.getFirst().id(), null, null, null, null, null, null, null);
        // Act
        AuthorEntity result = AuthorMapper.getInstance().fromAuthorToAuthorEntity(author);
        // Assert
        assertAll(
                () -> assertNotNull(result, "Resulting AuthorEntity should not be null"),
                () -> assertEquals(author.getId(), result.id(), "IDs should match"),
                () -> assertNull(result.name(), "Name should be null"),
                () -> assertNull(result.nationality(), "Nationality should be null"),
                () -> assertNull(result.biographyEs(), "BiographyEs should be null"),
                () -> assertNull(result.biographyEn(), "BiographyEn should be null"),
                () -> assertNull(result.birthYear(), "BirthYear should be null"),
                () -> assertNull(result.deathYear(), "DeathYear should be null"),
                () -> assertNull(result.slug(), "Slug should be null")
        );
    }

}