package com.moodify.backend.services.database.security;

import com.moodify.backend.services.database.exceptions.registration.InvalidEmailException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to validate an email address in the Moodify application.
 * It provides a static method to validate an email address.
 * The class uses a regular expression pattern to check if the email address meets certain conditions.
 * The conditions include the presence of the @ symbol, the presence of one or more characters before and after the @ symbol,
 * and the absence of whitespace characters after the @ symbol.
 * If the email address does not meet these conditions, the method throws an InvalidEmailException.
 */
public class EmailValidator {

    private static final String REGEX_PATTERN = "^(.+)@(\\S+)$";

    /**
     * This method is used to validate an email address.
     * It uses a regular expression pattern to check if the email address contains the @ symbol.
     * The regular expression pattern is defined as follows:
     * (1) The email address must start with one or more characters (.+).
     * (2) Then, it must contain the @ symbol (@).
     * (3) Finally, it must end with one or more non-whitespace characters (\\S+).
     * If the email address does not meet these conditions, it throws an InvalidEmailException.
     *
     * @param email The email address to be validated.
     * @throws InvalidEmailException If the email address does not meet the specified conditions.
     */
    public static void validateEmail(String email) throws InvalidEmailException {
        Pattern pattern = Pattern.compile(REGEX_PATTERN);
        Matcher matcher = pattern.matcher(email);

        boolean emailInvalid = !matcher.matches();
        if (emailInvalid) {
            throw new InvalidEmailException();
        }
    }
}
