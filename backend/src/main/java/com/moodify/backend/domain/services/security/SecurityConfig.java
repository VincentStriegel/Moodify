/*
package com.moodify.backend.domain.services.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrs->csrs.disable())
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/music/**").permitAll()
                        .requestMatchers("/register/submit").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/profile/**").authenticated()
                );
        return http.build();
    }
}
*/
