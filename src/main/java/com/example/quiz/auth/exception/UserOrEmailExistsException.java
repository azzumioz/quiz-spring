package com.example.quiz.auth.exception;


import org.springframework.security.core.AuthenticationException;

public class UserOrEmailExistsException extends AuthenticationException {

    public UserOrEmailExistsException(String msg) {
        super(msg);
    }

    public UserOrEmailExistsException(String explanation, Throwable t) {
        super(explanation, t);
    }
}
