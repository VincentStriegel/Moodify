package com.moodify.backend.services.database.exceptions.profiles;

public class UserNotArtistException extends Exception {

    private static final String MESSAGE = "User is not an artist";

    public UserNotArtistException() {
        super(MESSAGE);
    }
}
