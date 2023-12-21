package com.moodify.backend.services.database.exceptions.registration;

public class InvalidPasswordException extends Exception {
    private static final String MESSAGE = "Format of the password is not correct";

    public InvalidPasswordException() {
        super(MESSAGE);
    }

}
