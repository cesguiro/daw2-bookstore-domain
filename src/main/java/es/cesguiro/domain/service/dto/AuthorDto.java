package es.cesguiro.domain.service.dto;

import es.cesguiro.domain.validation.spring_validator.Slug;
import jakarta.validation.constraints.NotNull;

public record AuthorDto(
        Long id,
        @NotNull(message = "El nombre no puede ser nulo")
        String name,
        String nationality,
        String biographyEs,
        String biographyEn,
        Integer birthYear,
        Integer deathYear,
        @Slug
        String slug
) {
}
