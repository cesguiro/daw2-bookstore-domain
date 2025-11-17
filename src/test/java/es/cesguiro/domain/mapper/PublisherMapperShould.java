package es.cesguiro.domain.mapper;


import es.cesguiro.domain.model.Publisher;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.domain.service.dto.PublisherDto;
import es.cesguiro.util.InstancioModel;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class PublisherMapperShould {

    @Test
    void map_PublisherEntity_to_Publisher() {
        PublisherEntity publisherEntity = Instancio.of(InstancioModel.PUBLISHER_ENTITY_MODEL).withSeed(39).create();
        Publisher expected = Instancio.of(InstancioModel.PUBLISHER_MODEL).withSeed(39).create();

        Publisher result = PublisherMapper.getInstance().fromPublisherEntityToPublisher(publisherEntity);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void map_Publisher_to_PublisherEntity() {
        Publisher publisher = Instancio.of(InstancioModel.PUBLISHER_MODEL).withSeed(39).create();
        PublisherEntity expected = Instancio.of(InstancioModel.PUBLISHER_ENTITY_MODEL).withSeed(39).create();

        PublisherEntity result = PublisherMapper.getInstance().fromPublisherToPublisherEntity(publisher);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);    }

    @Test
    void map_Publisher_to_PublisherDto() {
        Publisher publisher = Instancio.of(InstancioModel.PUBLISHER_MODEL).withSeed(39).create();
        PublisherDto expected = Instancio.of(InstancioModel.PUBLISHER_DTO_MODEL).withSeed(39).create();

        PublisherDto result = PublisherMapper.getInstance().fromPublisherToPublisherDto(publisher);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);    }

    @Test
    void map_PublisherDto_to_Publisher() {
        PublisherDto publisherDto = Instancio.of(InstancioModel.PUBLISHER_DTO_MODEL).withSeed(39).create();
        Publisher expected = Instancio.of(InstancioModel.PUBLISHER_MODEL).withSeed(39).create();

        Publisher result = PublisherMapper.getInstance().fromPublisherDtoToPublisher(publisherDto);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);    }


    @ParameterizedTest
    @MethodSource("nullMappingMethods")
    void should_return_null_when_input_is_null(UnaryOperator<Object> mapperFunction) {
        assertThat(mapperFunction.apply(null)).isNull();
    }

    private static Stream<UnaryOperator<Object>> nullMappingMethods() {
        PublisherMapper mapper = PublisherMapper.getInstance();
        return Stream.of(
                obj -> mapper.fromPublisherEntityToPublisher((PublisherEntity) obj),
                obj -> mapper.fromPublisherToPublisherEntity((Publisher) obj),
                obj -> mapper.fromPublisherDtoToPublisher((PublisherDto) obj),
                obj -> mapper.fromPublisherToPublisherDto((Publisher) obj)
        );
    }


    /*@Test
    void return_null_when_model_to_map_is_null() {
        assertThat(PublisherMapper.getInstance().fromPublisherEntityToPublisher(null)).isNull();
        assertThat(PublisherMapper.getInstance().fromPublisherToPublisherEntity(null)).isNull();
        assertThat(PublisherMapper.getInstance().fromPublisherDtoToPublisher(null)).isNull();
        assertThat(PublisherMapper.getInstance().fromPublisherToPublisherDto(null)).isNull();

    }*/

}
