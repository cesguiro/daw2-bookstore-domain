package es.cesguiro.data.loader;

import es.cesguiro.data.mapper.PublisherMapper;
import es.cesguiro.model.Publisher;
import es.cesguiro.repository.entity.PublisherEntity;
import es.cesguiro.service.dto.PublisherDto;
import org.apache.commons.csv.CSVRecord;

import java.util.List;

public class PublishersDataLoader extends ResourceDataLoader {

    private final List<CSVRecord> publisherRawRecords;

    public PublishersDataLoader() {
        super("publishers.csv");
        publisherRawRecords = loadDataFromCsv();
    }

    public List<PublisherDto> loadPublisherDtosFromCSV() {
        return publisherRawRecords
                .stream()
                .map(PublisherMapper::toPublisherDto)
                .toList();
    }

    public List<Publisher> loadPublishersFromCSV() {
        return publisherRawRecords
                .stream()
                .map(PublisherMapper::toPublisher)
                .toList();
    }

    public List<PublisherEntity> loadPublishersEntitiesFromCSV() {
        return publisherRawRecords
                .stream()
                .map(PublisherMapper::toPublisherEntity)
                .toList();
    }

}
