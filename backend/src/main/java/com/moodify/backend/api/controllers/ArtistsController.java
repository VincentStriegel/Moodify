package com.moodify.backend.api.controllers;

import com.moodify.backend.api.transferobjects.TrackTO;
import com.moodify.backend.services.database.DatabaseService;
import com.moodify.backend.services.database.postgres.PostgresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * This class represents the ArtistsController in the Moodify application.
 * It is responsible for handling all the artist-related requests.
 * The class uses the POSTGRES_SERVICE to interact with the Moodify database.
 * It provides a method to add a single track to the discography of a user.
 * The class uses the @RestController annotation to indicate that it is a controller and the @RequestMapping annotation to map the requests to /artists.
 * The class has one property: POSTGRES_SERVICE.
 * The POSTGRES_SERVICE property is used to interact with the Moodify database.
 * The class provides a constructor that initializes this property.
 */
@RestController
@RequestMapping("/artists")
public class ArtistsController {
    private final DatabaseService POSTGRES_SERVICE;

    @Autowired
    public ArtistsController(PostgresService USER_SERVICE) {
        this.POSTGRES_SERVICE = USER_SERVICE;
    }

    /**
     * This method is a POST request that adds a single track to the discography of a user in the Moodify application.
     * It receives a userId as a path variable and a TrackTO object as a request body.
     * The userId represents the ID of the user whose discography is to be updated.
     * The TrackTO object contains the data of the track to be added to the discography.
     * The method uses the POSTGRES_SERVICE to add the track to the discography of the user.
     * If the track cannot be added, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param userId The ID of the user whose discography is to be updated.
     * @param track The TrackTO object that contains the data of the track to be added to the discography.
     */
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
