package com.getdonuts.digibooky.api.dto;

import java.time.LocalDate;

public class CreateLoanDto {

    private String userId;
    private String isbn;
    private LocalDate dueDate;

    public CreateLoanDto(String userId, String isbn, LocalDate dueDate) {
        this.userId = userId;
        this.isbn = isbn;
        this.dueDate = dueDate == null ? LocalDate.now().plusWeeks(3) : dueDate;
    }

    public String getUserId() {
        return userId;
    }

    public CreateLoanDto setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public CreateLoanDto setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public CreateLoanDto setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }
}
