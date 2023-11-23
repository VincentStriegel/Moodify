package com.moodify.backend.domain.services.exceptions.registration;

public class RegisteredUsernameException extends Exception {

    private static final String MESSAGE = "Provided username has already been used";

    public RegisteredUsernameException() {
        super(MESSAGE);
    }

}
