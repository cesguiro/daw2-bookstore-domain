package es.cesguiro.service.impl;

import es.cesguiro.data.loader.AuthorsDataLoader;
import es.cesguiro.data.loader.BooksDataLoader;
import es.cesguiro.data.loader.PublishersDataLoader;
import es.cesguiro.domain.exception.BusinessException;
import es.cesguiro.domain.exception.ResourceNotFoundException;
import es.cesguiro.domain.model.Author;
import es.cesguiro.domain.model.Book;
import es.cesguiro.domain.model.Page;
import es.cesguiro.domain.model.Publisher;
import es.cesguiro.domain.repository.BookRepository;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.domain.service.dto.AuthorDto;
import es.cesguiro.domain.service.dto.BookDto;
import es.cesguiro.domain.service.dto.PublisherDto;
import es.cesguiro.domain.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeAll;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    private static List<Book> books;
    private static List<BookEntity> bookEntities;
    private static List<BookDto> bookDtos;
    private static List<Author> authors;
    private static List<AuthorEntity> authorEntities;
    private static List<AuthorDto> authorDtos;
    private static List<Publisher> publishers;
    private static List<PublisherEntity> publisherEntities;
    private static List<PublisherDto> publisherDtos;

    @BeforeAll
    static void setUp() {
        BooksDataLoader booksDataLoader = new BooksDataLoader();
        AuthorsDataLoader authorsDataLoader = new AuthorsDataLoader();
        PublishersDataLoader publishersDataLoader = new PublishersDataLoader();

        books = booksDataLoader.loadBooksFromCSV();
        bookEntities = booksDataLoader.loadBookEntitiesFromCSV();
        bookDtos = booksDataLoader.loadBookDtosFromCSV();
        authors = authorsDataLoader.loadAuthorsFromCSV();
        authorEntities = authorsDataLoader.loadAuthorEntitiesFromCSV();
        authorDtos = authorsDataLoader.loadAuthorDtosFromCSV();
        publishers = publishersDataLoader.loadPublishersFromCSV();
        publisherEntities = publishersDataLoader.loadPublisherEntitiesFromCSV();
        publisherDtos = publishersDataLoader.loadPublisherDtosFromCSV();
    }

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    static Stream<Arguments> provideFindAllArguments() {
        return Stream.of(
                Arguments.of(1, 10, 2L, bookEntities.subList(0,2), new Page<>(bookDtos.subList(0, 2), 1, 10, 2)),
                Arguments.of(1, 3, 3L, bookEntities.subList(0,3), new Page<>(bookDtos.subList(0, 3), 1, 3, 3)),
                Arguments.of(1, 3, 9L, bookEntities.subList(0,3), new Page<>(bookDtos.subList(0, 3), 1, 3, 9)),
                Arguments.of(2, 3, 5L, bookEntities.subList(3,5), new Page<>(bookDtos.subList(3, 5), 2, 3, 5))
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
                () -> assertEquals(expected.data().getFirst().isbn(), result.data().getFirst().isbn(), "First book ISBN should match")
        );
    }

    // test getByIsbn when book exists
    @Test
    @DisplayName("getByIsbn should return book when it exists")
    void getByIsbn_ShouldReturnBook_WhenItExists() {
        String isbn = books.getFirst().getIsbn();
        when(bookRepository.findByIsbn(isbn)).thenReturn(java.util.Optional.of(bookEntities.getFirst()));
        BookDto result = bookServiceImpl.getByIsbn(isbn);
        BookDto expected = bookDtos.getFirst();
        assertAll(
                () -> assertNotNull(result, "Result should not be null"),
                () -> assertEquals(expected.isbn(), result.isbn(), "ISBN should match"),
                () -> assertEquals(expected.titleEs(), result.titleEs(), "Title should match")
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
        String isbn = books.getFirst().getIsbn();
        when(bookRepository.findByIsbn(isbn)).thenReturn(java.util.Optional.of(bookEntities.getFirst()));
        java.util.Optional<BookDto> result = bookServiceImpl.findByIsbn(isbn);
        assertAll(
                () -> assertTrue(result.isPresent(), "Result should be present"),
                () -> assertEquals(books.getFirst().getIsbn(), result.get().isbn(), "ISBN should match"),
                () -> assertEquals(books.getFirst().getTitleEs(), result.get().titleEs(), "Title should match")
        );
    }

    // test findByIsbn when book does not exist
    @Test
    @DisplayName("findByIsbn should return empty when book does not exist")
    void findByIsbn_ShouldReturnEmpty_WhenBookDoesNotExist() {
        String isbn = "non-existing-isbn";
        when(bookRepository.findByIsbn(isbn)).thenReturn(java.util.Optional.empty());
        java.util.Optional<BookDto> result = bookServiceImpl.findByIsbn(isbn);
        assertFalse(result.isPresent(), "Result should be empty");
    }

    @Test
    @DisplayName("findByIsbn with null publisher should return book when it exists")
    void findByIsbn_WithNullPublisher_ShouldReturnBook_WhenItExists() {
        Book bookWithNullPublisher = books.get(28);
        BookEntity bookEntityWithNullPublisher = bookEntities.get(28);
        BookDto bookDtoWithNullPublisher = bookDtos.get(28);

        when(bookRepository.findByIsbn("5555555555555")).thenReturn(Optional.of(bookEntityWithNullPublisher));
        Optional<BookDto> result = bookServiceImpl.findByIsbn("5555555555555");
        assertAll(
                () -> assertTrue(result.isPresent(), "Result should be present"),
                () -> assertEquals(bookDtoWithNullPublisher.isbn(), result.get().isbn(), "ISBN should match"),
                () -> assertEquals(bookDtoWithNullPublisher.titleEs(), result.get().titleEs(), "Title should match"),
                () -> assertNull(result.get().publisher(), "Publisher should be null")
        );
    }

    // test create book
    @Test
    @DisplayName("createBook should create a new book")
    void createBook_ShouldCreateNewBook() {
        BookDto newBookDto = new BookDto(
                "9999999999999",
                "New Book Title ES",
                "New Book Title EN",
                "New Book Synopsis ES",
                "New Book Synopsis EN",
                new BigDecimal("29.99"),
                0.0,
                null,
                "http://example.com/newbookcover.jpg",
                LocalDate.of(2024,1,1),
                publisherDtos.getFirst(),
                List.of(authorDtos.getFirst(), authorDtos.get(1))
        );

        BookEntity newBookEntity = new BookEntity(
                "9999999999999",
                "New Book Title ES",
                "New Book Title EN",
                "New Book Synopsis ES",
                "New Book Synopsis EN",
                new BigDecimal("29.99"),
                0.0,
                "http://example.com/newbookcover.jpg",
                LocalDate.of(2024,1,1),
                publisherEntities.getFirst(),
                List.of(authorEntities.getFirst(), authorEntities.get(1))
        );

        when(bookRepository.save(newBookEntity)).thenReturn(newBookEntity);

        BookDto createdBook = bookServiceImpl.create(newBookDto);

        assertAll(
                () -> assertNotNull(createdBook, "Created book should not be null"),
                () -> assertEquals(newBookDto.isbn(), createdBook.isbn(), "ISBN should match"),
                () -> assertEquals(newBookDto.titleEs(), createdBook.titleEs(), "Title should match")
        );
    }

    // test create book with existing isbn
    @Test
    @DisplayName("createBook should throw exception when ISBN already exists")
    void createBook_ShouldThrowException_WhenIsbnAlreadyExists() {
        BookDto existingBookDto = bookDtos.getFirst();
        BookEntity existingBookEntity = bookEntities.getFirst();

        when(bookRepository.findByIsbn(existingBookDto.isbn())).thenReturn(Optional.of(existingBookEntity));

        assertThrows(BusinessException.class, () -> bookServiceImpl.create(existingBookDto));
    }

    static Stream<Arguments> provideInvalidDataArguments() {
        return Stream.of(
                Arguments.of("", new BigDecimal("10.00"), 5.0),
                Arguments.of("9999999999999", new BigDecimal("-10.00"), 5.0),
                Arguments.of("9999999999999", new BigDecimal("10.00"), -5.0)
        );
    }

    // test create book with invalid data
    @ParameterizedTest
    @MethodSource("provideInvalidDataArguments")
    @DisplayName("createBook should throw exception when data is invalid")
    void createBook_ShouldThrowException_WhenDataIsInvalid(String isbn, BigDecimal basePrice, double discount) {
        BookDto invalidBookDto = new BookDto(
                isbn, // empty ISBN
                "Book Title ES",
                "Book Title EN",
                "Book Synopsis ES",
                "Book Synopsis EN",
                basePrice, // negative price
                discount,
                null,
                "url", // invalid URL
                LocalDate.of(2024,1,1),
                publisherDtos.getFirst(),
                List.of(authorDtos.getFirst())
        );

        assertThrows(BusinessException.class, () -> bookServiceImpl.create(invalidBookDto));
    }

    // test create book with non-existing authors

    // .....

}