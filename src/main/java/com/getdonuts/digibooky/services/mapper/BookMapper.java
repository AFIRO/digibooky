package com.getdonuts.digibooky.services.mapper;

import com.getdonuts.digibooky.api.dto.BookDto;
import com.getdonuts.digibooky.api.dto.BookWithSummaryDto;
import com.getdonuts.digibooky.api.dto.UpdateBookDto;
import com.getdonuts.digibooky.domain.Book;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public BookDto mapToDto(Book book) {

        return new BookDto()
                .setISBN(book.getISBN())
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor());
    }

    public BookWithSummaryDto mapToBookWithSummaryDto(Book book) {
        return new BookWithSummaryDto()
                .setISBN(book.getISBN())
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setLent(book.isLent())
                .setSummary(book.getSummary());
    }

    public Collection<BookDto> mapToDto(Collection<Book> books) {
        return books.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Book MapBookSummaryDTOtoBook(BookWithSummaryDto book){
        return new Book()
                .setISBN(book.getISBN())
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setLent(book.isLent())
                .setSummary(book.getSummary());
    }

    public Book updateBookDtoToBook(UpdateBookDto updateBookDto, Book book) {

        if (isGiven(updateBookDto.getTitle()))
            book.setTitle(updateBookDto.getTitle());

        if (isGiven(updateBookDto.getAuthor().getFirstName())){
            book.getAuthor().setFirstName(updateBookDto.getAuthor().getFirstName());
        }

        if (isGiven(updateBookDto.getAuthor().getLastName())){
            book.getAuthor().setLastName(updateBookDto.getAuthor().getLastName());
        }

        if (isGiven(updateBookDto.getSummary()))
            book.setSummary(updateBookDto.getSummary());

        return book;
    }

    public boolean isGiven(String input) {
        return !(input == null || input.isEmpty() || input.isBlank());
    }
}
