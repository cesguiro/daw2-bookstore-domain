package es.cesguiro.domain.service.dto;


import es.cesguiro.domain.validation.spring_validator.Slug;
import jakarta.validation.constraints.NotNull;

public record PublisherDto(
        Long id,
        @NotNull(message = "Nombre no puede ser nulo")
        String name,
        @Slug
        String slug
) {
}
