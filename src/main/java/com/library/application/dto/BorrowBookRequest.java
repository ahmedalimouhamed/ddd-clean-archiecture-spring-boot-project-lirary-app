package com.library.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BorrowBookRequest {
    @NotNull(message = "Book id is required")
    private Long bookI;

    @NotNull(message = "Member ID is required")
    private Long memberId;

    private String notes;
}
