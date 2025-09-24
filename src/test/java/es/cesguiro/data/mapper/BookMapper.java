package es.cesguiro.data.mapper;

import es.cesguiro.data.loader.AuthorsDataLoader;
import es.cesguiro.data.loader.BooksAuthorsDataLoader;
import es.cesguiro.data.loader.PublishersDataLoader;
import es.cesguiro.domain.model.Author;
import es.cesguiro.domain.model.Book;
import es.cesguiro.domain.model.Publisher;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.domain.service.dto.AuthorDto;
import es.cesguiro.domain.service.dto.BookDto;
import es.cesguiro.domain.service.dto.PublisherDto;
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
        PublisherDto publisherRecord = PublisherMapper.toPublisherDto(getPublisherCsvRecord(parseLong(csvRecord.get("publisher_id"))));
        List<AuthorDto> authorRecords;
        List<CSVRecord> authorCsvRecords = getAuthorCsvRecords(parseLong(csvRecord.get("id")));
        if(authorCsvRecords == null) {
            authorRecords = null;
        } else {
            authorRecords = authorCsvRecords.stream().map(AuthorMapper::toAuthorDto).collect(Collectors.toCollection(ArrayList::new));;
        }
        return new BookDto(
                parseLong(csvRecord.get("id")),
                parseString(csvRecord.get("isbn")),
                parseString(csvRecord.get("title_es")),
                parseString(csvRecord.get("title_en")),
                parseString(csvRecord.get("synopsis_es")),
                parseString(csvRecord.get("synopsis_en")),
                parseBigDecimal(csvRecord.get("base_price")),
                parseDouble(csvRecord.get("discount_percentage")),
                parseBigDecimal(csvRecord.get("price")),
                parseString(csvRecord.get("cover")),
                parseDate(csvRecord.get("publication_date")),
                publisherRecord,
                authorRecords
        );
    }

    public static Book toBook(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        Publisher publisher = PublisherMapper.toPublisher(getPublisherCsvRecord(parseLong(csvRecord.get("publisher_id"))));
        List<CSVRecord> authorCsvRecords = getAuthorCsvRecords(parseLong(csvRecord.get("id")));
        List<Author> authors;
        if(authorCsvRecords == null) {
            authors = null;
        } else {
            authors = authorCsvRecords.stream().map(AuthorMapper::toAuthor).collect(Collectors.toCollection(ArrayList::new));;
        }
        return new Book(
                parseLong(csvRecord.get("id")),
                parseString(csvRecord.get("isbn")),
                parseString(csvRecord.get("title_es")),
                parseString(csvRecord.get("title_en")),
                parseString(csvRecord.get("synopsis_es")),
                parseString(csvRecord.get("synopsis_en")),
                parseBigDecimal(csvRecord.get("base_price")),
                parseDouble(csvRecord.get("discount_percentage")),
                parseString(csvRecord.get("cover")),
                parseDate(csvRecord.get("publication_date")),
                publisher,
                authors
        );
    }

    public static BookEntity toBookEntity(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        PublisherEntity publisher = PublisherMapper.toPublisherEntity(getPublisherCsvRecord(parseLong(csvRecord.get("publisher_id"))));
        List<CSVRecord> authorCsvRecords = getAuthorCsvRecords(parseLong(csvRecord.get("id")));
        List<AuthorEntity> authors;
        if(authorCsvRecords == null) {
            authors = null;
        } else {
            authors = authorCsvRecords.stream().map(AuthorMapper::toAuthorEntity).collect(Collectors.toCollection(ArrayList::new));;
        }
        return new BookEntity(
                parseLong(csvRecord.get("id")),
                parseString(csvRecord.get("isbn")),
                parseString(csvRecord.get("title_es")),
                parseString(csvRecord.get("title_en")),
                parseString(csvRecord.get("synopsis_es")),
                parseString(csvRecord.get("synopsis_en")),
                parseBigDecimal(csvRecord.get("base_price")),
                parseDouble(csvRecord.get("discount_percentage")),
                parseString(csvRecord.get("cover")),
                parseDate(csvRecord.get("publication_date")),
                publisher,
                authors
        );
    }


    private static CSVRecord getPublisherCsvRecord(Long id) {
        if (id == null) {
            return null;
        }
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
