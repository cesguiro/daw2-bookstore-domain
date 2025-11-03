package es.cesguiro.domain.service.impl;

import es.cesguiro.domain.exception.ResourceNotFoundException;
import es.cesguiro.domain.model.Page;
import es.cesguiro.domain.repository.AuthorRepository;
import es.cesguiro.domain.repository.BookRepository;
import es.cesguiro.domain.repository.PublisherRepository;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.service.dto.BookDto;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
        when(bookRepository.save(newBookEntity)).thenReturn(newBookEntity);

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
    /*@Test
    @DisplayName("createBook should throw exception when ISBN already exists")
    void createBook_ShouldThrowException_WhenIsbnAlreadyExists() {
        BookDto existingBookDto = bookDtos.getFirst();
        BookEntity existingBookEntity = bookEntities.getFirst();

        when(bookRepository.findByIsbn(existingBookDto.isbn())).thenReturn(Optional.of(existingBookEntity));

        assertThrows(BusinessException.class, () -> bookServiceImpl.create(existingBookDto));
    }


    // test create book with non-existing publisher
    @Test
    @DisplayName("createBook should throw exception when publisher does not exist")
    void createBook_ShouldThrowException_WhenPublisherDoesNotExist() {
        PublisherDto nonExistingPublisher = new PublisherDto(99L, "Non existing Publisher", "non-existing-publisher");
        BookDto bookDtoWithNonExistingPublisher = new BookDto(
                null,
                "9999999999999",
                "Book Title ES",
                "Book Title EN",
                "Book Synopsis ES",
                "Book Synopsis EN",
                new BigDecimal("19.99"),
                10.0,
                null,
                "http://example.com/bookcover.jpg",
                LocalDate.of(2024, 1, 1),
                nonExistingPublisher,
                List.of(authorDtos.getFirst())
        );

        when(bookRepository.findByIsbn(bookDtoWithNonExistingPublisher.isbn())).thenReturn(Optional.empty());
        when(publisherRepository.findById(nonExistingPublisher.id())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookServiceImpl.create(bookDtoWithNonExistingPublisher));
    }

    @Test
    @DisplayName("createBook should throw exception when an author does not exist")
    void createBook_ShouldThrowException_WhenAnAuthorDoesNotExist() {
        AuthorDto nonExistingAuthor = new AuthorDto(
                99L,
                "Non existing Author",
                "nationality",
                "biographyEs",
                "biographyEn",
                1970,
                null,
                "non-existing-author"
        );
        BookDto bookDtoWithNonExistingAuthor = new BookDto(
                null,
                "9999999999999",
                "Book Title ES",
                "Book Title EN",
                "Book Synopsis ES",
                "Book Synopsis EN",
                new BigDecimal("19.99"),
                10.0,
                null,
                "http://example.com/bookcover.jpg",
                LocalDate.of(2024, 1, 1),
                publisherDtos.getFirst(),
                List.of(authorDtos.getFirst(), nonExistingAuthor)
        );

        when(bookRepository.findByIsbn(bookDtoWithNonExistingAuthor.isbn())).thenReturn(Optional.empty());
        when(publisherRepository.findById(publisherDtos.getFirst().id())).thenReturn(Optional.of(publisherEntities.getFirst()));
        when(authorRepository.findById(authorDtos.getFirst().id())).thenReturn(Optional.of(authorEntities.getFirst()));
        when(authorRepository.findById(nonExistingAuthor.id())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookServiceImpl.create(bookDtoWithNonExistingAuthor));
    }


    @Test
    @DisplayName("updateBook should update an existing book")
    void updateBook_ShouldUpdateExistingBook() {
        BookDto existingBookDto = bookDtos.getFirst();
        BookEntity existingBookEntity = bookEntities.getFirst();
        BookEntity updatedBookEntity = new BookEntity(
                existingBookEntity.id(),
                existingBookEntity.isbn(),
                "Updated Title ES",
                "Updated Title EN",
                "Updated Synopsis ES",
                "Updated Synopsis EN",
                new BigDecimal("39.99"),
                5.0,
                existingBookEntity.cover(),
                existingBookEntity.publicationDate(),
                existingBookEntity.publisher(),
                existingBookEntity.authors()
        );
        BookDto expectedUpdatedBookDto = new BookDto(
                existingBookDto.id(),
                existingBookDto.isbn(),
                "Updated Title ES",
                "Updated Title EN",
                "Updated Synopsis ES",
                "Updated Synopsis EN",
                new BigDecimal("39.99"),
                5.0,
                null,
                existingBookDto.cover(),
                existingBookDto.publicationDate(),
                existingBookDto.publisher(),
                existingBookDto.authors()
        );

        when(bookRepository.findById(existingBookDto.id())).thenReturn(Optional.of(existingBookEntity));
        when(bookRepository.save(updatedBookEntity)).thenReturn(updatedBookEntity);
        when(publisherRepository.findById(existingBookDto.publisher().id())).thenReturn(Optional.of(publisherEntities.getFirst()));
        when(authorRepository.findById(existingBookDto.authors().get(0).id())).thenReturn(Optional.of(authorEntities.getFirst()));
        if (existingBookDto.authors().size() > 1) {
            when(authorRepository.findById(existingBookDto.authors().get(1).id())).thenReturn(Optional.of(authorEntities.get(1)));
        }
        BookDto result = bookServiceImpl.update(expectedUpdatedBookDto);
        assertAll(
                () -> assertNotNull(result, "Result should not be null"),
                () -> assertEquals(expectedUpdatedBookDto.id(), result.id(), "ID should match"),
                () -> assertEquals(expectedUpdatedBookDto.titleEs(), result.titleEs(), "Title should be updated")
        );
    }

    @Test
    @DisplayName("updateBook should throw exception when book does not exist")
    void updateBook_ShouldThrowException_WhenBookDoesNotExist() {
        BookDto nonExistingBookDto = new BookDto(
                99L,
                "9999999999999",
                "Non-existing Book Title ES",
                "Non-existing Book Title EN",
                "Non-existing Book Synopsis ES",
                "Non-existing Book Synopsis EN",
                new BigDecimal("29.99"),
                0.0,
                null,
                "http://example.com/nonexistingbookcover.jpg",
                LocalDate.of(2024, 1, 1),
                publisherDtos.getFirst(),
                List.of(authorDtos.getFirst())
        );
        when(bookRepository.findById(nonExistingBookDto.id())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> bookServiceImpl.update(nonExistingBookDto));
    }

    @Test
    @DisplayName("updateBook should throw exception when updating to an existing ISBN")
    void updateBook_ShouldThrowException_WhenUpdatingToExistingIsbn() {
        BookDto existingBookDto = bookDtos.getFirst();
        BookEntity existingBookEntity = bookEntities.getFirst();
        BookEntity anotherExistingBookEntity = bookEntities.get(1);
        BookDto bookDtoWithExistingIsbn = new BookDto(
                existingBookDto.id(),
                anotherExistingBookEntity.isbn(), // ISBN of another existing book
                "Updated Title ES",
                "Updated Title EN",
                "Updated Synopsis ES",
                "Updated Synopsis EN",
                new BigDecimal("39.99"),
                5.0,
                null,
                existingBookDto.cover(),
                existingBookDto.publicationDate(),
                existingBookDto.publisher(),
                existingBookDto.authors()
        );

        when(bookRepository.findById(existingBookDto.id())).thenReturn(Optional.of(existingBookEntity));
        when(bookRepository.findByIsbn(anotherExistingBookEntity.isbn())).thenReturn(Optional.of(anotherExistingBookEntity));
        assertThrows(BusinessException.class, () -> bookServiceImpl.update(bookDtoWithExistingIsbn));
    }*/


}