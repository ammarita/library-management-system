package com.librarymanagementsystem.repository;

import com.librarymanagementsystem.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void givenBookRepository_whenFindByPublicationYear_thenOK() {
        Book book = bookRepository.save(new Book(1L, "Test Title",
                "Test Author", "Test Language", 2020L, true));
        List<Book> foundBooks = bookRepository.findByPublicationYear(2020L);

        assertNotNull(foundBooks);
        assertEquals(book.getPublicationYear(), foundBooks.get(0).getPublicationYear());
    }
}
