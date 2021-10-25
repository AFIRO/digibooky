package com.getdonuts.digibooky.api.dto;

import com.getdonuts.digibooky.domain.Author;

public class BookWithDetailsDto {

    private String ISBN;
    private String title;
    private Author author;
    private String summary;
    private boolean isLent;

    public String getISBN() {
        return ISBN;
    }

    public BookWithDetailsDto setISBN(String ISBN) {
        this.ISBN = ISBN;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookWithDetailsDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public BookWithDetailsDto setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public BookWithDetailsDto setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public boolean isLent() {
        return isLent;
    }

    public BookWithDetailsDto setLent(boolean lent) {
        isLent = lent;
        return this;
    }
}
