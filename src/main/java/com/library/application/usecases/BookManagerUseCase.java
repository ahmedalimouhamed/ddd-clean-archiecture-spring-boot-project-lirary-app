package com.library.application.usecases;

import com.library.application.dto.BookResponse;
import com.library.application.dto.CreateBookRequest;

import java.util.List;

public interface BookManagerUseCase {
    BookResponse createBook(CreateBookRequest request);
    BookResponse getBookById(Long id);
    BookResponse getBookByIsbn(String isbn);
    List<BookResponse> getAllBooks();
    List<BookResponse> getAvailableBooks();
    List<BookResponse> searchBooks(String title, String author);
    BookResponse updateBook(Long id, CreateBookRequest request);
    void deleteBook(Long id);
    BookResponse addBookCopies(Long id, int additionalCopies);
}
