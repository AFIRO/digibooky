package com.getdonuts.digibooky.domain;

import com.getdonuts.digibooky.services.EmailService;
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
    private String houseNr = "1";
    private String postalCode = "1000";
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
        String email6 = "test@@test.be";
        String email7 = "te.st@te.st";
        EmailService es = new EmailService();
        //then
        assertTrue(es.isValid(email1) && !es.isValid(email2) &&
                !es.isValid(email3) && !es.isValid(email4) &&
                !es.isValid(email5) && !es.isValid(email6) &&
                !es.isValid(email7));
    }

    @Test
    void tetn() {

    }
}