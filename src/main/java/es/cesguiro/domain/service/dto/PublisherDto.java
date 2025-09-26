package es.cesguiro.domain.service.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PublisherDto(
        Long id,
        @NotNull(message = "Nombre no puede ser nulo")
        String name,
        @NotNull(message = "Slug no puede ser nulo")
        @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "Slug inválido, debe ser URL-friendly (minúsculas, números y guiones)")
        String slug
) {
}
