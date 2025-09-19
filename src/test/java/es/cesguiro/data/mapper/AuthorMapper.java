package es.cesguiro.data.mapper;

import es.cesguiro.domain.model.Author;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.service.dto.AuthorDto;
import org.apache.commons.csv.CSVRecord;

public class AuthorMapper extends BaseMapper {

    public static AuthorDto toAuthorDto(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        return new AuthorDto(
                csvRecord.get("name"),
                csvRecord.get("nationality"),
                csvRecord.get("biography_es"),
                csvRecord.get("biography_en"),
                Integer.parseInt(csvRecord.get("birth_year")),
                parseInt(csvRecord.get("death_year")) != null ? Integer.parseInt(csvRecord.get("death_year")) : 0,
                csvRecord.get("slug")
        );
    }

    public static Author toAuthor(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        return new Author(
                csvRecord.get("name"),
                csvRecord.get("nationality"),
                csvRecord.get("biography_es"),
                csvRecord.get("biography_en"),
                Integer.parseInt(csvRecord.get("birth_year")),
                parseInt(csvRecord.get("death_year")) != null ? Integer.parseInt(csvRecord.get("death_year")) : 0,
                csvRecord.get("slug")
        );
    }

    public static AuthorEntity toAuthorEntity(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        return new AuthorEntity(
                csvRecord.get("name"),
                csvRecord.get("nationality"),
                csvRecord.get("biography_es"),
                csvRecord.get("biography_en"),
                Integer.parseInt(csvRecord.get("birth_year")),
                parseInt(csvRecord.get("death_year")) != null ? Integer.parseInt(csvRecord.get("death_year")) : 0,
                csvRecord.get("slug")
        );
    }


}
