package com.moodify.backend.api.controllers;

import com.moodify.backend.api.transferobjects.TrackTO;
import com.moodify.backend.services.database.DatabaseService;
import com.moodify.backend.services.database.postgres.PostgresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/artists")
public class ArtistsController {
    private final DatabaseService POSTGRES_SERVICE;

    @Autowired
    public ArtistsController(PostgresService USER_SERVICE) {
        this.POSTGRES_SERVICE = USER_SERVICE;
    }

    @PostMapping({"addSingleToDiscography/{userId}"})
    @ResponseStatus(HttpStatus.OK)
    public void addSingleToDiscography(@PathVariable long userId, @RequestBody TrackTO track) {

        try {
            this.POSTGRES_SERVICE.addSingleToDiscography(userId, track);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }
}
