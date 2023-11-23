package com.moodify.backend.domain.services.exceptions.registration;

public class RegisteredEmailException extends Exception {
    private static final String MESSAGE = "Provided email has already been used";

    public RegisteredEmailException() {
        super(MESSAGE);
    }

}