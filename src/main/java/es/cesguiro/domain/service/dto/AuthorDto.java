package es.cesguiro.domain.service.dto;

import es.cesguiro.domain.validation.hibernate_validator.Slug;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AuthorDto(
        Long id,
        @NotNull(message = "El nombre no puede ser nulo")
        String name,
        String nationality,
        String biographyEs,
        String biographyEn,
        int birthYear,
        Integer deathYear,
        @Slug
        String slug
) {
}
