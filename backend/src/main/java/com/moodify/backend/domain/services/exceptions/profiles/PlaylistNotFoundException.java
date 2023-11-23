package com.moodify.backend.domain.services.exceptions.profiles;

public class PlaylistNotFoundException extends Exception {

    private static final String MESSAGE = "Playlist not found";

    public PlaylistNotFoundException() {
        super(MESSAGE);
    }
}
