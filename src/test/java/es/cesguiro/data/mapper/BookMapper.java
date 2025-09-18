package es.cesguiro.data.mapper;

import es.cesguiro.data.loader.AuthorsDataLoader;
import es.cesguiro.data.loader.BooksAuthorsDataLoader;
import es.cesguiro.data.loader.PublishersDataLoader;
import es.cesguiro.model.Author;
import es.cesguiro.model.Book;
import es.cesguiro.model.Publisher;
import es.cesguiro.repository.entity.AuthorEntity;
import es.cesguiro.repository.entity.BookEntity;
import es.cesguiro.repository.entity.PublisherEntity;
import es.cesguiro.service.dto.AuthorDto;
import es.cesguiro.service.dto.BookDto;
import es.cesguiro.service.dto.PublisherDto;
import org.apache.commons.csv.CSVRecord;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookMapper extends BaseMapper{

    private static final PublishersDataLoader publishersDataLoader = new PublishersDataLoader();
    private static final AuthorsDataLoader authorsDataLoader = new AuthorsDataLoader();

    public static BookDto toBookDto(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        long id = Long.parseLong(csvRecord.get("id"));
        PublisherDto publisherRecord = PublisherMapper.toPublisherDto(getPublisherCsvRecord(Long.parseLong(csvRecord.get("publisher_id"))));
        List<AuthorDto> authorRecords;
        List<CSVRecord> authorCsvRecords = getAuthorCsvRecords(id);
        if(authorCsvRecords == null) {
            authorRecords = null;
        } else {
            authorRecords = authorCsvRecords.stream().map(AuthorMapper::toAuthorDto).collect(Collectors.toCollection(ArrayList::new));;
        }
        return new BookDto(
                csvRecord.get("isbn"),
                csvRecord.get("title_es"),
                csvRecord.get("title_en"),
                csvRecord.get("synopsis_es"),
                csvRecord.get("synopsis_en"),
                new BigDecimal(csvRecord.get("base_price")),
                Double.parseDouble(csvRecord.get("discount_percentage")),
                new BigDecimal(csvRecord.get("price")),
                csvRecord.get("cover"),
                parseDate(csvRecord.get("publication_date")),
                publisherRecord,
                authorRecords
        );
    }

    public static Book toBook(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        Publisher publisher = PublisherMapper.toPublisher(getPublisherCsvRecord(Long.parseLong(csvRecord.get("publisher_id"))));
        List<CSVRecord> authorCsvRecords = getAuthorCsvRecords(Long.parseLong(csvRecord.get("id")));
        List<Author> authors;
        if(authorCsvRecords == null) {
            authors = null;
        } else {
            authors = authorCsvRecords.stream().map(AuthorMapper::toAuthor).collect(Collectors.toCollection(ArrayList::new));;
        }
        Book book = new Book(
                csvRecord.get("isbn"),
                csvRecord.get("title_es"),
                csvRecord.get("title_en"),
                csvRecord.get("synopsis_es"),
                csvRecord.get("synopsis_en"),
                new BigDecimal(csvRecord.get("base_price")),
                Double.parseDouble(csvRecord.get("discount_percentage")),
                csvRecord.get("cover"),
                parseDate(csvRecord.get("publication_date")),
                publisher,
                authors
        );
        return book;
    }

    public static BookEntity toBookEntity(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        PublisherEntity publisher = PublisherMapper.toPublisherEntity(getPublisherCsvRecord(Long.parseLong(csvRecord.get("publisher_id"))));
        List<CSVRecord> authorCsvRecords = getAuthorCsvRecords(Long.parseLong(csvRecord.get("id")));
        List<AuthorEntity> authors;
        if(authorCsvRecords == null) {
            authors = null;
        } else {
            authors = authorCsvRecords.stream().map(AuthorMapper::toAuthorEntity).collect(Collectors.toCollection(ArrayList::new));;
        }
        BookEntity bookEntity = new BookEntity(
                csvRecord.get("isbn"),
                csvRecord.get("title_es"),
                csvRecord.get("title_en"),
                csvRecord.get("synopsis_es"),
                csvRecord.get("synopsis_en"),
                new BigDecimal(csvRecord.get("base_price")),
                Double.parseDouble(csvRecord.get("discount_percentage")),
                csvRecord.get("cover"),
                parseDate(csvRecord.get("publication_date")),
                publisher,
                authors
        );
        return bookEntity;
    }


    private static CSVRecord getPublisherCsvRecord(Long id) {
        return publishersDataLoader.findCsvRecordById(id).orElse(null);
    }

    private static List<CSVRecord> getAuthorCsvRecords(Long id) {
        BooksAuthorsDataLoader booksAuthorsDataLoader = new BooksAuthorsDataLoader();
        Long[] authorIds = booksAuthorsDataLoader.getAllAuthorIdsByBookId(id);
        if (authorIds.length == 0) {
            return null;
        }
        return authorsDataLoader.findAllCsvRecordsByIds(authorIds);
    }

}
