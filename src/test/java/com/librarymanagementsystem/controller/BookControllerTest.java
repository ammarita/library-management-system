package com.librarymanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagementsystem.dto.BookDto;
import com.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @MockBean
    BookService bookService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ModelMapper modelMapper;

    private Book book;
    private Book book2;
    private Book book3;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "Test title", "Test author", "lang", 2000L, false);
        book2 = new Book(2L, "New test book", "New author", "lang", 2020L, true);
        book3 = new Book(3L, "Another test book", "Another author", "lng", 2020L, true);
    }

    @Test
    void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book, book2, book3));
        mockMvc.perform(get("/library"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void testGetBookByIdFound() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(book);
        mockMvc.perform(get("/library/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id" ).value("1"))
                .andExpect(jsonPath("$.title").value("Test title"));
    }

    @Test
    void testGetBookByIdNotFound() throws Exception{
        when(bookService.getBookById(1L)).thenReturn(null);
        mockMvc.perform(get("/library/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetBooksByYearFound() throws Exception {
        when(bookService.getBooksByPublicationYear(2020L)).thenReturn(Arrays.asList(book2, book3));
        mockMvc.perform(get("/library/published?year=2020"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("[0].id").value("2"))
                .andExpect(jsonPath("[1].id").value("3"));
    }

    @Test
    void testCreateOrUpdateBook() throws Exception{
        BookDto bookDto = new BookDto();
        bookDto.setId(2);
        bookDto.setTitle("Test Book");
        bookDto.setAuthor("New Author");
        bookDto.setLanguage("lang");
        bookDto.setPublicationYear(2021L);
        bookDto.setAvailable(true);
        Book book = modelMapper.map(bookDto, Book.class);

        doReturn(book).when(bookService).createOrUpdateBook(book);
        mockMvc.perform(post("/library")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(book)))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(status().isCreated());
    }

    @Test
    void testDeleteBookById() throws Exception{
        doNothing().when(bookService).deleteBookById(1L);
        mockMvc.perform(delete("/library/{id}",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").doesNotExist());
    }

    static String toJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException("Error while wrapping Json");
        }
    }
}
