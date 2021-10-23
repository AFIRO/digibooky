package com.getdonuts.digibooky.domain;

import java.util.UUID;

public class User {

    private String INSS;
    private String firstName;
    private String lastName;
    private String id;
    private String email;
    private Address address;
    private boolean isAdmin;
    private boolean isLibrarian;
    private boolean isMember;


    public User(String INSS, String firstName, String lastName, String email, Address address) {
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isLibrarian() {
        return isLibrarian;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setLibrarian(boolean librarian) {
        isLibrarian = librarian;
    }

    public void setMember(boolean member) {
        isMember = member;
    }
}
