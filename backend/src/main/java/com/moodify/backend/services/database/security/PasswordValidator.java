package com.moodify.backend.services.database.security;

import com.moodify.backend.services.database.exceptions.registration.InvalidPasswordException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    private static final String REGEX_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-_]).{8,}$";
    /*
       (1) Has minimum 8 characters in length. Adjust it by modifying {8,}
       (2) At least one uppercase English letter. You can remove this condition by removing (?=.*?[A-Z])
       (3) At least one lowercase English letter.  You can remove this condition by removing (?=.*?[a-z])
       (5) At least one special character,  You can remove this condition by removing (?=.*?[#?!@$%^&*-])
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
