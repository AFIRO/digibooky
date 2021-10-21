package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.api.dto.BookDto;
import com.getdonuts.digibooky.api.dto.BookWithSummaryDto;
import com.getdonuts.digibooky.domain.Book;
import com.getdonuts.digibooky.repository.BookRepository;
import com.getdonuts.digibooky.services.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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

    public BookWithSummaryDto getBook(String isbn){
        return bookMapper.mapToBookWithSummaryDto(bookRepository.getBook(isbn));
    }

    public  Collection<BookDto> getBookWithRegexIsbn(String regex){

        return getAllBooks().stream()
                .filter(bookDto -> checkForRegexMatch(regex, bookDto.getISBN())).collect(Collectors.toList());
    }

    private boolean checkForRegexMatch(String regex, String target){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
}
