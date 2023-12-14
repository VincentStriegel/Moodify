package com.moodify.backend.api.controllers;

import com.moodify.backend.api.transferobjects.*;
import com.moodify.backend.domain.services.database.databaseobjects.*;
import com.moodify.backend.domain.services.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final DatabaseService POSTGRES_SERVICE;

    @Autowired
    public UsersController(PostgresService USER_SERVICE) {
        this.POSTGRES_SERVICE = USER_SERVICE;
    }

    @GetMapping({"findUserById/{userId}"})
    @ResponseStatus(HttpStatus.OK)
    public UserTO findUserById(@PathVariable("userId") long userId) {

        try {
            return ObjectTransformer.generateUserTOFrom(this.POSTGRES_SERVICE.getUserById(userId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping({"findUserById/{userId}/addCustomPlaylist/{title}"})
    @ResponseStatus(HttpStatus.CREATED)
    public Long addCustomPlaylist(@PathVariable("userId") long userId, @PathVariable("title") String title) {
        try {
            return this.POSTGRES_SERVICE.addCustomPlaylist(userId, title);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping({"findUserById/{userId}/removeCustomPlaylist/{playlistId}"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalLibraryTO removeCustomPlaylist(@PathVariable("userId") long userId,
                                                  @PathVariable("playlistId") long playlistId) {
        try {
            PersonalLibraryDO personalLibraryDO = this.POSTGRES_SERVICE.removeCustomPlaylist(userId, playlistId);
            return ObjectTransformer.generatePersonalLibraryTOFrom(personalLibraryDO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping({"findUserById/{userId}/addToCustomPlaylist/{playlistId}"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalLibraryTO addToCustomPlaylist(@RequestBody TrackTO trackTO,
                                                 @PathVariable("userId") long userId,
                                                 @PathVariable("playlistId") long playlistId) {

        try {
            PersonalLibraryDO personalLibraryDO = this.POSTGRES_SERVICE.addToCustomPlaylist(trackTO, userId, playlistId);
            return ObjectTransformer.generatePersonalLibraryTOFrom(personalLibraryDO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @DeleteMapping({"findUserById/{userId}/removeFromCustomPlaylist/{playlistId}/track/{trackId}"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalLibraryTO removeFromCustomPlaylist(@PathVariable("userId") long userId,
                                                      @PathVariable("playlistId") long playlistId,
                                                      @PathVariable("trackId") long trackId) {

        try {
            PersonalLibraryDO personalLibraryDO = this.POSTGRES_SERVICE.removeFromCustomPlaylist(userId, playlistId, trackId);
            return ObjectTransformer.generatePersonalLibraryTOFrom(personalLibraryDO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @PostMapping({"findUserById/{userId}/addToLikedTracks"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalLibraryTO addToLikedTracks(@RequestBody TrackTO trackTO, @PathVariable("userId") long userId) {

        try {
            PersonalLibraryDO personalLibraryDO = this.POSTGRES_SERVICE.addToLikedTracks(trackTO, userId);
            return ObjectTransformer.generatePersonalLibraryTOFrom(personalLibraryDO);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @DeleteMapping({"findUserById/{userId}/removeFromLikedTracks/{trackId}"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalLibraryTO addToLikedTracks(@PathVariable("userId") long userId, @PathVariable("trackId") long trackId) {

        try {
            PersonalLibraryDO personalLibraryDO = this.POSTGRES_SERVICE.removeFromLikedTracks(userId, trackId);
            return ObjectTransformer.generatePersonalLibraryTOFrom(personalLibraryDO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @PostMapping({"findUserById/{userId}/addToLikedArtists"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalLibraryTO addToLikedArtists(@RequestBody ArtistTO artistTO, @PathVariable("userId") long userId) {

        try {
            PersonalLibraryDO personalLibraryDO = this.POSTGRES_SERVICE.addToLikedArtists(artistTO, userId);
            return ObjectTransformer.generatePersonalLibraryTOFrom(personalLibraryDO);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @DeleteMapping({"findUserById/{userId}/removeFromLikedArtists/{artistId}"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalLibraryTO removeFromLikedArtists(@PathVariable("userId") long userId, @PathVariable("artistId") long artistId) {

        try {
            PersonalLibraryDO personalLibraryDO = this.POSTGRES_SERVICE.removeFromLikedArtists(artistId, userId);
            return ObjectTransformer.generatePersonalLibraryTOFrom(personalLibraryDO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @PostMapping({"findUserById/{userId}/addToLikedAlbums"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalLibraryTO addToLikedAlbums(@RequestBody AlbumTO albumTO, @PathVariable("userId") long userId) {

        try {
            PersonalLibraryDO personalLibraryDO = this.POSTGRES_SERVICE.addToLikedAlbums(albumTO, userId);
            return ObjectTransformer.generatePersonalLibraryTOFrom(personalLibraryDO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @DeleteMapping({"findUserById/{userId}/removeFromLikedAlbums/{albumId}"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalLibraryTO removeFromLikedAlbums(@PathVariable("userId") long userId, @PathVariable("albumId") long albumId) {

        try {
            PersonalLibraryDO personalLibraryDO = this.POSTGRES_SERVICE.removeFromLikedAlbums(albumId, userId);
            return ObjectTransformer.generatePersonalLibraryTOFrom(personalLibraryDO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

}
