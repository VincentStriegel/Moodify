package com.moodify.backend.domain.services.exceptions;

public class UsedEmailException extends Exception {
    public UsedEmailException(String message) {
        super(message);
    }
}
