package com.moodify.backend.api.controllers;

import com.moodify.backend.api.transferobjects.UserTO;
import com.moodify.backend.domain.services.database.DatabaseService;
import com.moodify.backend.domain.services.database.LoginUser;
import com.moodify.backend.domain.services.database.PostgresService;
import com.moodify.backend.domain.services.database.databaseobjects.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/sign")
public class SignController {

    private final DatabaseService POSTGRES_SERVICE;

    @Autowired
    public SignController(PostgresService POSTGRES_SERVICE) {
        this.POSTGRES_SERVICE = POSTGRES_SERVICE;
    }

    @PostMapping({"/up"})
    @ResponseStatus(HttpStatus.OK)
    public void registerUser(@RequestBody UserDO userDO) {

        try {
            UserDO user = this.POSTGRES_SERVICE.createUser(userDO);
            this.POSTGRES_SERVICE.saveUser(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @PostMapping({"/in"})
    @ResponseStatus(HttpStatus.OK)
    public UserTO loginUser(@RequestBody LoginUser loginUser) {
        try {
            long id = this.POSTGRES_SERVICE.loginUser(loginUser);
            UserTO userTO = new UserTO();
            userTO.setId(id);
            return userTO;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

}
