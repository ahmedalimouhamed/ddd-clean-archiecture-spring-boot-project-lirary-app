package com.library.application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateBookRequest {
    @NotBlank(message="ISBN is required")
    @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$", message = "Invalid ISBN format")
    private String isbn;

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 200, message = "Title must be btween 1 and 200 characters")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @Min(value = 1000, message = "Publication must be valid")
    @Max(value = 2100, message ="Publication year must be valid")
    private int publicationYear;

    private String publisher;

    @Min(value = 1, message = "Total sopies must be at least 1")
    private Integer totalCopies = 1;
}
