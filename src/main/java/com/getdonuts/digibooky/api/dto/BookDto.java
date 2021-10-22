package com.getdonuts.digibooky.api.dto;

import com.getdonuts.digibooky.domain.Author;

public class BookDto {

    private String ISBN;
    private String title;
    private Author author;


    public String getISBN() {
        return ISBN;
    }

    public BookDto setISBN(String ISBN) {
        this.ISBN = ISBN;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public BookDto setAuthor(Author author) {
        this.author = author;
        return this;
    }

}
