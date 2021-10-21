package com.getdonuts.digibooky.api;

import com.getdonuts.digibooky.api.dto.BookDto;
import com.getdonuts.digibooky.api.dto.BookWithSummaryDto;
import com.getdonuts.digibooky.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping(produces = "application/json", path = "/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public BookWithSummaryDto getBook(@PathVariable(value = "isbn", required = false) String isbn) {
        return bookService.getBook(isbn);
    }


    @GetMapping(produces = "application/json", path = "/search", params = "isbn")
    @ResponseStatus(HttpStatus.OK)
    public Collection<BookDto> getBookWithRegexIsbn(@RequestParam(value = "isbn") String isbnRegex) {
        return bookService.getBookWithRegexIsbn(isbnRegex);
    }

    @GetMapping(produces = "application/json", path = "/search", params = "title")
    @ResponseStatus(HttpStatus.OK)
    public Collection<BookDto> getBookByTitleWithRegex(@RequestParam(value = "title") String titleRegex){
        return bookService.getBookWithRegexTitle(titleRegex);
    }

    @GetMapping(produces = "application/json", path = "/search" /*, params = {"firstName", "lastName"}*/)
    @ResponseStatus(HttpStatus.OK)
    public Collection<BookDto> getBookByAuthor(
            @RequestParam(name = "firstName", required = false, defaultValue = "") String firstname,
            @RequestParam(name = "lastName", required = false, defaultValue = "") String lastname){
        System.out.println("I received your request");
        return bookService.getBookByAuthor(firstname, lastname);
    }
}
