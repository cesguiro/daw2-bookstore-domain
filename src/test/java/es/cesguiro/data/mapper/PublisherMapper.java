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
                parseString(csvRecord.get("name")),
                parseString(csvRecord.get("slug"))
        );
    }

    public static Publisher toPublisher(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        return new Publisher(
                parseString(csvRecord.get("name")),
                parseString(csvRecord.get("slug"))
        );
    }

    public static PublisherEntity toPublisherEntity(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        return new PublisherEntity(
                parseString(csvRecord.get("name")),
                parseString(csvRecord.get("slug"))
        );
    }
}
