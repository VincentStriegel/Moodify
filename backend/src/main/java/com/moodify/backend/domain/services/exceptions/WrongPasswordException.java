package com.moodify.backend.domain.services.exceptions;

public class WrongPasswordException extends Exception {
    private static final String MESSAGE = "Invalid password";

    public WrongPasswordException() {
        super(MESSAGE);
    }

}
