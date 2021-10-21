package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.exceptions.InvalidEmailException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemberService {


    public static boolean isEmailUnique(String email) {
        //implement van member repo is done.
        throw new IllegalArgumentException("This e-mail is already used.");

    }

    public static boolean isEmailValid(String email) {
        if (email == null || email.isEmpty() || email.isBlank())
            return false;

        Pattern regex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(email);

        if (matcher.find())
            return true;
        else
            throw new IllegalArgumentException("This e-mail is not valid.");
    }

    public static boolean isINSSunique(String inss) {
        //implement with member repo

        throw new IllegalArgumentException("INSS is already used.");
    }
}
