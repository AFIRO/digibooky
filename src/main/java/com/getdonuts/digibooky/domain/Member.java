package com.getdonuts.digibooky.domain;

import com.getdonuts.digibooky.services.MemberService;

import java.util.UUID;

public class Member {

    private String INSS;
    private String firstName;
    private String lastName;
    private String id;
    private String email;
    private Address address;


    public Member(String INSS, String firstName, String lastName, String email, Address address) {
        this.id = UUID.randomUUID().toString();

        if (MemberService.isINSSunique(INSS))
            this.INSS = INSS;

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

    public void setEmail(String email) {

        if (MemberService.isEmailValid(email) && MemberService.isEmailUnique(email))
            this.email = email;

    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.isEmpty() || lastName.isBlank() )
            throw new IllegalStateException();
        this.lastName = lastName;
    }
}
