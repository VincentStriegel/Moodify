package com.moodify.backend.services.database.exceptions.registration;

public class RegisteredUsernameException extends Exception {

    private static final String MESSAGE = "Provided username has already been used";

    public RegisteredUsernameException() {
        super(MESSAGE);
    }

}
