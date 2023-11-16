package com.moodify.backend.api.controllers;

import com.moodify.backend.domain.services.database.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping({"/submit"})
    @ResponseStatus(HttpStatus.OK)
    public void registerUser(@RequestBody User user) {

    }
}
