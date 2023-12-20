package com.moodify.backend.services.database.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
    private final BCryptPasswordEncoder encoder;

    public PasswordEncoder() {
        this.encoder = new BCryptPasswordEncoder();
    }

    public String encode(String password) {
        return this.encoder.encode(password);
    }

    public boolean compare(String password, String encoded) {
        return this.encoder.matches(password, encoded);
    }

}
