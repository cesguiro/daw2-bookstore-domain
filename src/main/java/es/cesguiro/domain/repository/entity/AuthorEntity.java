package es.cesguiro.domain.repository.entity;

public record AuthorEntity(
        Long id,
        String name,
        String nationality,
        String biographyEs,
        String biographyEn,
        Integer birthYear,
        Integer deathYear,
        String slug
) {

}
