package es.cesguiro.mapper;

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
    @DisplayName("Test map null AuthorEntity to Author throws BusinessException")
    void toAuthor_NullAuthorEntity_ThrowsBusinessException() {
        // Arrange
        AuthorEntity authorEntity = null;
        // Act & Assert
        var exception = assertThrows(BusinessException.class, () -> AuthorMapper.getInstance().fromAuthorEntityToAuthor(authorEntity));
        assertEquals("AuthorEntity cannot be null", exception.getMessage());
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

}