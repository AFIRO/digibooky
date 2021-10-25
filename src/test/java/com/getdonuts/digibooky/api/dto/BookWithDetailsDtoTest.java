package com.getdonuts.digibooky.api.dto;

import com.getdonuts.digibooky.domain.Author;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BookWithDetailsDtoTest {

    @Test
    void givenCorrectInputs_WhenBuilderIsCalled_ReturnObject(){
        BookWithDetailsDto toTest = new BookWithDetailsDto()
                .setISBN("342332434")
                .setAuthor(new Author("testy","mctest"))
                .setTitle("testbook")
                .setSummary("test");
        var toTestAgainst = toTest;

        Assertions.assertThat(toTest).isEqualTo(toTestAgainst);

    }

}