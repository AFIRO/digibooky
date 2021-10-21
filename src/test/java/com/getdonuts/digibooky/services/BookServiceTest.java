package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.repository.BookRepository;
import com.getdonuts.digibooky.services.mapper.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

//    BookService service;
//
//    public BookServiceTest(BookService service) {
//        this.service = service;
//    }


    @Test
    void whenInsertingRegex_methodShouldReturnAListOfBook(){
        //Given
        BookRepository bookRepository = new BookRepository();
        BookMapper bookMapper = new BookMapper();
        BookService bookService = new BookService(bookRepository, bookMapper);
        bookService.getAllBooks().stream()
                .forEach(System.out::println);

    }

}