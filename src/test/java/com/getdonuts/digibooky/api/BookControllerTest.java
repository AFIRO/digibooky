package com.getdonuts.digibooky.api;

import com.getdonuts.digibooky.domain.Author;
import com.getdonuts.digibooky.domain.Book;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;

class BookControllerTest {

    @Test
    void givenARepoOfBooks_whenGetAllBooks_thenReturnAllBooks(){
        // GIVEN
        ConcurrentHashMap<String, Book> booksByISBN = new ConcurrentHashMap<>();
        booksByISBN.put("1A", new Book("1A", "Head First",  new Author("James", "Brown"), "summary"));
        booksByISBN.put("1B", new Book("1B", "Design Patterns",  new Author("Bob", "Down"), "summary"));

        // WHEN


    }

}