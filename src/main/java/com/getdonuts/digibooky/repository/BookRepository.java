package com.getdonuts.digibooky.repository;

import com.getdonuts.digibooky.domain.Author;
import com.getdonuts.digibooky.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookRepository {

    private final ConcurrentHashMap<String, Book> booksByIsbn;

    public BookRepository() {
        this.booksByIsbn = new ConcurrentHashMap<>();
        booksByIsbn.put("1A", new Book("1A", "Head First",  new Author("James", "Brown"), "Summary"));
        booksByIsbn.put("1B", new Book("1B", "Design Patterns",  new Author("Bob", "Down"), "Summary"));
        booksByIsbn.put("1C", new Book("1C", "Design furniture",  new Author("George", "Down"), "Summary"));
        booksByIsbn.put("1D", new Book("1D", "Cool furniture",  new Author("Bob", "Up"), "Summary"));
    }

    public Collection<Book> getAllBooks(){
        return booksByIsbn.values();
    }

    public Book getBook(String isbn){
        if(!booksByIsbn.containsKey(isbn)){
            throw new IllegalArgumentException("ISBN should not be empty");
        }
        return booksByIsbn.get(isbn);
    }

    public Book searchBook(String isbn){
        return null;
    }

    public Book getBookByTitle(String title){
        return null;
    }

    public Book getBookByAuthor(Author author){
        return null;
    }

    public Book registerANewBook(Book book){
        booksByIsbn.put(book.getISBN(), book);
        return book;
    }

    public Book deleteBook(String isbn){
        return null;
    }
}
