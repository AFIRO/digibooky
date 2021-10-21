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
        setLastName(lastName);
        setEmail(email);
        this.address = address;
    }

    public void setEmail(String email) {
        if (MemberService.isEmailValid(email) && MemberService.isEmailUnique(email))
            this.email = email;

    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.isEmpty() || lastName.isBlank() )
            throw new IllegalArgumentException("Last name must be filled in.");
        this.lastName = lastName;
    }


    public String getINSS() {
        return INSS;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }
}
