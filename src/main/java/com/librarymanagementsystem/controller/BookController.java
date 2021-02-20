package com.librarymanagementsystem.controller;

import com.librarymanagementsystem.dto.BookDto;
import com.librarymanagementsystem.exception.BookNotFoundException;
import com.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/library")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<Book> bookList = bookService.getAllBooks();
        Type targetListType = new TypeToken<List<BookDto>>() {}.getType();
        return new ResponseEntity<>(modelMapper.map(bookList, targetListType), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(final @PathVariable("id") Long id) throws BookNotFoundException {
        Book book = bookService.getBookById(id);
        return (book != null) ? new ResponseEntity<>(modelMapper.map(book, BookDto.class), new HttpHeaders(), HttpStatus.OK)
                : new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/published")
    public ResponseEntity<List<BookDto>> getBooksByYear(final @RequestParam() Long year) throws BookNotFoundException {
        List<Book> bookList = bookService.getBooksByPublicationYear(year);
        Type targetListType = new TypeToken<List<BookDto>>() {}.getType();
        return new ResponseEntity<>(modelMapper.map(bookList, targetListType), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> createOrUpdateBook(final @RequestBody BookDto book) throws BookNotFoundException {
        Book updated = bookService.createOrUpdateBook(modelMapper.map(book, Book.class));
        return new ResponseEntity<>(modelMapper.map(updated, BookDto.class), new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteBookById(final @PathVariable("id") Long id) throws BookNotFoundException {
        bookService.deleteBookById(id);
        return HttpStatus.OK;
    }
}
