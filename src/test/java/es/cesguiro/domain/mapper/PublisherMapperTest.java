package es.cesguiro.domain.mapper;


import es.cesguiro.domain.model.Publisher;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.domain.service.dto.PublisherDto;
import es.cesguiro.utils.InstancioModel;
import es.cesguiro.utils.TestDataFactory;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PublisherMapperTest {

    @Test
    @DisplayName("Map PublisherEntity to Publisher should return correct Publisher")
    void fromPublisherEntityToPublisherTest() {
        PublisherEntity publisherEntity = Instancio.of(InstancioModel.PUBLISHER_ENTITY_MODEL).withSeed(39).create();
        Publisher expected = Instancio.of(InstancioModel.PUBLISHER_MODEL).withSeed(39).create();
        Publisher result = PublisherMapper.getInstance().fromPublisherEntityToPublisher(publisherEntity);
        assertAll(
                () -> assertEquals(expected.getId(), result.getId(), "ID should match"),
                () -> assertEquals(expected.getName(), result.getName(), "Name should match"),
                () -> assertEquals(expected.getSlug(), result.getSlug(), "Slug should match")
        );
    }

    @Test
    @DisplayName("Map Publisher to PublisherEntity should return correct PublisherEntity")
    void fromPublisherToPublisherEntityTest() {
        Publisher publisher = Instancio.of(InstancioModel.PUBLISHER_MODEL).withSeed(39).create();
        PublisherEntity expected = Instancio.of(InstancioModel.PUBLISHER_ENTITY_MODEL).withSeed(39).create();
        PublisherEntity result = PublisherMapper.getInstance().fromPublisherToPublisherEntity(publisher);
        assertAll(
                () -> assertEquals(expected.id(), result.id(), "ID should match"),
                () -> assertEquals(expected.name(), result.name(), "Name should match"),
                () -> assertEquals(expected.slug(), result.slug(), "Slug should match")
        );
    }

    @Test
    @DisplayName("Map Publisher to PublisherDto should return correct PublisherDto")
    void fromPublishertoPublisherDtoTest() {
        Publisher publisher = Instancio.of(InstancioModel.PUBLISHER_MODEL).withSeed(39).create();
        PublisherDto expected = Instancio.of(InstancioModel.PUBLISHER_DTO_MODEL).withSeed(39).create();
        PublisherDto result = PublisherMapper.getInstance().fromPublisherToPublisherDto(publisher);
        assertAll(
                () -> assertEquals(expected.id(), result.id(), "ID should match"),
                () -> assertEquals(expected.name(), result.name(), "Name should match"),
                () -> assertEquals(expected.slug(), result.slug(), "Slug should match")
        );
    }

    @Test
    @DisplayName("Map PublisherDto to Publisher should return correct Publisher")
    void fromPublisherDtoToPublisherTest() {
        PublisherDto publisherDto = Instancio.of(InstancioModel.PUBLISHER_DTO_MODEL).withSeed(39).create();
        Publisher expected = Instancio.of(InstancioModel.PUBLISHER_MODEL).withSeed(39).create();
        Publisher result = PublisherMapper.getInstance().fromPublisherDtoToPublisher(publisherDto);
        assertAll(
                () -> assertEquals(expected.getId(), result.getId(), "ID should match"),
                () -> assertEquals(expected.getName(), result.getName(), "Name should match"),
                () -> assertEquals(expected.getSlug(), result.getSlug(), "Slug should match")
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
