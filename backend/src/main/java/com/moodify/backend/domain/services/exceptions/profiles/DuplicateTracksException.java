package com.moodify.backend.domain.services.exceptions.profiles;

public class DuplicateTracksException extends Exception {

    private static final String MESSAGE = "Duplicate tracks";

    public DuplicateTracksException() {
        super(MESSAGE);
    }
}
