package com.librarymanagementsystem.repository;

import com.librarymanagementsystem.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryH2Test {

    @Autowired
    BookRepository bookRepository;

    @Test
    @Sql("classpath:data.sql")
    void testGerBooksByYear() {
        List<Book> bookList = bookRepository.findByPublicationYear(2013L);

        assertThat(bookList).isNotEmpty();
        assertThat(bookList.size()).isEqualTo(1);
        assertThat(bookList.get(0).getTitle()).isEqualTo("Vilko valanda");
        assertThat(bookList.get(0).getPublicationYear()).isEqualTo(2013L);
    }
}
