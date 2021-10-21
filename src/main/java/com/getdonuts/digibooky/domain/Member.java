package com.getdonuts.digibooky.domain;

import com.getdonuts.digibooky.services.EmailService;

import java.util.UUID;

public class Member {

    private final String INSS;
    private final String firstName;
    private final String lastName;
    private final String id;
    private  String email;
    private Address address;


    public Member(String INSS, String firstName, String lastName, String email, Address address) {
        this.id = UUID.randomUUID().toString();
        this.INSS = INSS;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

    public void setEmail(String email) {

        EmailService.isEmailUnique(email);

        EmailService.isValid(email);

    }
}
