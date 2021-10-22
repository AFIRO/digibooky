package com.getdonuts.digibooky.api.dto;

import java.time.LocalDate;

public class LoanDto {

    private String userId;
    private String isbn;
    private LocalDate dueDate;


    public String getUserId() {
        return userId;
    }

    public LoanDto setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public LoanDto setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LoanDto setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }
}
