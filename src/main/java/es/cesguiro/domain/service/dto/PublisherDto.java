package es.cesguiro.domain.service.dto;


import es.cesguiro.domain.validation.spring_validator.Slug;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PublisherDto(
        Long id,
        @NotBlank(message = "Nombre no puede ser nulo o vacío")
        String name,
        @Slug
        String slug
) {
}
