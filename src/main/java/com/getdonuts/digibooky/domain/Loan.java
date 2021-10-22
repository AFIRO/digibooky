package com.getdonuts.digibooky.domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Loan {
    private final String loanId;
    private String userId;
    private String isbn;
    private final LocalDate lendingDate;
    private LocalDate dueDate;

    private static final int DEFAULT_DUE_PERIOD = 3;

    public Loan(String userId, String isbn) {
        this(userId, isbn, LocalDate.now().plusWeeks(DEFAULT_DUE_PERIOD));
    }

    public Loan(String userId, String isbn, LocalDate dueDate) {
        this.loanId = UUID.randomUUID().toString();
        this.userId = userId;
        this.isbn = isbn;
        this.lendingDate = LocalDate.now();
        this.dueDate = dueDate;
    }

    public String getLoanId() {
        return loanId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getLendingDate() {
        return lendingDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Loan)) return false;
        Loan loan = (Loan) o;
        return Objects.equals(getLoanId(), loan.getLoanId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLoanId());
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId='" + loanId + '\'' +
                ", userId='" + userId + '\'' +
                ", isbn='" + isbn + '\'' +
                ", lendingDate=" + lendingDate +
                ", dueDate=" + dueDate +
                '}';
    }
}
