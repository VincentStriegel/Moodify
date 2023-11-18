package com.moodify.backend.api.controllers;

import com.moodify.backend.domain.services.database.DatabaseService;
import com.moodify.backend.domain.services.database.PostgresService;
import com.moodify.backend.domain.services.database.databaseobjects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/register")
public class RegisterController {
    private final DatabaseService POSTGRES_SERVICE;

    @Autowired
    public RegisterController(PostgresService POSTGRES_SERVICE) {
        this.POSTGRES_SERVICE = POSTGRES_SERVICE;
    }

    @PostMapping({"/submit"})
    @ResponseStatus(HttpStatus.OK)
    public void registerUser(@RequestBody User user) {

        try {
            this.POSTGRES_SERVICE.saveUser(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }
}
