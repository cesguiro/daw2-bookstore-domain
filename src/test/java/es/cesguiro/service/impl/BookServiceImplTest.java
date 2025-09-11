package es.cesguiro.service.impl;

import es.cesguiro.model.Book;
import es.cesguiro.repository.BookRepository;
import es.cesguiro.repository.entity.BookEntity;
import es.cesguiro.service.dto.BookDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    @Test
    @DisplayName("getAll should return list of books")
    void getAll_ShouldReturnListOfBooks() {
        // Arrange
        int page = 0;
        int size = 10;

        // Mock books
        BookEntity bookEntity1 = new BookEntity(
                "123",
                "TitleEs1",
                "TitleEn1",
                "SynopsisEs1",
                "SynopsisEn1",
                new BigDecimal("10.00"),
                5,
                "cover1.jpg", LocalDate.of(2020, 1, 1),
                null,
                null
        );
        BookEntity bookEntity2 = new BookEntity(
                "456",
                "TitleEs2",
                "TitleEn2",
                "SynopsisEs2",
                "SynopsisEn2",
                new BigDecimal("15.00"),
                10,
                "cover2.jpg", LocalDate.of(2021, 6, 15),
                null,
                null
        );
        List<BookEntity> bookEntities = List.of(bookEntity1, bookEntity2);
        when(bookRepository.findAll(page, size)).thenReturn(bookEntities);


        // Act
        List<BookDto> result = bookServiceImpl.getAll(page, size);

        // Assert
        assertAll(
                () -> assertNotNull(result, "Result should not be null"),
                () -> assertEquals(2, result.size(), "Result size should be 2"),
                () -> assertEquals("123", result.get(0).isbn(), "First book ISBN should match"),
                () -> assertEquals("456", result.get(1).isbn(), "Second book ISBN should match")
        );

        // Verify interaction with mock
        Mockito.verify(bookRepository).findAll(page, size);
    }


    // test getByIsbn when book exists

    // test getByIsbn when book does not exist

    // test findByIsbn when book exists

    // test findByIsbn when book does not exist

    // test create book

    // test create book with existing isbn

    // test create book with invalid data

    // test create book with non-existing authors

    // .....

}