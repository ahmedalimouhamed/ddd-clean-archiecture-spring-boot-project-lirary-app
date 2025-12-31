package com.library.infrastructure.web.controller;

import com.library.application.dto.BookResponse;
import com.library.application.dto.CreateBookRequest;
import com.library.application.dto.CreateMemberRequest;
import com.library.application.usecases.BookManagerUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookManagerUseCase bookManagerUseCase;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody CreateBookRequest request){
        BookResponse response = bookManagerUseCase.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id){
        BookResponse response = bookManagerUseCase.getBookById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookResponse> geBookByIsbn(@PathVariable String isbn){
        BookResponse response = bookManagerUseCase.getBookByIsbn(isbn);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks(){
        List<BookResponse> books = bookManagerUseCase.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/available")
    public ResponseEntity<List<BookResponse>> getAvailableBooks(){
        List<BookResponse> books = bookManagerUseCase.getAvailableBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author
    ){
        List<BookResponse> books = bookManagerUseCase.searchBooks(title, author);
        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody CreateBookRequest request
    ){
        BookResponse response = bookManagerUseCase.updateBook(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleeBook(@PathVariable Long id){
        bookManagerUseCase.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/add-copies")
    public ResponseEntity<BookResponse> addBookCopies(
            @PathVariable Long id,
            @RequestParam int additionalCopies
    ){
        BookResponse response = bookManagerUseCase.addBookCopies(id, additionalCopies);
        return ResponseEntity.ok(response);
    }

}
