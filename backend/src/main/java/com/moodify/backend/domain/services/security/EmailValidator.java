package com.moodify.backend.domain.services.security;

import com.moodify.backend.domain.services.exceptions.InvalidEmailException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final String REGEX_PATTERN = "^(.+)@(\\S+)$"; //It only checks the presence of the @ symbol in the email address. If present, then the validation result returns true,

    public static void validateEmail(String email) throws InvalidEmailException {
        Pattern pattern = Pattern.compile(REGEX_PATTERN);
        Matcher matcher = pattern.matcher(email);

        boolean emailInvalid = !matcher.matches();
        if (emailInvalid) {
            throw new InvalidEmailException("Format of the email is not correct");
        }
    }
}
