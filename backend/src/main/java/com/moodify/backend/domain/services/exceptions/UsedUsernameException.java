package com.moodify.backend.domain.services.exceptions;

public class UsedUsernameException extends Exception {
    public UsedUsernameException(String message) {
        super(message);
    }
}
