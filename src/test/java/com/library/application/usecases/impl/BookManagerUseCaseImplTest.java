package com.library.application.usecases.impl;

import com.library.application.dto.BookResponse;
import com.library.application.dto.CreateBookRequest;
import com.library.application.exception.NotFoundException;
import com.library.application.exception.ValidationException;
import com.library.application.mapper.BookMapper;
import com.library.domain.model.Book;
import com.library.domain.ports.input.BookRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookManagerUseCaseImplTest {

    @Mock
    private BookRepositoryPort bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookManagerUseCaseImpl bookManager;

    private CreateBookRequest createBookRequest;
    private Book book;

    @BeforeEach
    void setUp(){
        createBookRequest = new CreateBookRequest();
        createBookRequest.setIsbn("isb-123");
        createBookRequest.setTitle("title book");
        createBookRequest.setAuthor("author");
        createBookRequest.setPublicationYear(1999);
        createBookRequest.setPublisher("publisher");
        createBookRequest.setTotalCopies(50);

        book = Book.createBook(
                "isb-123",
                "title book",
                "author",
                1999,
                "publisher",
                50
        );
        book.setId(1L);
    }

    @Test
    void createBook_withvalidRequest_shouldReturnBookResponse(){

        when(bookRepository.existsByIsbn(anyString())).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookResponse responseMock = new BookResponse();
        responseMock.setId(1L);
        responseMock.setTitle("title book");
        responseMock.setAuthor("author");
        responseMock.setTotalCopies(50);
        responseMock.setAvailableCopies(50);

        when(bookMapper.toResponse(any(Book.class)))
                .thenReturn(responseMock);

        BookResponse response = bookManager.createBook(createBookRequest);

        assertNotNull(response);
        assertEquals("title book", response.getTitle());
        assertEquals("author", response.getAuthor());
        assertEquals(50, response.getAvailableCopies());
        assertEquals(50, response.getTotalCopies());

        verify(bookRepository).save(any(Book.class));
        verify(bookMapper).toResponse(any(Book.class));
    }

    @Test
    void creaeBook_withExistingIsbn_shouldThrowException(){
        when(bookRepository.existsByIsbn("isb-123")).thenReturn(true);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> bookManager.createBook(createBookRequest)
        );

        assertEquals(
                "Book with ISBN isb-123 already xists",
                exception.getMessage()
        );

        verify(bookRepository, never()).save(any());
    }

    @Test
    void getBook_withExistingId_shouldReturnBookResponse(){
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookResponse responseMock = new BookResponse();
        responseMock.setId(1L);
        responseMock.setTitle("title book");
        responseMock.setAuthor("author");
        responseMock.setTotalCopies(50);
        responseMock.setAvailableCopies(50);

        when(bookMapper.toResponse(book)).thenReturn(responseMock);

        BookResponse response = bookManager.getBookById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBook_withNonExistingId_shouldThrowException(){
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> bookManager.getBookById(999L)
        );
        assertEquals("Book not found with ID : 999", exception.getMessage());
    }

    @Test
    void borrowBook_withAvailableCopies_shouldSucceed(){
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookManager.borrowBook(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

   /* @Test
    void updateBook_withAdditionalCopies_shouldUpdateTotalCopies(){
        UpdateBookRequest updateBookRequest = new UpdateBookRequest();
        updateBookRequest.setAdditionalCopies(10);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        var response = bookManager.updateBook(1L, updateBookRequest);

        assertNotNull(response);
        verify(bookRepository, times(1)).save(any(Book.class));
    }*/

    @Test
    void deleteBook_withExistingId_shouldCallRepository(){
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookManager.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }
}