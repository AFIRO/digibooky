package com.getdonuts.digibooky.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void givenNoCityThrowsIllegalArgumentException() {
        //given
        Address address = new Address("Street", "2", "1000", null);
        //then
        assertThrows(IllegalArgumentException.class, () -> address.setCity(null));
    }

}