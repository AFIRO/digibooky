package com.getdonuts.digibooky.api.dto;

import com.getdonuts.digibooky.domain.Author;

public class BookWithSummaryDto {

    private String ISBN;
    private String title;
    private Author author;
    private String summary;
    private boolean isLent;

    public String getISBN() {
        return ISBN;
    }

    public BookWithSummaryDto setISBN(String ISBN) {
        this.ISBN = ISBN;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookWithSummaryDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public BookWithSummaryDto setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public BookWithSummaryDto setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public boolean isLent() {
        return isLent;
    }

    public BookWithSummaryDto setLent(boolean lent) {
        isLent = lent;
        return this;
    }
}
