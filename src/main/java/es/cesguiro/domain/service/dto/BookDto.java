package es.cesguiro.domain.service.dto;

import es.cesguiro.domain.validation.jvb.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record BookDto(
        Long id,
        @NotNull(message = "ISBN no puede ser nulo")
        String isbn,
        String titleEs,
        String titleEn,
        String synopsisEs,
        String synopsisEn,
        BigDecimal basePrice,
        double discountPercentage,
        BigDecimal price,
        String cover,
        LocalDate publicationDate,
        PublisherDto publisher,
        List<AuthorDto> authors
) {
}
