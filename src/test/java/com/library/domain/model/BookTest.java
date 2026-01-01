package com.library.domain.model;

import com.library.domain.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    private Book book;

    @BeforeEach
    void setUp(){
        book = Book.createBook(
                "isb-0001",
                "medical anatomiy",
                "Dr. jhon smith",
                1999,
                "publisher",
                50
        );

        book.setId(1L);
    }

    @Test
    void testCreateBook_success(){
        assertNotNull(book);
        assertEquals("isb-0001", book.getIsbn());
        assertEquals("medical anatomiy", book.getTitle());
        assertEquals("Dr. jhon smith", book.getAuthor());
        assertEquals(1999, book.getPublicationYear());
        assertEquals("publisher", book.getPublisher());
        assertEquals(50, book.getAvailableCopies());
        assertTrue(book.isActive());
        assertTrue(book.isAvailable());
    }

    @Test
    void testCreateBook_withoutTitle_shouldThrowException(){
        DomainException exception = assertThrows(DomainException.class, () ->
                Book.createBook("isb-0001", null, "author", 1999, "publisher", 50)
        );

        assertEquals("Title is required", exception.getMessage());
    }

//    @Test
//    void testCreateBook_withZeroCopies_shouldThrowException(){
//        DomainException exception = assertThrows(DomainException.class, () ->
//                Book.createBook("isb-0001", "title", "author", 1999, "publisher", 0)
//        );
//        assertEquals("Total copies must be positive ", exception.getMessage());
//    }

    @Test
    void testBorrowBook_success(){
        book.borrow();
        assertEquals(49, book.getAvailableCopies());
        assertTrue(book.isAvailable());
    }

    @Test
    void testBorrowBook_whenNoCopiesAvailable_shouldThrowException(){
        book.setAvailableCopies(0);
        DomainException exception = assertThrows(DomainException.class, () -> book.borrow());
        assertEquals("Book with isbn " + book.getIsbn() + " is not available for borrowing", exception.getMessage());
    }

    @Test
    void testRetrnBook_success(){
        book.setAvailableCopies(3);
        book.returnBook();
        assertEquals(4, book.getAvailableCopies());
    }

    @Test
    void testReturnBook_whenMaxCopiesExceeded_shouldThrowException(){
        book.setAvailableCopies(51);
        DomainException exception = assertThrows(DomainException.class, () -> book.returnBook());
        assertEquals("All copies are already available", exception.getMessage());
    }

    @Test
    void testAddCopies_success(){
        book.addCopies(3);
        assertEquals(53, book.getTotalCopies());
        assertEquals(53, book.getAvailableCopies());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -5})
    void testAddCopies_withInvalidNumber_shouldThrowsException(int additionalCopes){
        DomainException exception = assertThrows(DomainException.class, () -> book.addCopies(additionalCopes));
        assertEquals("Additional copies must be positive", exception.getMessage());
    }

    @Test
    void testIsAvailable_whenActiveAndHasCopies_should_returnTrue(){
        book.setActive(true);
        book.setAvailableCopies(3);
        assertTrue(book.isAvailable());
    }

    @Test
    void testIsAvailable_whenInactive_shouldReturnFalse(){
        book.setActive(false);
        book.setAvailableCopies(3);
        assertFalse(book.isAvailable());
    }

    @Test
    void testAvailable_whenNoCopies_shouldReturnFalse(){
        book.setActive(true);
        book.setAvailableCopies(0);
        assertFalse(book.isAvailable());
    }

    @Test
    void testDeactivate_success(){
        book.deactivate();
        assertFalse(book.isActive());
    }

}