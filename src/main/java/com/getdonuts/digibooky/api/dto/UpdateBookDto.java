package com.getdonuts.digibooky.api.dto;

import com.getdonuts.digibooky.domain.Author;

// CODEREVIEW Builder pattern (6x)
// ALthough Pieter really really likes the Builder pattern
// The implementation below does not follow the rules associated with it
// YES, you do have a (strange) fluent API
// But it doesn't have the structure of a builder available
// Nor does it follow the naming convention: .withIsb(), .withTitle()
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
