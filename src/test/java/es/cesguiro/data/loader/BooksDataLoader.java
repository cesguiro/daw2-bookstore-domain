package es.cesguiro.data.loader;

import es.cesguiro.data.mapper.BookMapper;
import es.cesguiro.model.Book;
import es.cesguiro.repository.entity.BookEntity;
import es.cesguiro.service.dto.BookDto;
import org.apache.commons.csv.CSVRecord;

import java.util.List;

public class BooksDataLoader extends ResourceDataLoader {

    private final List<CSVRecord> bookRawRecords;

    public BooksDataLoader() {
        super("books.csv");
        bookRawRecords = loadDataFromCsv();
    }


    public List<BookDto> loadBookDtosFromCSV() {
        return bookRawRecords
                .stream()
                .map(BookMapper::toBookDto)
                .toList();
    }

    public List<Book> loadBooksFromCSV() {
        return bookRawRecords
                .stream()
                .map(BookMapper::toBook)
                .toList();
    }

    public List<BookEntity> loadBookEntitiesFromCSV() {
        return bookRawRecords
                .stream()
                .map(BookMapper::toBookEntity)
                .toList();
    }

}
