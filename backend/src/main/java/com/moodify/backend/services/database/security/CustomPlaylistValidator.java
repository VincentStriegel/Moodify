package com.moodify.backend.services.database.security;

import com.moodify.backend.services.database.exceptions.profiles.InvalidPlaylistTitleException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to validate the title of a custom playlist in the Moodify application.
 * It provides a static method to validate the title of a custom playlist.
 * The class uses a regular expression pattern to check if the title meets certain conditions.
 * The conditions include the use of alphanumeric characters, the use of certain special characters (dot, underscore, hyphen),
 * the restriction of special characters at the start or end of the title, the restriction of consecutive special characters,
 * and the restriction of the title length.
 * If the title does not meet these conditions, the method throws an InvalidPlaylistTitleException.
 */
public class CustomPlaylistValidator {

    private static final String REGEX_PATTERN = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";

    /**
     * This method is used to validate the title of a custom playlist.
     * It uses a regular expression pattern to check if the title meets the following conditions:
     * (1) The title consists of alphanumeric characters (a-zA-Z0-9), lowercase, or uppercase.
     * (2) The title allows the use of the dot (.), underscore (_), and hyphen (-).
     * (3) The dot (.), underscore (_), or hyphen (-) must not be the first or last character.
     * (4) The dot (.), underscore (_), or hyphen (-) does not appear consecutively, e.g., java..regex
     * (5) The number of characters must be between 5 and 20 characters.
     * If the title does not meet these conditions, it throws an InvalidPlaylistTitleException.
     *
     * @param customPlaylistTitle The title of the custom playlist to be validated.
     * @throws InvalidPlaylistTitleException If the title does not meet the specified conditions.
     */
    public static void validateCustomPlaylist(String customPlaylistTitle) throws InvalidPlaylistTitleException {
        Pattern pattern = Pattern.compile(REGEX_PATTERN);
        Matcher matcher = pattern.matcher(customPlaylistTitle);

        boolean usernameInvalid = !matcher.matches();
        if (usernameInvalid) {
            throw new InvalidPlaylistTitleException();
        }
    }
}
