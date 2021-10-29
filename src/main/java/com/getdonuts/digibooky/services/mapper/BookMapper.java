package com.getdonuts.digibooky.services.mapper;

import com.getdonuts.digibooky.api.dto.BookDto;
import com.getdonuts.digibooky.api.dto.BookWithDetailsDto;
import com.getdonuts.digibooky.api.dto.CreateBookDto;
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

    public BookWithDetailsDto mapToBookWithDetailsDto(Book book) {
        return new BookWithDetailsDto()
                .setISBN(book.getISBN())
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setLent(book.isLent())
                .setSummary(book.getSummary());
    }

    public Collection<BookDto> mapToDto(Collection<Book> books) {
        // CODEREVIEW why not make this a List(2x)
        return books.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Book MapBookSummaryDTOtoBook(BookWithDetailsDto book) {
        return new Book()
                .setISBN(book.getISBN())
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setLent(book.isLent())
                .setSummary(book.getSummary());
    }

    public BookWithDetailsDto mapCreateBookDtotoBookWithDetailsDto(CreateBookDto dto) {
        return new BookWithDetailsDto()
                .setISBN(dto.getISBN())
                .setAuthor(dto.getAuthor())
                .setTitle(dto.getTitle())
                .setSummary(dto.getSummary());
    }

    public Book updateBookDtoToBook(UpdateBookDto updateBookDto, Book book) {

        if (isGiven(updateBookDto.getTitle()))
            book.setTitle(updateBookDto.getTitle());

        if (isGiven(updateBookDto.getAuthor().getFirstName())) {
            book.getAuthor().setFirstName(updateBookDto.getAuthor().getFirstName());
        }

        if (isGiven(updateBookDto.getAuthor().getLastName())) {
            book.getAuthor().setLastName(updateBookDto.getAuthor().getLastName());
        }

        if (isGiven(updateBookDto.getSummary()))
            book.setSummary(updateBookDto.getSummary());

        return book;
    }

    public boolean isGiven(String input) {
        return !(input == null || input.isEmpty() || input.isBlank());
    }

    public BookDto summaryBookDtoToBookDto(BookWithDetailsDto bookWithDetailsDto) {
        return new BookDto()
                .setISBN(bookWithDetailsDto.getISBN())
                .setTitle(bookWithDetailsDto.getTitle())
                .setAuthor(bookWithDetailsDto.getAuthor());
    }
}
