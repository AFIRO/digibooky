package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.api.dto.BookDto;
import com.getdonuts.digibooky.api.dto.BookWithSummaryDto;
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

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public Collection<BookDto> getAllBooks() {
        return bookMapper.mapToDto(bookRepository.getAllBooks());
    }

    public BookWithSummaryDto getBook(String isbn) {
        return bookMapper.mapToBookWithSummaryDto(bookRepository.getBook(isbn));
    }

    public  Collection<BookDto> getBookWithRegexIsbn(String regex){

        return getAllBooks().stream()
                .filter(bookDto -> checkForRegexMatch(regex, bookDto.getISBN()))
                .collect(Collectors.toList());
    }

    private boolean checkForRegexMatch(String regex, String target){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    public Collection<BookDto> getBookWithRegexTitle(String titleRegex) {
        return getAllBooks().stream()
                .filter(bookDto -> checkForRegexMatch(titleRegex, bookDto.getTitle()))
                .collect(Collectors.toList());
    }

    public boolean validateISBN(String ISBN) {
        int o = Character.getNumericValue(ISBN.charAt(0)) + Character.getNumericValue(ISBN.charAt(2)) + Character.getNumericValue(ISBN.charAt(4)) + Character.getNumericValue(ISBN.charAt(6)) + Character.getNumericValue(ISBN.charAt(8)) + Character.getNumericValue(ISBN.charAt(10));
        int e = Character.getNumericValue(ISBN.charAt(1)) + Character.getNumericValue(ISBN.charAt(3)) + Character.getNumericValue(ISBN.charAt(5)) + Character.getNumericValue(ISBN.charAt(7)) + Character.getNumericValue(ISBN.charAt(9)) + Character.getNumericValue(ISBN.charAt(11));

        int controlNumber = (10 - (o + (3 * e)) % 10) % 10;

        return controlNumber == Character.getNumericValue(ISBN.charAt(12));
    }
}
