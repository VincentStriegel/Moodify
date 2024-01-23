package com.moodify.backend.services.database.security;


import com.moodify.backend.services.database.exceptions.registration.InvalidUsernameException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to validate a username in the Moodify application.
 * It provides a static method to validate a username.
 * The class uses a regular expression pattern to check if the username meets certain conditions.
 * The conditions include the presence of alphanumeric characters (a-zA-Z0-9), the use of certain special characters (dot, underscore, hyphen),
 * the restriction of special characters at the start or end of the username, the restriction of consecutive special characters,
 * and the restriction of the username length (between 5 and 20 characters).
 * If the username does not meet these conditions, the method throws an InvalidUsernameException.
 */
public class UsernameValidator {

    private static final String REGEX_PATTERN = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";

    /**
     * This method is used to validate a username.
     * It uses a regular expression pattern to check if the username meets the following conditions:
     * (1) The username consists of alphanumeric characters (a-zA-Z0-9), lowercase, or uppercase.
     * (2) The username allows the use of the dot (.), underscore (_), and hyphen (-).
     * (3) The dot (.), underscore (_), or hyphen (-) must not be the first or last character.
     * (4) The dot (.), underscore (_), or hyphen (-) does not appear consecutively, e.g., java..regex
     * (5) The number of characters must be between 5 and 20 characters.
     * If the username does not meet these conditions, it throws an InvalidUsernameException.
     *
     * @param username The username to be validated.
     * @throws InvalidUsernameException If the username does not meet the specified conditions.
     */
    public static void validateUsername(String username) throws InvalidUsernameException {
        Pattern pattern = Pattern.compile(REGEX_PATTERN);
        Matcher matcher = pattern.matcher(username);

        boolean usernameInvalid = !matcher.matches();
        if (usernameInvalid) {
            throw new InvalidUsernameException();
        }
    }
}
