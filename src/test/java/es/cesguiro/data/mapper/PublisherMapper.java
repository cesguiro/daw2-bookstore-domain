package es.cesguiro.data.mapper;

import es.cesguiro.domain.model.Publisher;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.domain.service.dto.PublisherDto;
import org.apache.commons.csv.CSVRecord;

public class PublisherMapper extends BaseMapper {

    public static PublisherDto toPublisherDto(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        return new PublisherDto(
                parseLong(csvRecord.get("id")),
                parseString(csvRecord.get("name")),
                parseString(csvRecord.get("slug"))
        );
    }

    public static Publisher toPublisher(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        return new Publisher(
                parseLong(csvRecord.get("id")),
                parseString(csvRecord.get("name")),
                parseString(csvRecord.get("slug"))
        );
    }

    public static PublisherEntity toPublisherEntity(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        return new PublisherEntity(
                parseLong(csvRecord.get("id")),
                parseString(csvRecord.get("name")),
                parseString(csvRecord.get("slug"))
        );
    }
}
