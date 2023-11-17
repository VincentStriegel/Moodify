package com.moodify.backend.api.controllers;

import com.moodify.backend.domain.services.database.User;
import com.moodify.backend.domain.services.database.DatabaseService;
import com.moodify.backend.domain.services.exceptions.*;
import com.moodify.backend.domain.services.security.EmailValidator;
import com.moodify.backend.domain.services.security.PasswordValidator;
import com.moodify.backend.domain.services.security.UsernameValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final DatabaseService DATABASE_SERVICE;

    @Autowired
    public RegisterController(DatabaseService DATABASE_SERVICE) {
        this.DATABASE_SERVICE = DATABASE_SERVICE;
    }

    @PostMapping({"submit"})
    @ResponseStatus(HttpStatus.OK)
    public void registerUser(@RequestBody User user) {

        try {
            EmailValidator.validateEmail(user.getEmail());
            UsernameValidator.validateUsername(user.getUsername());
            PasswordValidator.validatePassword(user.getPassword());


            if (this.DATABASE_SERVICE.existsUserByEmail(user.getEmail())){
                throw new UsedEmailException("Provided email has already been used");
            }

            if(this.DATABASE_SERVICE.existsUserByUsername(user.getUsername())){
                throw new UsedUsernameException("Provided username has already been used");
            }

            //TODO encrypting the password
            this.DATABASE_SERVICE.save(user);

        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }



    }
}
