package com.getdonuts.digibooky.domain;

public class Address {

    private final String street;
    private final String houseNumber;
    private final String postalCode;
    private String city;

    public Address(String street, String houseNumber, String postalCode, String city) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        setCity(city);
    }

    public void setCity(String city){
        if (city == null || city.isBlank() || city.isEmpty())
            throw new IllegalArgumentException();
        else
            this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }
}
