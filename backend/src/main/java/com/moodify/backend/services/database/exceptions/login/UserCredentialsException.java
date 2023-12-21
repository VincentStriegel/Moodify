package com.moodify.backend.services.database.exceptions.login;

public class UserCredentialsException extends Exception {

    private static final String MESSAGE = "Email or Username do not exist";

    public UserCredentialsException() {
        super(MESSAGE);
    }

}
