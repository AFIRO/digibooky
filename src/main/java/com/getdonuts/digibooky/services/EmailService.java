package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.exceptions.InvalidEmailException;

public class EmailService {


    public static void isEmailUnique(String email) {

    }

    public static boolean isValid(String email) {
        if (email == null || email.isEmpty() || email.isBlank())
            return false;

        String regex = ".@.\\..";

        if (email.matches(regex))
            return true;

        else
            throw new InvalidEmailException("invalid email");
    }
}
