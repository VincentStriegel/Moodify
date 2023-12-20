package com.moodify.backend.services.database.exceptions.registration;

public class RegisteredEmailException extends Exception {
    private static final String MESSAGE = "Provided email has already been used";

    public RegisteredEmailException() {
        super(MESSAGE);
    }

}
