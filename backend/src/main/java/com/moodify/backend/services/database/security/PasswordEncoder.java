package com.moodify.backend.services.database.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * This class is used for password encoding in the Moodify application.
 * It uses the BCryptPasswordEncoder from the Spring Security framework to hash and compare passwords.
 * The class is annotated with @Component to indicate that it is a Spring Bean and can be autowired into other classes.
 * The class has one property: encoder, which is an instance of BCryptPasswordEncoder.
 * The class provides a constructor that initializes the encoder property.
 * The class provides two methods: encode and compare.
 * The encode method is used to hash a password.
 * The compare method is used to compare a raw password with a hashed password.
 */
@Component
public class PasswordEncoder {
    private final BCryptPasswordEncoder encoder;

    public PasswordEncoder() {
        this.encoder = new BCryptPasswordEncoder();
    }

    /**
     * This method is used to encode a password.
     * It uses the BCryptPasswordEncoder to hash the password.
     *
     * @param password The password to be encoded.
     * @return String The hashed password.
     */
    public String encode(String password) {
        return this.encoder.encode(password);
    }

    /**
     * This method is used to compare a raw password with an encoded password.
     * It uses the BCryptPasswordEncoder to check if the raw password matches the encoded password.
     *
     * @param password The raw password to be compared.
     * @param encoded The encoded password to be compared.
     * @return boolean Returns true if the raw password matches the encoded password, false otherwise.
     */
    public boolean compare(String password, String encoded) {
        return this.encoder.matches(password, encoded);
    }

}
