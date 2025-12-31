package com.library.domain.exception;

public class MemberNotFoundException extends DomainException{
    public MemberNotFoundException(Long id){
        super("Member not found with ID : " + id);
    }
}
