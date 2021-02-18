package com.librarymanagementsystem.service;

import com.librarymanagementsystem.exception.BookNotFoundException;
import com.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    BookService bookService;

    @MockBean
    BookRepository bookRepository;

    private Book book;
    private Book book2;
    private Book book3;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "Test title", "Test author", "lang", 2000L, false);
        book2 = new Book(2L, "New test book", "New author", "land", 2020L, true);
        book3 = new Book(3L, "Another test book", "Another author", "lng", 2020L, true);
    }

    @Test
    void testGetAllBooks() {
        doReturn(Arrays.asList(book, book2, book3)).when(bookRepository).findAll();

        List<Book> bookList = bookService.getAllBooks();

        assertEquals(3, bookList.size());
    }

    @Test
    void testGetAllBooksReturnsEmptyList() {
        doReturn(Collections.emptyList()).when(bookRepository).findAll();

        List<Book> bookList = bookService.getAllBooks();

        assertEquals(0, bookList.size());
    }

    @Test
    void testGetBookById() {
        doReturn(Optional.of(book2)).when(bookRepository).findById(2L);

        Book returnedBook = bookService.getBookById(2L);

        assertNotNull(returnedBook);
        assertSame(returnedBook, book2);
        assertEquals(2L, returnedBook.getId());
    }

    @Test
    void testGetBookByIdNotFoundAndThrowException() {
        doReturn(Optional.empty()).when(bookRepository).findById(10L);

        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(10L));
    }

    @Test
    void testCreateBook() {
        Book newBook = new Book(4L, "Create book", "Create author", "test", 2000L, false);

        doReturn(Optional.empty()).when(bookRepository).findById(4L);
        doReturn(newBook).when(bookRepository).save(newBook);
        Book createdBook = bookService.createOrUpdateBook(newBook);

        assertEquals(4L, createdBook.getId());
        assertEquals("test", createdBook.getLanguage());
    }

    @Test
    void testUpdateBook() {
        doReturn(Optional.of(book3)).when(bookRepository).findById(book3.getId());
        book3.setTitle("Updated Title");
        doReturn(book3).when(bookRepository).save(book3);

        Book updatedBook = bookService.createOrUpdateBook(book3);

        assertEquals(3L, updatedBook.getId());
        assertEquals("Updated Title", updatedBook.getTitle());
    }

    @Test
    void testDeleteBookById() throws BookNotFoundException {
        doReturn(Optional.of(book)).when(bookRepository).findById(1L);
        bookService.deleteBookById(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBookByIdNotFoundAndThrowsException() throws BookNotFoundException {
        doNothing().when(bookRepository).deleteById(1L);
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBookById(1L));
    }

    @Test
    void testGetBooksByPublicationYear() {
        doReturn(Arrays.asList(book3, book2)).when(bookRepository).findByPublicationYear(2020L);
        List<Book> bookList = bookService.getBooksByPublicationYear(2020L);

        assertEquals(2, bookList.size());
    }
}
