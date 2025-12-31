package com.library.domain.model;

import com.library.domain.exception.DomainException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Member {
    private Long id;
    private String memberId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;
    private boolean active;
    private int borrowedBookCount;
    private final int MAX_BORROWED_BOOKS = 5;

    public Member(){
        this.membershipStartDate = LocalDate.now();
        this.membershipEndDate = LocalDate.now().plusYears(1);
        this.active = true;
        this.borrowedBookCount = 0;
    }

    public static Member create(String memberId, String firstName, String lastName, String email, String phone){
        Member member = new Member();
        member.setMemberId(memberId);
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setEmail(email);
        member.setPhone(phone);

        member.validate();
        return member;
    }

    public void validate(){
        if(memberId == null || memberId.trim().isEmpty()){
            throw new DomainException("Member ID is required");
        }

        if(firstName == null || firstName.trim().isEmpty()){
            throw new DomainException("First name is required");
        }

        if(lastName == null || lastName.trim().isEmpty()){
            throw new DomainException("Last name is required");
        }
    }

    public boolean canBorrowMoreBooks(){
        return active && borrowedBookCount < MAX_BORROWED_BOOKS;
    }

    public void borrowBook(){
        if(!canBorrowMoreBooks()){
            throw new DomainException("Member cannot borrow more books");
        }
        this.borrowedBookCount++;
    }

    public void returnBook(){
        if(this.borrowedBookCount <= 0){
            throw new DomainException("Member has no borrowed books");
        }
    }

    public void deactivate(){
        if(this.borrowedBookCount > 0){
            throw new DomainException("Cannot deactivate member with borrowed books");
        }

        this.active = false;
    }

    public void renewMembership(){
        this.membershipEndDate = LocalDate.now().plusYears(1);
        this.active = true;
    }
}
