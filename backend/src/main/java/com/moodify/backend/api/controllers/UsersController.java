package com.moodify.backend.api.controllers;

import com.moodify.backend.api.transferobjects.*;
import com.moodify.backend.services.database.DatabaseService;
import com.moodify.backend.services.database.postgres.PostgresService;
import com.moodify.backend.services.database.util.TOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * This class represents the UsersController in the Moodify application.
 * It is responsible for handling all the user-related requests.
 * The class uses the POSTGRES_SERVICE to interact with the Moodify database.
 * It provides several methods to manage user data, such as finding a user by ID, creating and deleting custom playlists,
 * adding and removing tracks from custom playlists and liked tracks, adding and removing artists from liked artists,
 * adding and removing albums from liked albums, and promoting a user to an artist.
 * The class uses the @RestController annotation to indicate that it is a controller and the @RequestMapping annotation to map the requests to /users.
 * The class has two properties: POSTGRES_SERVICE and TO_OBJECT_ASSEMBLER.
 * The POSTGRES_SERVICE property is used to interact with the Moodify database.
 * The TO_OBJECT_ASSEMBLER property is used to assemble transfer objects from the database entities.
 * The class provides a constructor that initializes these properties.
 */
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
    @GetMapping({"findUserById/{userId}"})
    @ResponseStatus(HttpStatus.OK)
    public UserTO findUserById(@PathVariable("userId") long userId) {

        try {
            return this.TO_OBJECT_ASSEMBLER.generateUserTOFrom(this.POSTGRES_SERVICE.findUserById(userId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
     * This method is a POST request that creates a custom playlist for a user in the Moodify application.
     * It receives a userId and a title as request parameters.
     * The userId represents the ID of the user for whom the playlist is to be created.
     * The title represents the title of the playlist to be created.
     * The method uses the POSTGRES_SERVICE to create the playlist.
     * If the playlist cannot be created, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param userId The ID of the user for whom the playlist is to be created.
     * @param title The title of the playlist to be created.
     * @return The ID of the created playlist.
     */
    @PostMapping({"createCustomPlaylist"})
    @ResponseStatus(HttpStatus.CREATED)
    public Long createCustomPlaylist(@RequestParam long userId, @RequestParam String title) {
        try {
            return this.POSTGRES_SERVICE.createCustomPlaylist(userId, title);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
     * This method is a DELETE request that deletes a custom playlist of a user in the Moodify application.
     * It receives a userId and a playlistId as request parameters.
     * The userId represents the ID of the user whose playlist is to be deleted.
     * The playlistId represents the ID of the playlist to be deleted.
     * The method uses the POSTGRES_SERVICE to delete the playlist.
     * If the playlist cannot be deleted, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param userId The ID of the user whose playlist is to be deleted.
     * @param playlistId The ID of the playlist to be deleted.
     */
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

    /**
     * This method is a POST request that adds a track to a custom playlist of a user in the Moodify application.
     * It receives a TrackTO object as a request body and a userId and a playlistId as request parameters.
     * The TrackTO object represents the track to be added to the playlist.
     * The userId represents the ID of the user whose playlist is to be updated.
     * The playlistId represents the ID of the playlist to be updated.
     * The method uses the POSTGRES_SERVICE to add the track to the playlist.
     * If the track cannot be added, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param trackTO The TrackTO object that represents the track to be added to the playlist.
     * @param userId The ID of the user whose playlist is to be updated.
     * @param playlistId The ID of the playlist to be updated.
     */
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

    /**
     * This method is a DELETE request that removes a track from a custom playlist of a user in the Moodify application.
     * It receives a userId, a playlistId, and a trackId as request parameters.
     * The userId represents the ID of the user whose playlist is to be updated.
     * The playlistId represents the ID of the playlist to be updated.
     * The trackId represents the ID of the track to be removed from the playlist.
     * The method uses the POSTGRES_SERVICE to remove the track from the playlist.
     * If the track cannot be removed, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param userId The ID of the user whose playlist is to be updated.
     * @param playlistId The ID of the playlist to be updated.
     * @param trackId The ID of the track to be removed from the playlist.
     */
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

    /**
     * This method is a POST request that adds a track to the liked tracks of a user in the Moodify application.
     * It receives a TrackTO object as a request body and a userId as a request parameter.
     * The TrackTO object represents the track to be added to the liked tracks.
     * The userId represents the ID of the user whose liked tracks are to be updated.
     * The method uses the POSTGRES_SERVICE to add the track to the liked tracks of the user.
     * If the track cannot be added, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param trackTO The TrackTO object that represents the track to be added to the liked tracks.
     * @param userId The ID of the user whose liked tracks are to be updated.
     */
    @PostMapping({"addToLikedTracks"})
    @ResponseStatus(HttpStatus.OK)
    public void addToLikedTracks(@RequestBody TrackTO trackTO, @RequestParam long userId) {

        try {
            this.POSTGRES_SERVICE.addToLikedTracks(trackTO, userId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    /**
     * This method is a DELETE request that removes a track from the liked tracks of a user in the Moodify application.
     * It receives a userId and a trackId as request parameters.
     * The userId represents the ID of the user whose liked tracks are to be updated.
     * The trackId represents the ID of the track to be removed from the liked tracks.
     * The method uses the POSTGRES_SERVICE to remove the track from the liked tracks of the user.
     * If the track cannot be removed, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param userId The ID of the user whose liked tracks are to be updated.
     * @param trackId The ID of the track to be removed from the liked tracks.
     */
    @DeleteMapping({"deleteFromLikedTracks"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteFromLikedTracks(@RequestParam long userId, @RequestParam long trackId) {

        try {
            this.POSTGRES_SERVICE.deleteFromLikedTracks(userId, trackId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    /**
     * This method is a POST request that adds an artist to the liked artists of a user in the Moodify application.
     * It receives an ArtistTO object as a request body and a userId as a request parameter.
     * The ArtistTO object represents the artist to be added to the liked artists.
     * The userId represents the ID of the user whose liked artists are to be updated.
     * The method uses the POSTGRES_SERVICE to add the artist to the liked artists of the user.
     * If the artist cannot be added, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param artistTO The ArtistTO object that represents the artist to be added to the liked artists.
     * @param userId The ID of the user whose liked artists are to be updated.
     */
    @PostMapping({"addToLikedArtists"})
    @ResponseStatus(HttpStatus.OK)
    public void addToLikedArtists(@RequestBody ArtistTO artistTO, @RequestParam long userId) {

        try {
            this.POSTGRES_SERVICE.addToLikedArtists(artistTO, userId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    /**
     * This method is a DELETE request that removes an artist from the liked artists of a user in the Moodify application.
     * It receives a userId and an artistId as request parameters.
     * The userId represents the ID of the user whose liked artists are to be updated.
     * The artistId represents the ID of the artist to be removed from the liked artists.
     * The method uses the POSTGRES_SERVICE to remove the artist from the liked artists of the user.
     * If the artist cannot be removed, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param userId The ID of the user whose liked artists are to be updated.
     * @param artistId The ID of the artist to be removed from the liked artists.
     */
    @DeleteMapping({"deleteFromLikedArtists"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteFromLikedArtists(@RequestParam long userId, @RequestParam long artistId) {

        try {
            this.POSTGRES_SERVICE.deleteFromLikedArtists(artistId, userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    /**
     * This method is a POST request that adds an album to the liked albums of a user in the Moodify application.
     * It receives an AlbumTO object as a request body and a userId as a request parameter.
     * The AlbumTO object represents the album to be added to the liked albums.
     * The userId represents the ID of the user whose liked albums are to be updated.
     * The method uses the POSTGRES_SERVICE to add the album to the liked albums of the user.
     * If the album cannot be added, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param albumTO The AlbumTO object that represents the album to be added to the liked albums.
     * @param userId The ID of the user whose liked albums are to be updated.
     */
    @PostMapping({"addToLikedAlbums"})
    @ResponseStatus(HttpStatus.OK)
    public void addToLikedAlbums(@RequestBody AlbumTO albumTO, @RequestParam long userId) {

        try {
            this.POSTGRES_SERVICE.addToLikedAlbums(albumTO, userId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    /**
     * This method is a DELETE request that removes an album from the liked albums of a user in the Moodify application.
     * It receives a userId and an albumId as request parameters.
     * The userId represents the ID of the user whose liked albums are to be updated.
     * The albumId represents the ID of the album to be removed from the liked albums.
     * The method uses the POSTGRES_SERVICE to remove the album from the liked albums of the user.
     * If the album cannot be removed, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param userId The ID of the user whose liked albums are to be updated.
     * @param albumId The ID of the album to be removed from the liked albums.
     */
    @DeleteMapping({"deleteFromLikedAlbums"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteFromLikedAlbums(@RequestParam long userId, @RequestParam long albumId) {

        try {
            this.POSTGRES_SERVICE.deleteFromLikedAlbums(albumId, userId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    /**
     * This method is a POST request that promotes a user to an artist in the Moodify application.
     * It receives a userId as a path variable and two strings, picture_big and picture_small, as request parameters.
     * The userId represents the ID of the user to be promoted to an artist.
     * The picture_big and picture_small represent the URLs of the big and small profile pictures of the user, respectively.
     * The method uses the POSTGRES_SERVICE to promote the user to an artist.
     * If the user cannot be promoted, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param userId The ID of the user to be promoted to an artist.
     * @param picture_big The URL of the big profile picture of the user.
     * @param picture_small The URL of the small profile picture of the user.
     */
    @PostMapping({"promoteToArtist/{userId}"})
    @ResponseStatus(HttpStatus.OK)
    public void promoteToArtist(@PathVariable("userId") long userId, @RequestParam String picture_big, @RequestParam String picture_small) {

        try {
            this.POSTGRES_SERVICE.promoteUserToArtist(userId, picture_big, picture_small);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

}
