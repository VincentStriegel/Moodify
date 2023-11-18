package com.moodify.backend.domain.services.exceptions;

public class InvalidPasswordException extends Exception {
    private static final String MESSAGE = "Format of the password is not correct";

    public InvalidPasswordException() {
        super(MESSAGE);
    }

}
