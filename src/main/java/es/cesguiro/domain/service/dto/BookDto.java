package es.cesguiro.domain.service.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record BookDto(
        Long id,
        //@NotNull
        @NotNull(message = "ISBN es obligatorio")
        @Pattern(regexp = "\\d{13}", message = "ISBN debe tener 13 dígitos")
        String isbn,
        String titleEs,
        String titleEn,
        String synopsisEs,
        String synopsisEn,
        @NotNull(message = "El precio base no puede ser nulo")
        @DecimalMin(value = "0.0", inclusive = true, message = "El precio base debe ser mayor o igual a 0")
        BigDecimal basePrice,
        @NotNull
        @DecimalMin(value = "0.0", inclusive = true, message = "El descuento no puede ser menor a 0")
        @DecimalMax(value = "100.0", inclusive = true, message = "El descuento no puede ser mayor a 100")
        Double discountPercentage,
        BigDecimal price,
        String cover,
        @PastOrPresent(message = "La fecha de publicación no puede ser futura")
        LocalDate publicationDate,
        PublisherDto publisher,
        List<AuthorDto> authors
) {

    public BookDto(
            Long id,
            String isbn,
            String titleEs,
            String titleEn,
            String synopsisEs,
            String synopsisEn,
            BigDecimal basePrice,
            Double discountPercentage,
            BigDecimal price,
            String cover,
            LocalDate publicationDate,
            PublisherDto publisher,
            List<AuthorDto> authors
    ) {
        this.id = id;
        this.isbn = isbn;
        this.titleEs = titleEs;
        this.titleEn = titleEn;
        this.synopsisEs = synopsisEs;
        this.synopsisEn = synopsisEn;
        this.basePrice = basePrice;
        this.discountPercentage = discountPercentage;
        this.price = price;
        this.cover = cover;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
        if(authors == null) {
            this.authors = List.of();
        } else {
            this.authors = List.copyOf(authors);
        }
    }
}
