package com.moodify.backend.domain.services.exceptions.profiles;

public class DuplicateAlbumsException extends Exception {

    private static final String MESSAGE = "Duplicate albums";

    public DuplicateAlbumsException() {
        super(MESSAGE);
    }

}
