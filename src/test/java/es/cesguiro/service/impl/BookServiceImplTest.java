package es.cesguiro.service.impl;

import es.cesguiro.data.loader.BooksDataLoader;
import es.cesguiro.domain.exception.ResourceNotFoundException;
import es.cesguiro.domain.model.Book;
import es.cesguiro.domain.model.Page;
import es.cesguiro.domain.repository.BookRepository;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.service.dto.BookDto;
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

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    private static List<Book> books;
    private static List<BookEntity> bookEntities;
    private static List<BookDto> bookDtos;

    @BeforeAll
    static void setUp() {
        BooksDataLoader booksDataLoader = new BooksDataLoader();
        books = booksDataLoader.loadBooksFromCSV();
        bookEntities = booksDataLoader.loadBookEntitiesFromCSV();
        bookDtos = booksDataLoader.loadBookDtosFromCSV();
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

    // test create book

    // test create book with existing isbn

    // test create book with invalid data

    // test create book with non-existing authors

    // .....

}