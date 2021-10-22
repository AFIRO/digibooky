package com.getdonuts.digibooky.api.dto;

import com.getdonuts.digibooky.domain.Author;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookDtoTest {

    @Test
    void givenCorrectInputs_WhenBuilderIsCalled_ReturnObject(){
        BookDto toTest = new BookDto()
                .setISBN("342332434")
                .setAuthor(new Author("testy","mctest"))
                .setTitle("testbook");
        var toTestAgainst = toTest;

        Assertions.assertThat(toTest).isEqualTo(toTestAgainst);

    }

}