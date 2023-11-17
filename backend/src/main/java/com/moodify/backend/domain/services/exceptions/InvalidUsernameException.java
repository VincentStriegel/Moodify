package com.moodify.backend.domain.services.exceptions;

public class InvalidUsernameException extends Exception{
    public InvalidUsernameException(String message) {
        super(message);
    }
}
