package com.moodify.backend.api.controllers;

import com.moodify.backend.api.transferobjects.UserTO;
import com.moodify.backend.domain.services.database.DatabaseService;
import com.moodify.backend.domain.services.database.PostgresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    private final DatabaseService POSTGRES_SERVICE;

    @Autowired
    public ProfileController(PostgresService USER_SERVICE) {
        this.POSTGRES_SERVICE = USER_SERVICE;
    }

    @GetMapping({"findById/{profileId}"})
    @ResponseStatus(HttpStatus.OK)
    public UserTO getUser(@PathVariable("profileId") long profileId) {
        return POSTGRES_SERVICE.getUser(profileId);
    }

}
