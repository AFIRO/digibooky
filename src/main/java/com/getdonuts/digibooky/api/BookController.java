package com.getdonuts.digibooky.api;

import com.getdonuts.digibooky.api.dto.BookDto;
import com.getdonuts.digibooky.api.dto.BookWithDetailsDto;
import com.getdonuts.digibooky.api.dto.UpdateBookDto;
import com.getdonuts.digibooky.services.BookService;
import com.getdonuts.digibooky.services.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private final BookService bookService;
    private final LoanService loanService;
    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    public BookController(BookService bookService, LoanService loanService) {
        this.bookService = bookService;
        this.loanService = loanService;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Collection<BookDto> getAllBooks() {
        logger.info("getAllBooks() called");
        return bookService.getAllBooks();
    }

    @GetMapping(produces = "application/json", path = "/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public BookWithDetailsDto getBook(@PathVariable(value = "isbn", required = false) String isbn) {
        logger.info("getBook() called");
        return loanService.getBookWithDetails(isbn);
    }

    @GetMapping(produces = "application/json", path = "/searchByISBN", params = "isbn")
    @ResponseStatus(HttpStatus.OK)
    public Collection<BookDto> getBookWithRegexIsbn(@RequestParam(value = "isbn") String isbnRegex) {
        logger.info("getBookWithRegexIsbn() called");
        return bookService.getBookWithRegexIsbn(isbnRegex);
    }

    @GetMapping(produces = "application/json", path = "/searchByTitle", params = "title")
    @ResponseStatus(HttpStatus.OK)
    public Collection<BookDto> getBookByTitleWithRegex(@RequestParam(value = "title") String titleRegex) {
        logger.info("getBookByTitleWithRegex() called");
        return bookService.getBookWithRegexTitle(titleRegex);
    }

    @GetMapping(produces = "application/json", path = "/searchByAuthor" /*, params = {"firstName", "lastName"}*/)
    @ResponseStatus(HttpStatus.OK)
    public Collection<BookDto> getBookByAuthor(
            @RequestParam(name = "firstName", required = false, defaultValue = "") String firstname,
            @RequestParam(name = "lastName", required = false, defaultValue = "") String lastname) {
        logger.info("getBookByAuthor() called");
        return bookService.getBookByAuthor(firstname, lastname);
    }

    @PostMapping(produces = "application/json", consumes = "application/json", path = "/{id})")
    @ResponseStatus(HttpStatus.CREATED)
    public BookWithDetailsDto createBook(@RequestBody BookWithDetailsDto dto, @PathVariable("id") String id) {
        logger.info("createBook() called");
        return bookService.saveBook(dto, id);
    }

    @PatchMapping(produces = "application/json", consumes = "application/json", path = "/{id}/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public BookWithDetailsDto updateBook(@RequestBody UpdateBookDto dto, @PathVariable("id") String id, @PathVariable("isbn") String isbn) {
        logger.info("updateBook() called");
        return bookService.updateBook(dto, isbn, id);
    }

    @DeleteMapping(path = "/{id}/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public boolean toggleDeleteBook(@PathVariable("id") String id, @PathVariable("isbn") String isbn) {
        logger.info("toggleDeleteBook() called");
        return bookService.toggleDeleteBook(isbn, id);
    }
}
