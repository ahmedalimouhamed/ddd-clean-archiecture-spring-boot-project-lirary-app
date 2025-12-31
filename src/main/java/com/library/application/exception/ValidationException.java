package com.library.application.exception;

public class ValidationException extends ApplicationException{
    public ValidationException(String message){
        super(message, "VALIDATION_ERROR");
    }
}
