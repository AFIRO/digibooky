package com.getdonuts.digibooky.exceptions;

public class AuthorisationException extends RuntimeException {
    private static final String ERROR_MESSAGE = "You do not have the correct authorization to perform this action.";

    public AuthorisationException(String message) {
        super(message);
    }

    public AuthorisationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorisationException(Throwable cause) {
        super(cause);
    }

    public AuthorisationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AuthorisationException() {
        super(ERROR_MESSAGE);

    }
}
