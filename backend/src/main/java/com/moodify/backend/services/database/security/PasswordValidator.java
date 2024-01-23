package com.moodify.backend.services.database.security;

import com.moodify.backend.services.database.exceptions.registration.InvalidPasswordException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to validate a password in the Moodify application.
 * It provides a static method to validate a password.
 * The class uses a regular expression pattern to check if the password meets certain conditions.
 * The conditions include the presence of at least one uppercase letter (A-Z), at least one lowercase letter (a-z),
 * at least one special character (#?!@$%^&*-_), and a minimum length of 8 characters.
 * If the password does not meet these conditions, the method throws an InvalidPasswordException.
 */
public class PasswordValidator {
    private static final String REGEX_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-_]).{8,}$";

    /**
     * This method is used to validate a password.
     * It uses a regular expression pattern to check if the password meets the following conditions:
     * (1) The password must contain at least one uppercase letter (A-Z).
     * (2) The password must contain at least one lowercase letter (a-z).
     * (3) The password must contain at least one special character (#?!@$%^&*-_)
     * (4) The password must be at least 8 characters long.
     * If the password does not meet these conditions, it throws an InvalidPasswordException.
     *
     * @param password The password to be validated.
     * @throws InvalidPasswordException If the password does not meet the specified conditions.
     */
    public static void validatePassword(String password) throws InvalidPasswordException {
        Pattern pattern = Pattern.compile(REGEX_PATTERN);
        Matcher matcher = pattern.matcher(password);

        boolean passwordInvalid = !matcher.matches();
        if (passwordInvalid) {
            throw new InvalidPasswordException();
        }
    }
}
