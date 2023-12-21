package com.moodify.backend.services.database.exceptions.profiles;

public class TrackNotFoundException extends Exception {

    private static final String MESSAGE = "Track not found";

    public TrackNotFoundException() {
        super(MESSAGE);
    }
}
