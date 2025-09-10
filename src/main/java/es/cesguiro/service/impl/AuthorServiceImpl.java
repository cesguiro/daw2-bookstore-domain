package es.cesguiro.service.impl;

import es.cesguiro.service.dto.AuthorDto;
import es.cesguiro.service.AuthorService;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {

    @Override
    public List<AuthorDto> findAll() {
        return List.of();
    }

    @Override
    public AuthorDto findBySlug(String slug) {
        return null;
    }

    @Override
    public AuthorDto create(AuthorDto authorDto) {
        return null;
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
