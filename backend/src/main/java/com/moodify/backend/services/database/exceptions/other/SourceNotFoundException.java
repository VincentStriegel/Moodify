package com.moodify.backend.services.database.exceptions.other;

public class SourceNotFoundException extends Exception {

    private static final String MESSAGE = "Source not found";

    public SourceNotFoundException() {
        super(MESSAGE);
    }
}
