package com.getdonuts.digibooky.api.dto;

public class CreateMemberDTO {
    private final String INSS;
    private final String firstName;
    private final String lastname;
    private final String email;
    private final String street;
    private final String houseNumber;
    private final String city;
    private final String postcode;

    public CreateMemberDTO(String INSS, String firstName, String lastname, String email, String street, String houseNumber, String city, String postcode) {
        this.INSS = INSS;
        this.firstName = firstName;
        this.lastname = lastname;
        this.email = email;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postcode = postcode;
    }

    public String getINSS() {
        return INSS;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getCity() {
        return city;
    }

    public String getPostcode() {
        return postcode;
    }
}
