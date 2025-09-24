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
                parseLong(csvRecord.get("id")),
                parseString(csvRecord.get("name")),
                parseString(csvRecord.get("nationality")),
                parseString(csvRecord.get("biography_es")),
                parseString(csvRecord.get("biography_en")),
                parseInt(csvRecord.get("birth_year")),
                parseInt(csvRecord.get("death_year")),
                parseString(csvRecord.get("slug"))
        );
    }

    public static Author toAuthor(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        return new Author(
                parseLong(csvRecord.get("id")),
                parseString(csvRecord.get("name")),
                parseString(csvRecord.get("nationality")),
                parseString(csvRecord.get("biography_es")),
                parseString(csvRecord.get("biography_en")),
                parseInt(csvRecord.get("birth_year")),
                parseInt(csvRecord.get("death_year")),
                parseString(csvRecord.get("slug"))
        );
    }

    public static AuthorEntity toAuthorEntity(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        return new AuthorEntity(
                parseLong(csvRecord.get("id")),
                parseString(csvRecord.get("name")),
                parseString(csvRecord.get("nationality")),
                parseString(csvRecord.get("biography_es")),
                parseString(csvRecord.get("biography_en")),
                parseInt(csvRecord.get("birth_year")),
                parseInt(csvRecord.get("death_year")),
                parseString(csvRecord.get("slug"))
        );
    }


}
