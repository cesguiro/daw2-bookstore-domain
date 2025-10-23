package es.cesguiro.domain.mapper;


import es.cesguiro.domain.model.Publisher;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.domain.service.dto.PublisherDto;
import es.cesguiro.utils.TestDataFactory;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

public class PublisherMapperTest {

    private final TestDataFactory testDataFactory = new TestDataFactory(null);

    @Test
    @DisplayName("Map PublisherEntity to Publisher should return correct Publisher")
    void fromPublisherEntityToPublisherTest() {
        /*PublisherEntity publisherEntity = Instancio.of(PublisherEntity.class)
                .generate(field(PublisherEntity.class,"slug"), gen -> gen.text().pattern("#c#c#c-#c#c#c"))
                .create();*/
        PublisherEntity publisherEntity = testDataFactory.of(PublisherEntity.class);
        var result = PublisherMapper.getInstance().fromPublisherEntityToPublisher(publisherEntity);
        assertAll(
                () -> assertEquals(publisherEntity.id(), result.getId(), "ID should match"),
                () -> assertEquals(publisherEntity.name(), result.getName(), "Name should match"),
                () -> assertEquals(publisherEntity.slug(), result.getSlug(), "Slug should match")
        );
    }

    @Test
    @DisplayName("Map Publisher to PublisherEntity should return correct PublisherEntity")
    void fromPublisherToPublisherEntityTest() {
        /*Publisher publisher = Instancio.of(Publisher.class)
                .generate(field(Publisher.class,"slug"), gen -> gen.text().pattern("#c#c#c-#c#c#c"))
                .create();*/
        Publisher publisher = testDataFactory.of(Publisher.class);
        var result = PublisherMapper.getInstance().fromPublisherToPublisherEntity(publisher);
        assertAll(
                () -> assertEquals(publisher.getId(), result.id(), "ID should match"),
                () -> assertEquals(publisher.getName(), result.name(), "Name should match"),
                () -> assertEquals(publisher.getSlug(), result.slug(), "Slug should match")
        );
    }

    @Test
    @DisplayName("Map Publisher to PublisherDto should return correct PublisherDto")
    void fromPublishertoPublisherDtoTest() {
        /*Publisher publisher = Instancio.of(Publisher.class)
                .generate(field(Publisher.class,"slug"), gen -> gen.text().pattern("#c#c#c-#c#c#c"))
                .create();*/
        Publisher publisher = testDataFactory.of(Publisher.class);
        var result = PublisherMapper.getInstance().fromPublisherToPublisherDto(publisher);

        assertAll(
                () -> assertEquals(publisher.getId(), result.id(), "ID should match"),
                () -> assertEquals(publisher.getName(), result.name(), "Name should match"),
                () -> assertEquals(publisher.getSlug(), result.slug(), "Slug should match")
        );
    }

    @Test
    @DisplayName("Map PublisherDto to Publisher should return correct Publisher")
    void fromPublisherDtoToPublisherTest() {
        /*PublisherDto publisherDto = Instancio.of(PublisherDto.class)
                .generate(field(PublisherDto.class,"slug"), gen -> gen.text().pattern("#c#c#c-#c#c#c"))
                .create();*/
        PublisherDto publisherDto = testDataFactory.of(PublisherDto.class);
        var result = PublisherMapper.getInstance().fromPublisherDtoToPublisher(publisherDto);

        assertAll(
                () -> assertEquals(publisherDto.id(), result.getId(), "ID should match"),
                () -> assertEquals(publisherDto.name(), result.getName(), "Name should match"),
                () -> assertEquals(publisherDto.slug(), result.getSlug(), "Slug should match")
        );
    }

    @Test
    @DisplayName("Mapping null Publisher should return null")
    void mapNullPublisherShouldReturnNull() {
        assertAll(
                () -> assertNull(PublisherMapper.getInstance().fromPublisherEntityToPublisher(null), "Mapping null PublisherEntity should return null Publisher"),
                () -> assertNull(PublisherMapper.getInstance().fromPublisherToPublisherEntity(null), "Mapping null Publisher should return null PublisherEntity"),
                () -> assertNull(PublisherMapper.getInstance().fromPublisherDtoToPublisher(null), "Mapping null PublisherDto should return null Publisher"),
                () -> assertNull(PublisherMapper.getInstance().fromPublisherToPublisherDto(null), "Mapping null Publisher should return null PublisherDto")
        );
    }

}
