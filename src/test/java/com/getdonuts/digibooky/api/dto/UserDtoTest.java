package com.getdonuts.digibooky.api.dto;

import com.getdonuts.digibooky.domain.Address;
import com.getdonuts.digibooky.domain.Author;
import com.getdonuts.digibooky.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    @Test
    void givenCorrectInputs_WhenBuilderIsCalled_ReturnObject(){
        UserDto toTest = new UserDto("323424","John", "Doe","test@test.com","Fakestreet", "123", "1337","Springfield");
        var toTestAgainst = toTest;

        Assertions.assertThat(toTest).isEqualTo(toTestAgainst);

    }

}