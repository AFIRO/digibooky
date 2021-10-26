package com.getdonuts.digibooky.api.dto;

import com.getdonuts.digibooky.domain.Author;

public class CreateBookDto {

    private String ISBN;
    private String title;
    private Author author;
    private String summary;

    public CreateBookDto() {
        this(null, null, null, null);
    }

    public CreateBookDto(String ISBN, String title, Author author) {
        this(ISBN, title, author, null);
    }

    public CreateBookDto(String ISBN, String title, Author author, String summary) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.summary = summary;
    }

    public CreateBookDto setISBN(String ISBN) {
        this.ISBN = ISBN;
        return this;
    }

    public CreateBookDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public CreateBookDto setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public CreateBookDto setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public String getSummary() {
        return summary;
    }
}
