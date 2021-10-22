package com.getdonuts.digibooky.domain;

public class Book {

    private String ISBN;
    private String title;
    private Author author;
    private String summary;
    private boolean isLent;
    private boolean isPassive;

    public Book() {
    }

    public Book(String ISBN, String title, Author author, String summary) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.summary = summary;
    }

    public String getISBN() {
        return ISBN;
    }

    public Book setISBN(String ISBN) {
        this.ISBN = ISBN;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public Book setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public Book setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public boolean isLent() {
        return isLent;
    }

    public void setLent(boolean lent) {
        isLent = lent;
    }

    public boolean isPassive() {
        return isPassive;
    }

    public void setPassive(boolean passive) {
        isPassive = passive;
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
