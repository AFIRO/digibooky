package com.getdonuts.digibooky.repository;

import com.getdonuts.digibooky.domain.Author;
import com.getdonuts.digibooky.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
        return booksByIsbn.values().stream()
                .filter(book -> !book.isPassive())
                .collect(Collectors.toList());
    }

    public Collection<Book> getAllBooksIncludingPassiveBooks(){
        return booksByIsbn.values().stream()
                .collect(Collectors.toList());
    }


    public Book getBook(String isbn){
        if(!booksByIsbn.containsKey(isbn)){
            throw new IllegalArgumentException("ISBN should not be empty");
        }

        if(booksByIsbn.get(isbn).isPassive()){
            throw new IllegalArgumentException("The library does not contain book: " + isbn);
        }

        return booksByIsbn.get(isbn);
    }

    public Book registerANewBook(Book book){
        booksByIsbn.put(book.getISBN(), book);
        return book;
    }
}
