package com.getdonuts.digibooky.domain;

public class Book {

    private String ISBN;
    private String title;
    private Author author;
    private String summary;

    public Book(String ISBN, String title, Author author, String summary) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.summary = summary;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN='" + ISBN + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                '}';
    }
}
