package com.moodify.backend.services.database.exceptions.registration;

public class InvalidEmailException extends Exception {

    private static final String MESSAGE = "Format of the email is not correct";

    public InvalidEmailException() {
        super(MESSAGE);
    }
}
