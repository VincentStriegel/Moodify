package com.moodify.backend.domain.services.exceptions.login;

public class UserCredentialsException extends Exception {

    private static final String MESSAGE = "Email or Username do not exist";

    public UserCredentialsException() {
        super(MESSAGE);
    }

}
