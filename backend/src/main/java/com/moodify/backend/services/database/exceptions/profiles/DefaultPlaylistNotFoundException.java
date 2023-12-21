package com.moodify.backend.services.database.exceptions.profiles;

public class DefaultPlaylistNotFoundException extends Exception {

    private static final String MESSAGE = "Liked tracks playlist can not be found";

    public DefaultPlaylistNotFoundException() {
        super(MESSAGE);
    }
}
