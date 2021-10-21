package com.getdonuts.digibooky.services.mapper;

import com.getdonuts.digibooky.api.dto.BookDto;
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


    public Collection<BookDto> mapToDto(Collection<Book> books) {
        return books.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }



}
