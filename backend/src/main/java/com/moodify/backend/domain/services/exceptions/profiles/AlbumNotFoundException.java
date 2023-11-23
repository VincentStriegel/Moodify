package com.moodify.backend.domain.services.exceptions.profiles;

public class AlbumNotFoundException extends Exception {

    private static final String MESSAGE = "Album not found";

    public AlbumNotFoundException() {
        super(MESSAGE);
    }
}
