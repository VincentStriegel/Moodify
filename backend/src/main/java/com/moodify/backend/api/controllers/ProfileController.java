package com.moodify.backend.api.controllers;

import com.moodify.backend.api.transferobjects.AlbumTO;
import com.moodify.backend.api.transferobjects.ArtistTO;
import com.moodify.backend.api.transferobjects.TrackTO;
import com.moodify.backend.domain.services.database.DatabaseService;
import com.moodify.backend.domain.services.database.PostgresService;
import com.moodify.backend.domain.services.database.databaseobjects.PersonalLibraryDO;
import com.moodify.backend.domain.services.database.databaseobjects.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class ProfileController {
    private final DatabaseService POSTGRES_SERVICE;

    @Autowired
    public ProfileController(PostgresService USER_SERVICE) {
        this.POSTGRES_SERVICE = USER_SERVICE;
    }

    @GetMapping({"getUser/{profileId}"})
    @ResponseStatus(HttpStatus.OK)
    public UserDO getUser(@PathVariable("profileId") long profileId) {

        try {
            return POSTGRES_SERVICE.getUser(profileId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping({"getUser/{profileId}/addCustomPlaylist/{title}"})
    @ResponseStatus(HttpStatus.CREATED)
    public PersonalLibraryDO addCustomPlaylist(@PathVariable("profileId") long profileId, @PathVariable("title") String title) {
        try {
            return this.POSTGRES_SERVICE.addCustomPlaylist(profileId, title);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping({"getUser/{profileId}/removeCustomPlaylist/{playlistId}"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalLibraryDO removeCustomPlaylist(@PathVariable("profileId") long profileId,
                                                  @PathVariable("playlistId") long playlistId) {
        try {
            return this.POSTGRES_SERVICE.removeCustomPlaylist(profileId, playlistId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping({"getUser/{profileId}/addToCustomPlaylist/{playlistId}"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalLibraryDO addToCustomPlaylist(@RequestBody TrackTO trackTO, @PathVariable("profileId") long profileId, @PathVariable("playlistId") long playlistId) {

        try {
            return this.POSTGRES_SERVICE.addToCustomPlaylist(trackTO, profileId, playlistId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @DeleteMapping({"getUser/{profileId}/removeFromCustomPlaylist/{playlistId}/track/{trackId}"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalLibraryDO removeFromCustomPlaylist(@PathVariable("profileId") long profileId, @PathVariable("playlistId") long playlistId, @PathVariable("trackId") long trackId) {

        try {
            return this.POSTGRES_SERVICE.removeFromCustomPlaylist(profileId, playlistId, trackId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @PostMapping({"getUser/{profileId}/addToLikedTracks"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalLibraryDO addToLikedTracks(@RequestBody TrackTO trackTO, @PathVariable("profileId") long profileId) {

        try {
            return this.POSTGRES_SERVICE.addToLikedTracks(trackTO, profileId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @DeleteMapping({"getUser/{profileId}/removeFromLikedTracks/{trackId}"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalLibraryDO addToLikedTracks(@PathVariable("profileId") long profileId, @PathVariable("trackId") long trackId) {

        try {
            return this.POSTGRES_SERVICE.removeFromLikedTracks(profileId, trackId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @PostMapping({"getUser/{profileId}/addToLikedArtists"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalLibraryDO addToLikedArtists(@RequestBody ArtistTO artistTO, @PathVariable("profileId") long profileId) {

        try {
            return this.POSTGRES_SERVICE.addToLikedArtists(artistTO, profileId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @DeleteMapping({"getUser/{profileId}/removeFromLikedArtists/{artistId}"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalLibraryDO removeFromLikedArtists(@PathVariable("profileId") long profileId, @PathVariable("artistId") long artistId) {

        try {
            return this.POSTGRES_SERVICE.removeFromLikedArtists(artistId, profileId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @PostMapping({"getUser/{profileId}/addToLikedAlbums"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalLibraryDO addToLikedAlbums(@RequestBody AlbumTO albumTO, @PathVariable("profileId") long profileId) {

        try {
            return this.POSTGRES_SERVICE.addToLikedAlbums(albumTO, profileId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @DeleteMapping({"getUser/{profileId}/removeFromLikedArtists/{albumId}"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalLibraryDO removeFromLikedAlbums(@PathVariable("profileId") long profileId, @PathVariable("albumId") long albumId) {

        try {
            return this.POSTGRES_SERVICE.removeFromLikedAlbums(albumId, profileId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

}
