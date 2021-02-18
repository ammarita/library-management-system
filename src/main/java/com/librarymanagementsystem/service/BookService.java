package com.librarymanagementsystem.service;

import com.librarymanagementsystem.exception.BookNotFoundException;
import com.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();

        if(bookList.size() > 0) {
            return bookList;
        } else {
            return List.of();
        }
    }

    public Book getBookById(final Long id) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(id);

        if(book.isPresent()) {
            return book.get();
        } else {
            throw new BookNotFoundException("No book entry exist for given id: {}", id);
        }
    }

    public Book createOrUpdateBook(Book book) throws BookNotFoundException {
        Optional<Book> newBook = bookRepository.findById(book.getId());

        if(newBook.isPresent()) {
            Book newEntry = newBook.get();
            newEntry.setTitle(book.getTitle());
            newEntry.setAuthor(book.getAuthor());
            newEntry.setLanguage(book.getLanguage());
            newEntry.setPublicationYear(book.getPublicationYear());
            newEntry = bookRepository.save(newEntry);
            return newEntry;
        } else {
            book = bookRepository.save(book);
            return book;
        }
    }

    public void deleteBookById(final Long id) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(id);

        if(book.isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw new BookNotFoundException("No book record exist for given id: {}", id);
        }
    }

    public List<Book> getBooksByPublicationYear(final long year) {
        return new ArrayList<>(bookRepository.findByPublicationYear(year));
    }
}