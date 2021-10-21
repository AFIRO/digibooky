package com.getdonuts.digibooky.domain;

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
        this.INSS = INSS;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
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
