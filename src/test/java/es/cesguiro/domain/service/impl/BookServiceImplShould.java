package es.cesguiro.domain.service.impl;

import es.cesguiro.domain.exception.BusinessException;
import es.cesguiro.domain.exception.ResourceNotFoundException;
import es.cesguiro.domain.model.Page;
import es.cesguiro.domain.repository.AuthorRepository;
import es.cesguiro.domain.repository.BookRepository;
import es.cesguiro.domain.repository.PublisherRepository;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.domain.service.dto.BookDto;
import es.cesguiro.util.InstancioModel;
import org.instancio.Instancio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.instancio.Select.field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplShould {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;


    @ParameterizedTest
    @CsvSource({
            "1, 2, 2, 5",
            "1, 5, 5, 5",
            "1, 5, 3, 3",
            "2, 4, 4, 10",
            "2, 4, 4, 4",
            "2, 4, 3, 3"
    })
    void return_page_of_BookDto_when_getAll_is_called_with_valid_arguments(int page, int size, int elementsToCreate, long totalElements) {
        List<BookEntity> bookEntities = Instancio.ofList(InstancioModel.BOOK_ENTITY_MODEL)
                .size(elementsToCreate)
                .withSeed(10)
                .create();
        List<BookDto> expectedBookDtos = Instancio.ofList(InstancioModel.BOOK_DTO_MODEL)
                .size(elementsToCreate)
                .withSeed(10)
                .ignore(field(BookDto::price))
                .lenient()
                .create();

        when(bookRepository.findAll(page, size)).thenReturn(new Page<>(bookEntities, page, size, totalElements));
        Page<BookDto> expected = new Page<>(expectedBookDtos, page, size, totalElements);

        Page<BookDto> result = bookServiceImpl.getAll(page, size);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("data.price")
                .isEqualTo(expected);
    }

    @Test
    void return_empty_page_when_getAll_finds_no_books() {
        when(bookRepository.findAll(anyInt(), anyInt())).thenReturn(new Page<>(List.of(), 1, 10, 0));

        Page<BookDto> result = bookServiceImpl.getAll(1, 10);

        assertThat(result.data()).isEmpty();
        assertThat(result.totalElements()).isZero();
        assertThat(result.totalPages()).isZero();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 10",
            "-1, 10",
    })
    void throw_IllegalArgumentException_when_getAll_receives_invalid_page(int page, int size) {
        assertThatThrownBy(() -> bookServiceImpl.getAll(page, size))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 0",
            "1, -5",
    })
    void throw_IllegalArgumentException_when_getAll_receives_invalid_size(int page, int size) {
        assertThatThrownBy(() -> bookServiceImpl.getAll(page, size))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void return_BookDto_when_getByIsbn_finds_a_book() {
        BookEntity bookEntity = Instancio.of(InstancioModel.BOOK_ENTITY_MODEL).withSeed(10).create();
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.of(bookEntity));
        BookDto expected = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .withSeed(10)
                .ignore(field(BookDto::price))
                .lenient()
                .create();

        BookDto result = bookServiceImpl.getByIsbn(bookEntity.isbn());

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("price")
                .isEqualTo(expected);
    }

    // test getByIsbn when book does not exist
    @Test
    void throw_ResourceNotFoundException_when_getByIsbn_finds_no_book() {
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookServiceImpl.getByIsbn("non-existing-isbn"))
                .isInstanceOf(ResourceNotFoundException.class);
    }


    // test findByIsbn when book exists
    @Test
    void return_Optional_of_BookDto_when_findByIsbn_finds_a_book() {
        BookEntity bookEntity = Instancio.of(InstancioModel.BOOK_ENTITY_MODEL).withSeed(20).create();
        when(bookRepository.findByIsbn(anyString())).thenReturn(java.util.Optional.of(bookEntity));
        BookDto expected = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .withSeed(20)
                .ignore(field(BookDto::price))
                .lenient()
                .create();

        Optional<BookDto> result = bookServiceImpl.findByIsbn("some-isbn");

        assertThat(result)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringFields("price")
                .isEqualTo(expected);
    }

    @Test
    void return_empty_optional_when_findByIsbn_finds_no_book() {
        String isbn = "non-existing-isbn";
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.empty());

        Optional<BookDto> result = bookServiceImpl.findByIsbn(isbn);

        assertThat(result).isNotPresent();
    }

    @Test
    void return_created_BookDto_when_create_book_with_valid_data() {
        Long newId = 19L;
        BookDto newBookDto = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .withSeed(50)
                .ignore(field(BookDto::id))
                .ignore(field(BookDto::price))
                .lenient()
                .create();

        BookEntity newBookEntity = Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                .withSeed(50)
                .set(field(BookEntity::id), newId)
                .lenient()
                .create();

        when(bookRepository.findByIsbn(newBookDto.isbn())).thenReturn(Optional.empty());
        when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(newBookEntity.publisher()));
        for (int i = 0; i < newBookDto.authors().size(); i++) {
            when(authorRepository.findById(newBookDto.authors().get(i).id()))
                    .thenReturn(Optional.of(newBookEntity.authors().get(i)));
        }
        when(bookRepository.save(any())).thenReturn(newBookEntity);
        BookDto expected = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .withSeed(50)
                .set(field(BookDto::id), newId)
                .ignore(field(BookDto::price))
                .lenient()
                .create();

        BookDto result = bookServiceImpl.create(newBookDto);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    // test create book with existing isbn
    @Test
    @DisplayName("createBook should throw exception when ISBN already exists")
    void createBook_ShouldThrowException_WhenIsbnAlreadyExists() {
        BookDto newBookDto = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .withSeed(60)
                .ignore(field(BookDto::id))
                .ignore(field(BookDto::price))
                .lenient()
                .create();
        BookEntity existingBookEntity = Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                .withSeed(60)
                .create();

        when(bookRepository.findByIsbn(any())).thenReturn(Optional.of(existingBookEntity));

        assertThrows(BusinessException.class, () -> bookServiceImpl.create(newBookDto));
    }


    // test create book with non-existing publisher
    @Test
    @DisplayName("createBook should throw exception when publisher does not exist")
    void createBook_ShouldThrowException_WhenPublisherDoesNotExist() {

        BookDto newBookDto = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .withSeed(70)
                .ignore(field(BookDto::id))
                .ignore(field(BookDto::price))
                .lenient()
                .create();

        when(bookRepository.findByIsbn(any())).thenReturn(Optional.empty());
        when(publisherRepository.findById(any())).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> bookServiceImpl.create(newBookDto));
    }

    @Test
    @DisplayName("createBook should throw exception when an author does not exist")
    void createBook_ShouldThrowException_WhenAnAuthorDoesNotExist() {

        BookDto newBookDto = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .withSeed(80)
                .ignore(field(BookDto::id))
                .ignore(field(BookDto::price))
                .lenient()
                .create();
        PublisherEntity publisherEntity = Instancio.of(InstancioModel.PUBLISHER_ENTITY_MODEL)
                .withSeed(80)
                .create();

        when(bookRepository.findByIsbn(any())).thenReturn(Optional.empty());
        when(publisherRepository.findById(any())).thenReturn(Optional.of(publisherEntity));
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookServiceImpl.create(newBookDto));
    }


    @Test
    @DisplayName("updateBook should update an existing book")
    void updateBook_ShouldUpdateExistingBook() {

        BookEntity existingBookEntity = Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                .withSeed(90)
                .ignore(field(BookEntity::publisher))
                .ignore(field(BookEntity::authors))
                .lenient()
                .create();
        BookDto bookDtoToUpdate = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .withSeed(90)
                .ignore(field(BookDto::price))
                .ignore(field(BookDto::publisher))
                .ignore(field(BookDto::authors))
                .set(field(BookDto::titleEs), "Updated Title ES")
                .lenient()
                .create();
        BookEntity updatedBookEntity = Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                .withSeed(90)
                .ignore(field(BookEntity::publisher))
                .ignore(field(BookEntity::authors))
                .set(field(BookEntity::titleEs), "Updated Title ES")
                .lenient()
                .create();

        when(bookRepository.findById(any())).thenReturn(Optional.of(existingBookEntity));
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.of(existingBookEntity));

        when(bookRepository.save(any())).thenReturn(updatedBookEntity);

        BookDto result = bookServiceImpl.update(bookDtoToUpdate);
        assertAll(
                () -> assertNotNull(result, "Result should not be null"),
                () -> assertEquals(bookDtoToUpdate.id(), result.id(), "ID should match"),
                () -> assertEquals(bookDtoToUpdate.isbn(), result.isbn(), "ISBN should match"),
                () -> assertEquals(bookDtoToUpdate.titleEs(), result.titleEs(), "Title should match")
        );
    }

    @Test
    @DisplayName("updateBook should throw exception when book does not exist")
    void updateBook_ShouldThrowException_WhenBookDoesNotExist() {
        BookDto nonExistingBookDto = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .withSeed(100)
                .ignore(field(BookDto::price))
                .lenient()
                .create();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> bookServiceImpl.update(nonExistingBookDto));
    }

    @Test
    @DisplayName("updateBook should throw exception when updating to an existing ISBN")
    void updateBook_ShouldThrowException_WhenUpdatingToExistingIsbn() {
        BookDto bookDtoToUpdate = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .withSeed(110)
                .ignore(field(BookDto::price))
                .lenient()
                .create();
        BookEntity existingBookEntity = Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                .withSeed(110)
                .create();
        BookEntity anotherExistingBookEntity = Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                .withSeed(111)
                .create();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(existingBookEntity));
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.of(anotherExistingBookEntity));
        assertThrows(BusinessException.class, () -> bookServiceImpl.update(bookDtoToUpdate));
    }


}