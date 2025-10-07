package es.cesguiro.domain.model;

import es.cesguiro.domain.exception.BusinessException;
import es.cesguiro.domain.exception.ValidationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Book {

    private final Long id;
    private final String isbn;
    private final String titleEs;
    private final String titleEn;
    private final String synopsisEs;
    private final String synopsisEn;
    private final BigDecimal basePrice;
    private final Double discountPercentage;
    private final BigDecimal price;
    private final String cover;
    private final LocalDate publicationDate;
    private Publisher publisher;
    private List<Author> authors;

    public Book(
            Long id,
            String isbn,
            String titleEs,
            String titleEn,
            String synopsisEs,
            String synopsisEn,
            BigDecimal basePrice,
            Double discountPercentage,
            String cover,
            LocalDate publicationDate,
            Publisher publisher,
            List<Author> authors
    ) {
        this.id = id;
        this.isbn = isbn;
        this.titleEs = titleEs;
        this.titleEn = titleEn;
        this.synopsisEs = synopsisEs;
        this.synopsisEn = synopsisEn;
        this.basePrice = basePrice;
        this.discountPercentage = discountPercentage;
        this.price = calculateFinalPrice();
        this.cover = cover;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
        this.authors = (authors == null) ? new ArrayList<>() : new ArrayList<>(authors);
    }

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }


    public String getTitleEs() {
        return titleEs;
    }


    public String getTitleEn() {
        return titleEn;
    }


    public String getSynopsisEs() {
        return synopsisEs;
    }


    public String getSynopsisEn() {
        return synopsisEn;
    }


    public BigDecimal getBasePrice() {
        return basePrice;
    }


    public Double getDiscountPercentage() {
        return discountPercentage;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public String getCover() {
        return cover;
    }


    public LocalDate getPublicationDate() {
        return publicationDate;
    }


    public BigDecimal calculateFinalPrice() {
        if( basePrice == null ) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal discount = basePrice
                .multiply(BigDecimal.valueOf(discountPercentage))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        return basePrice.subtract(discount).setScale(2, RoundingMode.HALF_UP);
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void addAuthor(Author author) {
        if (this.authors.contains(author)) {
            throw  new BusinessException("Author already exists");
        }
        this.authors.add(author);
    }


}
