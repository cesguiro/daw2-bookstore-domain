package es.cesguiro.domain.service.impl;

import es.cesguiro.domain.exception.BusinessException;
import es.cesguiro.domain.exception.ResourceNotFoundException;
import es.cesguiro.domain.model.Page;
import es.cesguiro.domain.repository.AuthorRepository;
import es.cesguiro.domain.repository.BookRepository;
import es.cesguiro.domain.repository.PublisherRepository;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.domain.service.dto.AuthorDto;
import es.cesguiro.domain.service.dto.BookDto;
import es.cesguiro.domain.service.dto.PublisherDto;
import es.cesguiro.util.InstancioModel;
import org.instancio.Instancio;
import static org.instancio.Select.field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;


    static Stream<Arguments> provideFindAllArguments() {
        return Stream.of(
                Arguments.of(1, 5, 2L,
                        Instancio.ofList(InstancioModel.BOOK_ENTITY_MODEL).size(2).withSeed(10).create(),
                        new Page<>(Instancio.ofList(InstancioModel.BOOK_DTO_MODEL).size(2).withSeed(10).ignore(field(BookDto::price)).lenient().create(), 1, 5, 2)),
                Arguments.of(1, 10, 2L,
                        Instancio.ofList(InstancioModel.BOOK_ENTITY_MODEL).size(2).withSeed(20).create(),
                        new Page<>(Instancio.ofList(InstancioModel.BOOK_DTO_MODEL).size(2).withSeed(20).ignore(field(BookDto::price)).lenient().create(), 1, 10, 2)),
                Arguments.of(1, 3, 3L,
                        Instancio.ofList(InstancioModel.BOOK_ENTITY_MODEL).size(3).withSeed(30).create(),
                        new Page<>(Instancio.ofList(InstancioModel.BOOK_DTO_MODEL).size(3).withSeed(30).ignore(field(BookDto::price)).lenient().create(), 1, 3, 3)),
                Arguments.of(1, 3, 9L,
                        Instancio.ofList(InstancioModel.BOOK_ENTITY_MODEL).size(3).withSeed(40).create(),
                        new Page<>(Instancio.ofList(InstancioModel.BOOK_DTO_MODEL).size(3).withSeed(40).ignore(field(BookDto::price)).lenient().create(), 1, 3, 9)),
                Arguments.of(2, 3, 5L,
                        Instancio.ofList(InstancioModel.BOOK_ENTITY_MODEL).size(3).withSeed(50).create(),
                        new Page<>(Instancio.ofList(InstancioModel.BOOK_DTO_MODEL).size(3).withSeed(50).ignore(field(BookDto::price)).lenient().create(), 2, 3, 5))
        );
    }

    @ParameterizedTest
    @DisplayName("getAll should return list of books")
    @MethodSource("provideFindAllArguments")
    void getAll_ShouldReturnListOfBooks(int page, int size, long count, List<BookEntity> bookEntities, Page<BookDto> expected) {
        when(bookRepository.findAll(page, size)).thenReturn(new Page<>(bookEntities, page, size, count));
        Page<BookDto> result = bookServiceImpl.getAll(page, size);

        assertAll(
                () -> assertEquals(expected.data().size(), result.data().size(), "Number of books should match"),
                () -> assertEquals(expected.pageNumber(), result.pageNumber(), "Page number should match"),
                () -> assertEquals(expected.pageSize(), result.pageSize(), "Page size should match"),
                () -> assertEquals(expected.totalElements(), result.totalElements(), "Total items should match"),
                () -> assertEquals(expected.data().getFirst().isbn(), result.data().getFirst().isbn(), "First book ISBN should match"),
                () -> assertEquals(expected.data().getLast().isbn(), result.data().getLast().isbn(), "Last book ISBN should match")
        );
    }

    @Test
    @DisplayName("getByIsbn should return book when it exists")
    void getByIsbn_ShouldReturnBook_WhenItExists() {
        BookEntity bookEntity = Instancio.of(InstancioModel.BOOK_ENTITY_MODEL).withSeed(10).create();
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.of(bookEntity));
        BookDto result = bookServiceImpl.getByIsbn(bookEntity.isbn());
        BookDto expected = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .withSeed(10)
                .ignore(field(BookDto::price))
                .lenient()
                .create();
        assertAll(
                () -> assertNotNull(result, "Result should not be null"),
                () -> assertEquals(expected.isbn(), result.isbn(), "ISBN should match"),
                () -> assertEquals(expected.titleEs(), result.titleEs(), "Title should match"),
                () -> assertEquals(expected.publisher().id(), result.publisher().id(), "Publisher ID should match"),
                () -> assertEquals(expected.authors().size(), result.authors().size(), "Number of authors should match")
        );
    }

    // test getByIsbn when book does not exist
    @Test
    @DisplayName("getByIsbn should throw exception when book does not exist")
    void getByIsbn_ShouldThrowException_WhenBookDoesNotExist() {
        String isbn = "non-existing-isbn";
        when(bookRepository.findByIsbn(isbn)).thenReturn(java.util.Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> bookServiceImpl.getByIsbn(isbn));
    }


    // test findByIsbn when book exists
    @Test
    @DisplayName("findByIsbn should return book when it exists")
    void findByIsbn_ShouldReturnBook_WhenItExists() {
        BookEntity bookEntity = Instancio.of(InstancioModel.BOOK_ENTITY_MODEL).withSeed(20).create();
        when(bookRepository.findByIsbn(anyString())).thenReturn(java.util.Optional.of(bookEntity));
        BookDto expected = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .withSeed(20)
                .ignore(field(BookDto::price))
                .lenient()
                .create();

        Optional<BookDto> result = bookServiceImpl.findByIsbn("some-isbn");
        assertAll(
                () -> assertTrue(result.isPresent(), "Result should be present"),
                () -> assertEquals(expected.isbn(), result.get().isbn(), "ISBN should match"),
                () -> assertEquals(expected.titleEs(), result.get().titleEs(), "Title should match"),
                () -> assertEquals(expected.publisher().id(), result.get().publisher().id(), "Publisher ID should match"),
                () -> assertEquals(expected.authors().size(), result.get().authors().size(), "Number of authors should match")
        );
    }

    // test findByIsbn when book does not exist
    @Test
    @DisplayName("findByIsbn should return empty when book does not exist")
    void findByIsbn_ShouldReturnEmpty_WhenBookDoesNotExist() {
        String isbn = "non-existing-isbn";
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.empty());
        Optional<BookDto> result = bookServiceImpl.findByIsbn(isbn);
        assertFalse(result.isPresent(), "Result should be empty");
    }

    @Test
    @DisplayName("findByIsbn with null publisher should return book when it exists")
    void findByIsbn_WithNullPublisher_ShouldReturnBook_WhenItExists() {
        BookEntity bookEntityWithNullPublisher = Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                .ignore(field(BookEntity::publisher))
                .lenient()
                .withSeed(30)
                .create();
        BookDto expected = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .ignore(field(BookDto::publisher))
                .ignore(field(BookDto::price))
                .lenient()
                .withSeed(30)
                .create();

        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.of(bookEntityWithNullPublisher));
        Optional<BookDto> result = bookServiceImpl.findByIsbn("some-isbn");

        assertAll(
                () -> assertTrue(result.isPresent(), "Result should be present"),
                () -> assertEquals(expected.isbn(), result.get().isbn(), "ISBN should match"),
                () -> assertEquals(expected.titleEs(), result.get().titleEs(), "Title should match"),
                () -> assertNull(result.get().publisher(), "Publisher should be null")
        );
    }

    @Test
    @DisplayName("findByIsbn with null authors should return book when it exists")
    void findByIsbn_WithNullAuthors_ShouldReturnBook_WhenItExists() {
        BookEntity bookEntityWithNullAuthors = Instancio.of(InstancioModel.BOOK_ENTITY_MODEL)
                .ignore(field(BookEntity::authors))
                .lenient()
                .withSeed(40)
                .create();
        BookDto expected = Instancio.of(InstancioModel.BOOK_DTO_MODEL)
                .ignore(field(BookDto::authors))
                .ignore(field(BookDto::price))
                .lenient()
                .withSeed(40)
                .create();

        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.of(bookEntityWithNullAuthors));
        Optional<BookDto> result = bookServiceImpl.findByIsbn("some-isbn");
        assertAll(
                () -> assertTrue(result.isPresent(), "Result should be present"),
                () -> assertEquals(expected.isbn(), result.get().isbn(), "ISBN should match"),
                () -> assertEquals(expected.titleEs(), result.get().titleEs(), "Title should match"),
                () -> assertTrue(result.get().authors().isEmpty(), "Authors should be empty")
        );
    }

    // test create book
    @Test
    @DisplayName("createBook should create a new book")
    void createBook_ShouldCreateNewBook() {
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

        assertAll(
                () -> assertNotNull(result, "Created book should not be null"),
                () -> assertEquals(expected.id(), result.id(), "ID should match"),
                () -> assertEquals(expected.isbn(), result.isbn(), "ISBN should match"),
                () -> assertEquals(expected.titleEs(), result.titleEs(), "Title should match"),
                () -> assertEquals(expected.publisher().id(), result.publisher().id(), "Publisher ID should match"),
                () -> assertEquals(expected.authors().size(), result.authors().size(), "Number of authors should match")
        );
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

        when(bookRepository.save(updatedBookEntity)).thenReturn(updatedBookEntity);

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
        when(bookRepository.findById(bookDtoToUpdate.id())).thenReturn(Optional.of(existingBookEntity));
        when(bookRepository.findByIsbn(bookDtoToUpdate.isbn())).thenReturn(Optional.of(anotherExistingBookEntity));
        assertThrows(BusinessException.class, () -> bookServiceImpl.update(bookDtoToUpdate));
    }


}