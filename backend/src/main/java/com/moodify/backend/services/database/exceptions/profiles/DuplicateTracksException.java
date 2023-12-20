package com.moodify.backend.services.database.exceptions.profiles;

public class DuplicateTracksException extends Exception {

    private static final String MESSAGE = "Duplicate tracks";

    public DuplicateTracksException() {
        super(MESSAGE);
    }
}
