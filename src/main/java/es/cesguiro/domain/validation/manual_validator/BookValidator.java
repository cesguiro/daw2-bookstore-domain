package es.cesguiro.domain.validation.manual_validator;

import es.cesguiro.domain.exception.ValidationException;
import es.cesguiro.domain.service.dto.BookDto;

import java.lang.reflect.Field;
import java.util.List;

public class BookValidator extends Validator<BookDto>{


    @Override
    public List<String> validate(BookDto bookDto) {

        try {
            Field isbnField = BookDto.class.getDeclaredField("isbn");
            this.notNull(bookDto, isbnField);
        } catch (Exception e) {
            throw new ValidationException("ISBN is required");
        }
        return getErrors();
    }
}
