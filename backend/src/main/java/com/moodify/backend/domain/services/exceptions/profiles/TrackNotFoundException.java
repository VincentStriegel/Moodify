package com.moodify.backend.domain.services.exceptions.profiles;

public class TrackNotFoundException extends Exception {

    private static final String MESSAGE = "Track not found";

    public TrackNotFoundException() {
        super(MESSAGE);
    }
}
