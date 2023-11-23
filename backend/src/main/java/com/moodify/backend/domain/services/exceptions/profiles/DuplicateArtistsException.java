package com.moodify.backend.domain.services.exceptions.profiles;

public class DuplicateArtistsException extends Exception {

    private static final String MESSAGE = "Duplicate artists";

    public DuplicateArtistsException() {
        super(MESSAGE);
    }
}
