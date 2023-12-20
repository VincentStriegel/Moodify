package com.moodify.backend.api.controllers;

import com.moodify.backend.api.transferobjects.*;
import com.moodify.backend.domain.services.database.*;
import com.moodify.backend.domain.services.database.databaseobjects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final DatabaseService POSTGRES_SERVICE;
    private final TOAssembler TO_OBJECT_ASSEMBLER;

    @Autowired
    public UsersController(PostgresService USER_SERVICE, TOAssembler TO_OBJECT_ASSEMBLER) {
        this.POSTGRES_SERVICE = USER_SERVICE;
        this.TO_OBJECT_ASSEMBLER = TO_OBJECT_ASSEMBLER;
    }

    @GetMapping({"findUserById/{userId}"})
    @ResponseStatus(HttpStatus.OK)
    public UserTO findUserById(@PathVariable("userId") long userId) {

        try {
            return this.TO_OBJECT_ASSEMBLER.generateUserTOFrom(this.POSTGRES_SERVICE.findUserById(userId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping({"createCustomPlaylist"})
    @ResponseStatus(HttpStatus.CREATED)
    public Long createCustomPlaylist(@RequestParam long userId, @RequestParam String title) {
        try {
            return this.POSTGRES_SERVICE.createCustomPlaylist(userId, title);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping({"deleteCustomPlaylist"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomPlaylist(@RequestParam long userId,
                                     @RequestParam long playlistId) {
        try {
            this.POSTGRES_SERVICE.deleteCustomPlaylist(userId, playlistId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping({"addToCustomPlaylist"})
    @ResponseStatus(HttpStatus.OK)
    public void addToCustomPlaylist(@RequestBody TrackTO trackTO,
                                    @RequestParam long userId,
                                    @RequestParam long playlistId) {

        try {
            this.POSTGRES_SERVICE.addToCustomPlaylist(trackTO, userId, playlistId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @DeleteMapping({"deleteFromCustomPlaylist"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteFromCustomPlaylist(@RequestParam long userId,
                                         @RequestParam long playlistId,
                                         @RequestParam long trackId) {

        try {
            this.POSTGRES_SERVICE.deleteFromCustomPlaylist(userId, playlistId, trackId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @PostMapping({"addToLikedTracks"})
    @ResponseStatus(HttpStatus.OK)
    public void addToLikedTracks(@RequestBody TrackTO trackTO, @RequestParam long userId) {

        try {
            this.POSTGRES_SERVICE.addToLikedTracks(trackTO, userId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @DeleteMapping({"deleteFromLikedTracks"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteFromLikedTracks(@RequestParam long userId, @RequestParam long trackId) {

        try {
            this.POSTGRES_SERVICE.deleteFromLikedTracks(userId, trackId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @PostMapping({"addToLikedArtists"})
    @ResponseStatus(HttpStatus.OK)
    public void addToLikedArtists(@RequestBody ArtistTO artistTO, @RequestParam long userId) {

        try {
            this.POSTGRES_SERVICE.addToLikedArtists(artistTO, userId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @DeleteMapping({"deleteFromLikedArtists"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteFromLikedArtists(@RequestParam long userId, @RequestParam long artistId) {

        try {
            this.POSTGRES_SERVICE.deleteFromLikedArtists(artistId, userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @PostMapping({"addToLikedAlbums"})
    @ResponseStatus(HttpStatus.OK)
    public void addToLikedAlbums(@RequestBody AlbumTO albumTO, @RequestParam long userId) {

        try {
            this.POSTGRES_SERVICE.addToLikedAlbums(albumTO, userId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @DeleteMapping({"deleteFromLikedAlbums"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteFromLikedAlbums(@RequestParam long userId, @RequestParam long albumId) {

        try {
            this.POSTGRES_SERVICE.deleteFromLikedAlbums(albumId, userId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

}
