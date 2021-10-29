package com.getdonuts.digibooky.repository;

import com.getdonuts.digibooky.domain.Author;
import com.getdonuts.digibooky.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.Collection;
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
        // Very small suggestion: Return immutable collection
        return booksByIsbn.values().stream()
                .filter(book -> !book.isPassive())
                .collect(Collectors.toList());
    }

    public Collection<Book> getAllBooksIncludingPassiveBooks(){
        // CODEREVIEW follow the suggestion of IntelliJ ;-)
        // Very small suggestion: Return immutable collection
        return booksByIsbn.values().stream()
                .collect(Collectors.toList());
    }


    public Book getBook(String isbn){
        if(!booksByIsbn.containsKey(isbn)){
            // CODEREVIEW It might be better to throw an actual NoSuchBookException
            // This is more descriptive (linked to your domain) than the generic error below
            throw new IllegalArgumentException("ISBN should not be empty");
            // CODEREVIEW the message can be confusing
            // Did you mean `isbn` was null or empty ("")
            // Or did you mean no book matches this isbn?
        }

        // CODEREVIEW (Slight) risk of a NullPointerException
        // The test above was only to check if the KEY is present
        // The value can be null
        // BUT if the `registerANewBook()` method is used consistently, this cannot happen
        if(booksByIsbn.get(isbn).isPassive()){
            // CODEREVIEW It might be better to throw an actual NoSuchBookException
            // This is more descriptive
            throw new IllegalArgumentException("The library does not contain book: " + isbn);
        }

        return booksByIsbn.get(isbn);
    }

    public Book registerANewBook(Book book){
        booksByIsbn.put(book.getISBN(), book);
        return book;
    }
}
