package com.moodify.backend.services.database.exceptions.profiles;

public class PlaylistNotFoundException extends Exception {

    private static final String MESSAGE = "Playlist not found";

    public PlaylistNotFoundException() {
        super(MESSAGE);
    }
}
