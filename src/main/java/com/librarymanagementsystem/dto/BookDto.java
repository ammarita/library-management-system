package com.librarymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    long id;
    String title;
    String author;
    String language;
    long publicationYear;
    boolean isAvailable;
}
