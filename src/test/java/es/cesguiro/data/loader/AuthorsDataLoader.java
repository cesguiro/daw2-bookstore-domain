package es.cesguiro.data.loader;

import es.cesguiro.data.mapper.AuthorMapper;
import es.cesguiro.model.Author;
import es.cesguiro.repository.entity.AuthorEntity;
import es.cesguiro.service.dto.AuthorDto;
import org.apache.commons.csv.CSVRecord;

import java.util.List;

public class AuthorsDataLoader extends ResourceDataLoader {

    private final List<CSVRecord> authorRawRecords;

    public AuthorsDataLoader() {
        super("authors.csv");
        this.authorRawRecords = loadDataFromCsv();
    }


    public List<AuthorDto> loadAuthorDtosFromCSV() {
        return authorRawRecords
                .stream()
                .map(AuthorMapper::toAuthorDto)
                .toList();
    }

    public List<Author> loadAuthorsFromCSV() {
        return authorRawRecords
                .stream()
                .map(AuthorMapper::toAuthor)
                .toList();
    }

    public List<AuthorEntity> loadAuthorEntitiesFromCSV() {
        return authorRawRecords
                .stream()
                .map(AuthorMapper::toAuthorEntity)
                .toList();
    }

}
