package com.library.application.usecases;

import com.library.application.dto.BorrowBookRequest;
import com.library.application.dto.BorrowingRecordResponse;

import java.util.List;

public interface BorrowingUseCase {
    BorrowingRecordResponse borrowBook(BorrowBookRequest request);
    void returnBook(Long bookId, Long memberId);
    List<BorrowingRecordResponse> getBorrowingHistory(Long memberId);
}
