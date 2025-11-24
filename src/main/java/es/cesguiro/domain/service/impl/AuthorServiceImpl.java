package es.cesguiro.domain.service.impl;

import es.cesguiro.domain.exception.BusinessException;
import es.cesguiro.domain.exception.ValidationException;
import es.cesguiro.domain.mapper.AuthorMapper;
import es.cesguiro.domain.model.Author;
import es.cesguiro.domain.repository.AuthorRepository;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.service.dto.AuthorDto;
import es.cesguiro.domain.service.AuthorService;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDto> getAll(int page, int size) {
        return List.of();
    }

    @Override
    public AuthorDto getBySlug(String slug) {
        return null;
    }

    @Override
    public AuthorDto create(AuthorDto authorDto) {
        if (authorRepository.findBySlug(authorDto.slug()).isPresent()) {
            throw new ValidationException("Author with slug " + authorDto.slug() + " already exists");
        }
        AuthorEntity authorEntity = AuthorMapper.getInstance().fromAuthorToAuthorEntity(
                AuthorMapper.getInstance().fromAuthorDtoToAuthor(authorDto)
        );
        AuthorEntity newAuthorEntity = authorRepository.save(authorEntity);
        return AuthorMapper.getInstance().fromAuthorToAuthorDto(
                AuthorMapper.getInstance().fromAuthorEntityToAuthor(newAuthorEntity)
        );
    }

    @Override
    public AuthorDto update(AuthorDto authorDto) {
        return null;
    }

    @Override
    public int delete(String slug) {
        return 0;
    }
}
