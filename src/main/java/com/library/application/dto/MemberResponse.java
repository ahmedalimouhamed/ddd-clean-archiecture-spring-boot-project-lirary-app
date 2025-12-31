package com.library.application.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberResponse {
    private Long id;
    private String memberId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;
    private boolean active;
    private int borrowedBooksCount;
    private int maxBorrowedBooks;

}
