package com.getdonuts.digibooky.api.dto;

import java.time.LocalDate;

public class ReturnLoanDto {

    private String loanId;
    private String userId;
    private String isbn;
    private final LocalDate returnDate = LocalDate.now();
    private LocalDate dueDate;
    private String message;

    public String getLoanId() {
        return loanId;
    }

    public ReturnLoanDto setLoanId(String loanId) {
        this.loanId = loanId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public ReturnLoanDto setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public ReturnLoanDto setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public ReturnLoanDto setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ReturnLoanDto setMessage(String message) {
        this.message = message;
        return this;
    }
}
