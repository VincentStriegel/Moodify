package com.moodify.backend.api.controllers;

import com.moodify.backend.domain.services.database.DatabaseService;
import com.moodify.backend.domain.services.database.LoginUser;
import com.moodify.backend.domain.services.database.User;
import com.moodify.backend.domain.services.exceptions.UserCredentialsException;
import com.moodify.backend.domain.services.exceptions.WrongPasswordException;
import com.moodify.backend.domain.services.security.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final DatabaseService DATABASE_SERVICE;
    private final PasswordEncoder ENCODER;
    @Autowired
    public LoginController(DatabaseService USER_SERVICE, PasswordEncoder ENCODER) {
        this.DATABASE_SERVICE = USER_SERVICE;
        this.ENCODER = ENCODER;
    }

    @GetMapping({"/submit"})
    @ResponseStatus(HttpStatus.OK)
    public long loginUser(@RequestBody LoginUser loginUser) {
        try {
            String username = loginUser.getCredential();
            String email = loginUser.getCredential();
            String password = loginUser.getPassword();


            if (emailExists(email)) {
                User user = this.DATABASE_SERVICE.getUserByEmail(email);

                boolean passNotEqual = !this.ENCODER.compare(loginUser.getPassword(),user.getPassword());
                if (passNotEqual) {
                    throw new WrongPasswordException("Invalid password");
                }

                return user.getId();
            }

            if (usernameExists(username)) {
                User user = this.DATABASE_SERVICE.getUserByUsername(username);

                boolean passNotEqual = !this.ENCODER.compare(loginUser.getPassword(),user.getPassword());
                if (passNotEqual) {
                    throw new WrongPasswordException("Wrong password");
                }

                return user.getId();
            }

            throw new UserCredentialsException("Email or Username do not exist");

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    private boolean emailExists(String email) {
        return this.DATABASE_SERVICE.existsUserByEmail(email);
    }

    private boolean usernameExists(String username) {
        return this.DATABASE_SERVICE.existsUserByUsername(username);
    }
}
