package com.moodify.backend.domain.services.exceptions;

public class UserCredentialsException extends Exception {

    private static final String MESSAGE = "Email and Username do not exist";

    public UserCredentialsException() {
        super(MESSAGE);
    }

}
