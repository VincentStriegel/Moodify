package com.moodify.backend.api.controllers;

import com.moodify.backend.services.database.DatabaseService;
import com.moodify.backend.services.database.objects.UserDO;
import com.moodify.backend.services.database.postgres.PostgresService;
import com.moodify.backend.services.database.util.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * This class represents the sign controller in the Moodify application.
 * It is responsible for handling all the sign-related requests, such as registering a new user and logging in a user.
 * The class uses the POSTGRES_SERVICE to interact with the Moodify database.
 * It provides two methods: registerUser and loginUser.
 * The registerUser method is a POST request that registers a new user in the application.
 * The loginUser method is a POST request that logs in a user in the application.
 * The class uses the @RestController annotation to indicate that it is a controller and the @RequestMapping annotation to map the requests to /sign.
 * The class has one property: POSTGRES_SERVICE.
 * The POSTGRES_SERVICE property is used to interact with the Moodify database.
 * The class provides a constructor that initializes this property.
 */
@RestController
@RequestMapping("/sign")
public class SignController {

    private final DatabaseService POSTGRES_SERVICE;

    @Autowired
    public SignController(PostgresService POSTGRES_SERVICE) {
        this.POSTGRES_SERVICE = POSTGRES_SERVICE;
    }

    /**
     * This method is a POST request that registers a new user in the Moodify application.
     * It receives a UserDO object as a request body, which contains the data of the new user.
     * The method uses the POSTGRES_SERVICE to create and save the new user in the database.
     * If the user cannot be created or saved, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param user The UserDO object that contains the data of the new user.
     */
    @PostMapping({"/up"})
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDO user) {

        try {
            UserDO newUser = this.POSTGRES_SERVICE.createUser(user);
            this.POSTGRES_SERVICE.saveUser(newUser);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    /**
     * This method is a POST request that logs in a user in the Moodify application.
     * It receives a LoginUser object as a request body, which contains the login data of the user.
     * The method uses the POSTGRES_SERVICE to log in the user and returns the ID of the logged-in user.
     * If the user cannot be logged in, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param loginUser The LoginUser object that contains the login data of the user.
     * @return The ID of the logged-in user.
     */
    @PostMapping({"/in"})
    @ResponseStatus(HttpStatus.OK)
    public long loginUser(@RequestBody LoginUser loginUser) {
        try {
            return this.POSTGRES_SERVICE.loginUser(loginUser);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

}
