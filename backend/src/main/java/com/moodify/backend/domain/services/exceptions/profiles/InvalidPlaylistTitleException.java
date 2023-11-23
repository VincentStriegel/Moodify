package com.moodify.backend.domain.services.exceptions.profiles;

public class InvalidPlaylistTitleException extends Exception {

    private static final String MESSAGE = "Format of the playlist title is not correct";

    public InvalidPlaylistTitleException() {
        super(MESSAGE);
    }
}
