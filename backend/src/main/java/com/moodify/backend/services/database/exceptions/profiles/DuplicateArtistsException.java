package com.moodify.backend.services.database.exceptions.profiles;

public class DuplicateArtistsException extends Exception {

    private static final String MESSAGE = "Duplicate artists";

    public DuplicateArtistsException() {
        super(MESSAGE);
    }
}
