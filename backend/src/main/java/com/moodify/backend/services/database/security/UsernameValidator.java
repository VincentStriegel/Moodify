package com.moodify.backend.services.database.security;


import com.moodify.backend.services.database.exceptions.registration.InvalidUsernameException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator {

   /* (1) Username consists of alphanumeric characters (a-zA-Z0-9), lowercase, or uppercase.
      (2) Username allowed of the dot (.), underscore (_), and hyphen (-).
      (3) The dot (.), underscore (_), or hyphen (-) must not be the first or last character.
      (4) The dot (.), underscore (_), or hyphen (-) does not appear consecutively, e.g., java..regex
      (5) The number of characters must be between 5 and 20 characters.*/

    private static final String REGEX_PATTERN = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";

    public static void validateUsername(String username) throws InvalidUsernameException {
        Pattern pattern = Pattern.compile(REGEX_PATTERN);
        Matcher matcher = pattern.matcher(username);

        boolean usernameInvalid = !matcher.matches();
        if (usernameInvalid) {
            throw new InvalidUsernameException();
        }
    }
}
