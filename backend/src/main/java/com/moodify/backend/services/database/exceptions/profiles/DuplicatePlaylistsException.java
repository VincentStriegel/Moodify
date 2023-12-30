package com.moodify.backend.services.database.exceptions.profiles;

public class DuplicatePlaylistsException extends Exception {

    private static final String MESSAGE = "Duplicate playlists";

    public DuplicatePlaylistsException() {
        super(MESSAGE);
    }
}
