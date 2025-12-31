package com.library.application.dto;

import jakarta.validation.constraints.NotNull;


public record BorrowingRecordResponse(
        Long bookI,
        Long memberId,
        String notes
) {
}
