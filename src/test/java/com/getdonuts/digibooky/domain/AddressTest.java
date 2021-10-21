package com.getdonuts.digibooky.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void givenNoCityThrowsIllegalArgumentException() {
        //then
        assertThrows(IllegalArgumentException.class, () -> new Address("Street", "2", "1000" ,null));
    }

}