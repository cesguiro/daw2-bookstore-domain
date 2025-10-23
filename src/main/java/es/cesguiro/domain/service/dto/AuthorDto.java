package es.cesguiro.domain.service.dto;

import es.cesguiro.domain.validation.spring_validator.Slug;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AuthorDto(
        Long id,
        @NotBlank(message = "Nombre no puede ser nulo o vacío")
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
