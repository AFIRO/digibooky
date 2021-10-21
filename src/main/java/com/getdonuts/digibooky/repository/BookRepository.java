package com.getdonuts.digibooky.repository;

import com.getdonuts.digibooky.domain.Author;
import com.getdonuts.digibooky.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookRepository {

    private final ConcurrentHashMap<String, Book> booksByISBN;

    public BookRepository() {
        this.booksByISBN = new ConcurrentHashMap<>();
        booksByISBN.put("1A", new Book("1A", "Head First",  new Author("James", "Brown")));
        booksByISBN.put("1B", new Book("1B", "Design Patterns",  new Author("Bob", "Down")));
    }

    public Collection<Book> getAllBooks(){
        return booksByISBN.values();
    }

    public Book getBook(String ISBN){
        return null;
    }

    public Book searchBook(String ISBN){
        return null;
    }

    public Book getBookByTitle(String title){
        return null;
    }

    public Book getBookByAuthor(Author author){
        return null;
    }

    public Book registerANewBook(Book book){
        return null;
    }

    public Book updateBook(Book book, String ISBN){
        return null;
    }

    public Book deleteBook(String ISBN){
        return null;
    }


}
