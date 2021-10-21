package com.getdonuts.digibooky.api;

import com.getdonuts.digibooky.api.dto.BookDto;
import com.getdonuts.digibooky.api.dto.BookWithSummaryDto;
import com.getdonuts.digibooky.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Collection<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(produces = "application/json", path="/{isbn}" )
    @ResponseStatus(HttpStatus.OK)
    public BookWithSummaryDto getBook(@PathVariable(value = "isbn", required = false) String isbn) {
        return bookService.getBook(isbn);
    }



}
