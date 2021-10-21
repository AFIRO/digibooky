package com.getdonuts.digibooky.domain;

import com.getdonuts.digibooky.exceptions.InvalidEmailException;
import com.getdonuts.digibooky.services.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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


 //   @BeforeEach
    void setUp() {
        address = new Address(street, houseNr, postalCode, city);
        member = new Member(inss, firstName, lastName, email, address);
    }

    @Test
    void emailAddressValidatorWorks() {
        //when
        String email1 = "test@test.be";
        //then
        assertTrue(MemberService.isEmailValid(email1));
    }

    @Test
    void emailAddressValidatorThrowsExceptionWhenGivenInvalidEmail() {
        //when
        String email2 = "test@";
        String email3 = "test@test.";
        String email4 = "@test.be";
        String email5 = "test.be";
        String email6 = "test@@test.be";
        String email7 = "te.st@te.st";
        //then
        assertThrows(InvalidEmailException.class, () -> MemberService.isEmailValid(email2));
        assertThrows(InvalidEmailException.class, () -> MemberService.isEmailValid(email3));
        assertThrows(InvalidEmailException.class, () -> MemberService.isEmailValid(email4));
        assertThrows(InvalidEmailException.class, () -> MemberService.isEmailValid(email5));
        assertThrows(InvalidEmailException.class, () -> MemberService.isEmailValid(email6));
        assertThrows(InvalidEmailException.class, () -> MemberService.isEmailValid(email7));
    }

    @Test
    void tetn() {

    }
}