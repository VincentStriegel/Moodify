package com.moodify.backend.services.database.exceptions.profiles;

public class UserAlreadyPromotedException extends Exception {

    private static final String MESSAGE = "User has already been promoted for artist";

    public UserAlreadyPromotedException() {
        super(MESSAGE);
    }
}
