package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.api.dto.BookDto;
import com.getdonuts.digibooky.api.dto.BookWithDetailsDto;
import com.getdonuts.digibooky.api.dto.CreateBookDto;
import com.getdonuts.digibooky.api.dto.UpdateBookDto;
import com.getdonuts.digibooky.domain.Book;
import com.getdonuts.digibooky.exceptions.AuthorisationException;
import com.getdonuts.digibooky.repository.BookRepository;
import com.getdonuts.digibooky.services.mapper.BookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper, UserService userService) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.userService = userService;
    }

    public Collection<BookDto> getAllBooks() {
        return bookMapper.mapToDto(bookRepository.getAllBooks());
    }

    public Collection<BookDto> getAllBooksIncludingPassiveBooks() {
        return bookMapper.mapToDto(bookRepository.getAllBooksIncludingPassiveBooks());
    }

    public BookWithDetailsDto getBook(String isbn) {
        return bookMapper.mapToBookWithDetailsDto(bookRepository.getBook(isbn));
    }

    public Book getBookFromRepo(String isbn) {
        return bookRepository.getBook(isbn);
    }

    public Collection<BookDto> getBookWithRegexIsbn(String regex) {

        return getAllBooks().stream()
                .filter(bookDto -> checkForRegexMatch(regex, bookDto.getISBN()))
                .collect(Collectors.toList());
    }

    private boolean checkForRegexMatch(String regex, String target) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    public Collection<BookDto> getBookWithRegexTitle(String titleRegex) {
        return getAllBooks().stream()
                .filter(bookDto -> checkForRegexMatch(titleRegex, bookDto.getTitle()))
                .collect(Collectors.toList());
    }

    public Collection<BookDto> getBookByAuthor(String firstname, String lastname) {
        return getAllBooks().stream()
                .filter(bookDto -> checkForRegexMatch(firstname, bookDto.getAuthor().getFirstName()))
                .filter(bookDto -> checkForRegexMatch(lastname, bookDto.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    public boolean validateISBN(String ISBN) {
        int o = Character.getNumericValue(ISBN.charAt(0)) + Character.getNumericValue(ISBN.charAt(2)) + Character.getNumericValue(ISBN.charAt(4)) + Character.getNumericValue(ISBN.charAt(6)) + Character.getNumericValue(ISBN.charAt(8)) + Character.getNumericValue(ISBN.charAt(10));
        int e = Character.getNumericValue(ISBN.charAt(1)) + Character.getNumericValue(ISBN.charAt(3)) + Character.getNumericValue(ISBN.charAt(5)) + Character.getNumericValue(ISBN.charAt(7)) + Character.getNumericValue(ISBN.charAt(9)) + Character.getNumericValue(ISBN.charAt(11));

        int controlNumber = (10 - (o + (3 * e)) % 10) % 10;

        return controlNumber == Character.getNumericValue(ISBN.charAt(12));
    }

    public CreateBookDto saveBook(CreateBookDto dto, String id) {
        if (userService.validateLibrarian(id) && validateBook(bookMapper.mapCreateBookDtotoBookWithDetailsDto(dto))) {
            Book savedBook = bookMapper.MapBookSummaryDTOtoBook(bookMapper.mapCreateBookDtotoBookWithDetailsDto(dto));
            bookRepository.registerANewBook(savedBook);
            logger.info("Book " + dto.getISBN() + " is saved");
            return dto;
        } else
            throw new AuthorisationException("User without Librarian rights tried to save a book.");
    }

    public boolean validateBook(BookWithDetailsDto dto) {
        if (!isGiven(dto.getSummary()))
            dto.setSummary("No summary given.");

        if (isGiven(dto.getISBN()) && isGiven(dto.getAuthor().getFirstName()) && isGiven(dto.getAuthor().getLastName()) && isGiven(dto.getTitle()))
            return true;
        else
            throw new IllegalArgumentException("The information given for this book was not valid.");

    }

    public BookWithDetailsDto updateBook(UpdateBookDto dto, String isbn, String id) {

        if (userService.validateLibrarian(id)) {
            Book updatedBook = bookMapper.updateBookDtoToBook(dto, bookRepository.getBook(isbn));
            bookRepository.registerANewBook(updatedBook);
            logger.info("Book " + updatedBook.getISBN() + " is updated");
            return bookMapper.mapToBookWithDetailsDto(updatedBook);
        } else
            throw new AuthorisationException("User without Librarian rights tried to update a book.");
    }

    public boolean toggleDeleteBook(String isbn, String id) {
        if (userService.validateLibrarian(id) && exists(isbn)) {
            if (getBookFromRepo(isbn).isPassive()) {
                logger.info("Book : " + isbn + " put back in the collection");
            } else {
                logger.info("Book : " + isbn + " removed");
            }
            getBookFromRepo(isbn).togglePassive();
            return true;
        } else
            throw new AuthorisationException("User without Librarian rights tried to delete a book.");
    }

    public boolean isGiven(String input) {
        return !(input == null || input.isEmpty() || input.isBlank());
    }


    public boolean exists(String isbn) {
        return getAllBooksIncludingPassiveBooks().stream()
                .anyMatch(book -> book.getISBN().equals(isbn));
    }
}

