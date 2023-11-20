package com.moodify.backend.domain.services.exceptions.login;

public class WrongPasswordException extends Exception {
    private static final String MESSAGE = "Invalid password";

    public WrongPasswordException() {
        super(MESSAGE);
    }

}
