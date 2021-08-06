package com.example.quiz.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class UserActivatedException  extends AuthenticationException {
    public UserActivatedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserActivatedException(String msg) {
        super(msg);
    }
}
