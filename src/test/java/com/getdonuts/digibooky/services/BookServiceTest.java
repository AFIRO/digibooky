package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.repository.BookRepository;
import com.getdonuts.digibooky.repository.UserRepository;
import com.getdonuts.digibooky.services.mapper.BookMapper;
import com.getdonuts.digibooky.services.mapper.UserMapper;
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
        UserService userService = new UserService(new UserRepository(), new UserMapper());
        BookService bookService = new BookService(bookRepository, bookMapper, userService);
        bookService.getAllBooks().stream()
                .forEach(System.out::println);

    }

}