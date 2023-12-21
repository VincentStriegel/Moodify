package com.moodify.backend.services.database.exceptions.profiles;

public class DefaultPlaylistException extends Exception {

    private static final String MESSAGE = "Liked tracks playlist can not be deleted";

    public DefaultPlaylistException() {
        super(MESSAGE);
    }
}
