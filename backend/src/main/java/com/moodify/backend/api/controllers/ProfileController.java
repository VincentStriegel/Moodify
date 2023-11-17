package com.moodify.backend.api.controllers;

import com.moodify.backend.api.transferobjects.UserTO;
import com.moodify.backend.domain.services.database.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    private final UserService USER_SERVICE;

    @Autowired
    public ProfileController(UserService USER_SERVICE) {
        this.USER_SERVICE = USER_SERVICE;
    }

    @GetMapping({"findById/{profileId}"})
    @ResponseStatus(HttpStatus.OK)
    public UserTO getUser(@PathVariable("profileId") long profileId) {
        return USER_SERVICE.getUser(profileId);
    }

}
