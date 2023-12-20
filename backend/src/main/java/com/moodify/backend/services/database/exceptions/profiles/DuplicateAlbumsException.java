package com.moodify.backend.services.database.exceptions.profiles;

public class DuplicateAlbumsException extends Exception {

    private static final String MESSAGE = "Duplicate albums";

    public DuplicateAlbumsException() {
        super(MESSAGE);
    }

}
