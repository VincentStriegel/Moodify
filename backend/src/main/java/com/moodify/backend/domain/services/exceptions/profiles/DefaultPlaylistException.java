package com.moodify.backend.domain.services.exceptions.profiles;

public class DefaultPlaylistException extends Exception {

    private static final String MESSAGE = "Liked tracks playlist can not be deleted";

    public DefaultPlaylistException() {
        super(MESSAGE);
    }
}
