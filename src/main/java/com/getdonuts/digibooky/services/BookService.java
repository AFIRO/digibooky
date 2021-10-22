package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.api.dto.BookDto;
import com.getdonuts.digibooky.api.dto.BookWithSummaryDto;
import com.getdonuts.digibooky.domain.Book;
import com.getdonuts.digibooky.exceptions.AuthorisationException;
import com.getdonuts.digibooky.repository.BookRepository;
import com.getdonuts.digibooky.services.mapper.BookMapper;
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

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper, UserService userService) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.userService = userService;
    }

    public Collection<BookDto> getAllBooks() {
        return bookMapper.mapToDto(bookRepository.getAllBooks());
    }

    public BookWithSummaryDto getBook(String isbn) {
        return bookMapper.mapToBookWithSummaryDto(bookRepository.getBook(isbn));
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

    public BookWithSummaryDto SaveBook(BookWithSummaryDto dto, String id) {
        if (userService.validateLibrarian(id)) {
            Book savedBook = bookMapper.MapBookSummaryDTOtoBook(dto);
            bookRepository.registerANewBook(savedBook);
            return dto;
        } else
            throw new AuthorisationException();


    }
}
