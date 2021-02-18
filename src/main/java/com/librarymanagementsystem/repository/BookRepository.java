package com.librarymanagementsystem.repository;

import com.librarymanagementsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByPublicationYear(final long publicationYear);
}