package com.moodify.backend.domain.services.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
    private BCryptPasswordEncoder encoder;

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
