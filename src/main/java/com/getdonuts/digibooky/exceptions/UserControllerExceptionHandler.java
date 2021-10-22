package com.getdonuts.digibooky.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class UserControllerExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    protected void illegalArgumentException(IllegalArgumentException exception, HttpServletResponse response) throws Exception{
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(AuthorisationException.class)
    protected void authorisationException(AuthorisationException exception, HttpServletResponse response) throws Exception{
        response.sendError(HttpStatus.FORBIDDEN.value(), exception.getMessage());
    }
}
