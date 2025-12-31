package com.library.application.exception;

public class NotFoundException extends ApplicationException {
    public NotFoundException(String message) {
        super(message, "NOT_FOUND");
    }
}
