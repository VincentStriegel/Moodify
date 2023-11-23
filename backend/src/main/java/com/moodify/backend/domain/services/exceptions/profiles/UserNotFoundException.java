package com.moodify.backend.domain.services.exceptions.profiles;

public class UserNotFoundException extends Exception {

    private static final String MESSAGE = "User not found";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}

