package com.library.domain.exception;

public class BookNotAvailableException extends DomainException{
    public BookNotAvailableException(String isbn){
        super("Book with isbn " + isbn + " is not available for borrowing");
    }
}
