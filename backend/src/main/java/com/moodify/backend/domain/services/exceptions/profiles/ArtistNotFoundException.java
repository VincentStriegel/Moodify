package com.moodify.backend.domain.services.exceptions.profiles;

public class ArtistNotFoundException extends Exception {

    private static final String MESSAGE = "Artist not found";

    public ArtistNotFoundException() {
        super(MESSAGE);
    }
}
