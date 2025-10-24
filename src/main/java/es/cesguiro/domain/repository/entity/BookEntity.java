package es.cesguiro.domain.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record BookEntity(
        Long id,
        String isbn,
        String titleEs,
        String titleEn,
        String synopsisEs,
        String synopsisEn,
        BigDecimal basePrice,
        BigDecimal discountPercentage,
        String cover,
        LocalDate publicationDate,
        PublisherEntity publisher,
        List<AuthorEntity> authors
) {

    public BookEntity(
            Long id,
            String isbn,
            String titleEs,
            String titleEn,
            String synopsisEs,
            String synopsisEn,
            BigDecimal basePrice,
            BigDecimal discountPercentage,
            String cover,
            LocalDate publicationDate,
            PublisherEntity publisher,
            List<AuthorEntity> authors
    ) {
        this.id = id;
        this.isbn = isbn;
        this.titleEs = titleEs;
        this.titleEn = titleEn;
        this.synopsisEs = synopsisEs;
        this.synopsisEn = synopsisEn;
        this.basePrice = basePrice;
        this.discountPercentage = discountPercentage;
        this.cover = cover;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
        this.authors = authors == null ? List.of() : List.copyOf(authors);
    }
}
