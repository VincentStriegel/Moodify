package com.moodify.backend.api.controllers;


import com.moodify.backend.domain.services.database.User;
import com.moodify.backend.domain.services.database.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserRepository repo;

    @PostMapping({"submit"})
    @ResponseStatus(HttpStatus.OK)
    public void registerUser(@RequestBody User user) {

    }



}
