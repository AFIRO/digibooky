package com.getdonuts.digibooky.api.dto;

import com.getdonuts.digibooky.domain.Author;

public class UpdateBookDto {

    private String title;
    private Author author;
    private String summary;


    public String getTitle() {
        return title;
    }

    public UpdateBookDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public UpdateBookDto setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public UpdateBookDto setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public String getSummary() {
        return summary;

    }




}
