package com.moodify.backend.domain.services.exceptions;

public class InvalidUsernameException extends Exception {

    private static final String MESSAGE = "Format of the username is not correct";

    public InvalidUsernameException() {
        super(MESSAGE);
    }

}
