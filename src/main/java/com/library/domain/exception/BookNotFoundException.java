package com.library.domain.exception;

public class BookNotFoundException extends DomainException{
    public BookNotFoundException(String isbn){
        super("Book not found with isbn : "+ isbn);
    }

    public BookNotFoundException(Long id){
        super("Book not found with ID : "+ id);
    }
}
