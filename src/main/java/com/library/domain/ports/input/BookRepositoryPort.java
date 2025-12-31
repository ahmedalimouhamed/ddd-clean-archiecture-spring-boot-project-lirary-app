package com.library.domain.ports.input;

import com.library.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryPort {
    Book save(Book book);
    Optional<Book> findById(Long id);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findAll();
    List<Book> findByAuthor(String author);
    List<Book> findByTitleContaining(String title);
    List<Book> findAvailableBooks();
    void deleteById(Long id);
    boolean existsByIsbn(String isbn);
}
