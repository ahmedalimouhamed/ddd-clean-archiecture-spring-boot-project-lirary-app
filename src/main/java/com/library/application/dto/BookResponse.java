package com.library.application.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookResponse {
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private int publicationYear;
    private String publisher;
    private String status;
    private String totalCopies;
    private int availableCopies;
    private LocalDate ceatedAt;
    private LocalDate updatedAt;
}
