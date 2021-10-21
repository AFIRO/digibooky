package com.getdonuts.digibooky.domain;

import org.apache.tomcat.jni.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    private Member member;
    private String inss = "TestValue";
    private String firstName = "TestFirstName";
    private String lastName = "TestLastName";
    private String email = "TestEMail@TestProvider.com";
    private String street = "TestStreet";
    private int houseNr = 1;
    private int postalCode = 1000;
    private String city = "TestCity";
    private Address address;


    @BeforeEach
    void setUp() {
        address = new Address(street, houseNr, postalCode, city);
        member = new Member(inss, firstName, lastName, email, address);
    }

    @Test
    void emailAddressValidatorWorks() {
        //when
        String email1 = "test@test.be";
        String email2 = "test@";
        String email3 = "test@test.";
        String email4 = "@test.be";
        String email5 = "test.be";
        EmailService es = new EmailService();
        //then
        assertTrue(es.isValid(email1) && !isValid(email2) &&
                !isValid(email3) && !isValid(email4) &&
                !isValid(email5));
    }

    @Test
    void tetn() {

    }
}