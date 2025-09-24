package es.cesguiro.domain.mapper;

import es.cesguiro.domain.exception.BusinessException;
import es.cesguiro.domain.model.Author;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.service.dto.AuthorDto;

public class AuthorMapper {

    private  static AuthorMapper instance;

    private AuthorMapper() {}

    public static AuthorMapper getInstance() {
        if (instance == null) {
            instance = new AuthorMapper();
        }
        return instance;
    }

    public Author fromAuthorEntityToAuthor(AuthorEntity authorEntity) {
        if (authorEntity == null) {
            return null;
        }
        return new Author(
                authorEntity.id(),
                authorEntity.name(),
                authorEntity.nationality(),
                authorEntity.biographyEs(),
                authorEntity.biographyEn(),
                authorEntity.birthYear(),
                authorEntity.deathYear(),
                authorEntity.slug()
        );
    }

    public AuthorEntity fromAuthorToAuthorEntity(Author author) {
        if (author == null) {
            return null;
        }
        return new AuthorEntity(
                author.getId(),
                author.getName(),
                author.getNationality(),
                author.getBiographyEs(),
                author.getBiographyEn(),
                author.getBirthYear(),
                author.getDeathYear(),
                author.getSlug()
        );
    }

    public AuthorDto fromAuthorToAuthorDto(Author author) {
        if (author == null) {
            return null;
        }
        return new AuthorDto(
                author.getId(),
                author.getName(),
                author.getNationality(),
                author.getBiographyEs(),
                author.getBiographyEn(),
                author.getBirthYear(),
                author.getDeathYear(),
                author.getSlug()
        );
    }

    public Author fromAuthorDtoToAuthor(AuthorDto authorDto) {
        if (authorDto == null) {
            return null;
        }
        return new Author(
                authorDto.id(),
                authorDto.name(),
                authorDto.nationality(),
                authorDto.biographyEs(),
                authorDto.biographyEn(),
                authorDto.birthYear(),
                authorDto.deathYear(),
                authorDto.slug()
        );
    }

}
