package com.moodify.backend.services.database.exceptions.profiles;

public class UserNotFoundException extends Exception {

    private static final String MESSAGE = "User not found";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}

