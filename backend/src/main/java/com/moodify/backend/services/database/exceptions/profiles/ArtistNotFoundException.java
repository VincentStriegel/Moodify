package com.moodify.backend.services.database.exceptions.profiles;

public class ArtistNotFoundException extends Exception {

    private static final String MESSAGE = "Artist not found";

    public ArtistNotFoundException() {
        super(MESSAGE);
    }
}
