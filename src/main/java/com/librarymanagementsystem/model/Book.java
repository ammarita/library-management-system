package com.librarymanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name = "title", nullable = false)
    String title;
    @Column(name = "author", nullable = false)
    String author;
    @Column(name = "language", nullable = false)
    String language;
    @Column(name = "publication_year", nullable = false)
    long publicationYear;
    @Column(name = "available", nullable = false)
    boolean isAvailable;
}
