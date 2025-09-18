package es.cesguiro.data.mapper;

import es.cesguiro.model.Publisher;
import es.cesguiro.repository.entity.PublisherEntity;
import es.cesguiro.service.dto.PublisherDto;
import org.apache.commons.csv.CSVRecord;

public class PublisherMapper extends BaseMapper {

    public static PublisherDto toPublisherDto(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        return new PublisherDto(
                csvRecord.get("name"),
                csvRecord.get("slug")
        );
    }

    public static Publisher toPublisher(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        return new Publisher(
                csvRecord.get("name"),
                csvRecord.get("slug")
        );
    }

    public static PublisherEntity toPublisherEntity(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        return new PublisherEntity(
                csvRecord.get("name"),
                csvRecord.get("slug")
        );
    }
}
