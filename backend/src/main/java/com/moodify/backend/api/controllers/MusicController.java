package com.moodify.backend.api.controllers;

import com.moodify.backend.api.transferobjects.*;
import com.moodify.backend.api.util.Source;
import com.moodify.backend.services.database.exceptions.other.SourceNotFoundException;
import com.moodify.backend.services.database.objects.PlaylistDO;
import com.moodify.backend.services.database.objects.UserDO;
import com.moodify.backend.services.database.postgres.PostgresService;
import com.moodify.backend.services.database.util.TOAssembler;
import com.moodify.backend.services.music.deezer.DeezerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class represents the music controller in the Moodify application.
 * It is responsible for handling all the music-related requests.
 * The class uses the Deezer API and the Moodify database to fetch the required data.
 * It provides methods to fetch a track, an artist, a playlist, an album, and to perform a search for tracks, albums, artists, and playlists.
 * It also provides a method to get recommendations for a user.
 * The class uses the @RestController annotation to indicate that it is a controller and the @RequestMapping annotation to map the requests to /music.
 * The class has four properties: DEEZER_API, DEFAULT_LIMIT, DATABASE_SERVICE, and TO_OBJECT_ASSEMBLER.
 * The DEEZER_API property is used to interact with the Deezer API.
 * The DEFAULT_LIMIT property is used to limit the number of results returned by the Deezer API.
 * The DATABASE_SERVICE property is used to interact with the Moodify database.
 * The TO_OBJECT_ASSEMBLER property is used to convert the data fetched from the Moodify database to transfer objects.
 * The class provides a constructor that initializes these properties.
 */
@RestController
@RequestMapping("/music")
public class MusicController {
    private final DeezerApi DEEZER_API;
    private final long DEFAULT_LIMIT = 100;
    private final PostgresService DATABASE_SERVICE;
    private final TOAssembler TO_OBJECT_ASSEMBLER;
    @Autowired
    public MusicController(DeezerApi DEEZER_API,
                           PostgresService DATABASE_SERVICE,
                           TOAssembler TO_OBJECT_ASSEMBLER) {
        this.DEEZER_API = DEEZER_API;
        this.DATABASE_SERVICE = DATABASE_SERVICE;
        this.TO_OBJECT_ASSEMBLER = TO_OBJECT_ASSEMBLER;
    }

    /**
     * This method is a GET request that returns a track based on the provided trackId.
     * It uses the Deezer API to fetch the track.
     * If the track cannot be fetched, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param trackId The ID of the track to be fetched.
     * @return The fetched track as a TrackTO object.
     */
    @GetMapping({"track/{trackId}"})
    @ResponseStatus(HttpStatus.OK)
    public TrackTO getTrack(@PathVariable("trackId") long trackId) {
        try {

            return DEEZER_API.getTrack(trackId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
     * This method is a GET request that returns an artist based on the provided artistId and source.
     * It uses the Deezer API or the Moodify database to fetch the artist, depending on the source.
     * If the artist cannot be fetched, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param artistId The ID of the artist to be fetched.
     * @param source The source of the artist data (Deezer or Moodify).
     * @return The fetched artist as an ArtistTO object.
     */
    @GetMapping({"artist/{artistId}"})
    @ResponseStatus(HttpStatus.OK)
    public ArtistTO getArtist(@PathVariable("artistId") long artistId, @RequestParam("source")  Source source) {
        try {

            if (source == Source.DEEZER) {
               return DEEZER_API.getArtist(artistId);
            } else if (source == Source.MOODIFY) {

                UserDO moodifyArtist = DATABASE_SERVICE.getArtist(artistId);
                ArtistTO artist = this.TO_OBJECT_ASSEMBLER.generateArtistTOFromMoodifyArtist(moodifyArtist);
                artist.setSource(Source.MOODIFY);

                return artist;
            } else {
                throw new SourceNotFoundException();
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
     * This method is a GET request that returns a playlist based on the provided playlistId and source.
     * It uses the Deezer API or the Moodify database to fetch the playlist, depending on the source.
     * If the playlist cannot be fetched, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param playlistId The ID of the playlist to be fetched.
     * @param source The source of the playlist data (Deezer or Moodify).
     * @return The fetched playlist as a PlaylistTO object.
     */
    @GetMapping({"playlist/{playlistId}"})
    @ResponseStatus(HttpStatus.OK)
    public PlaylistTO getPlaylist(@PathVariable("playlistId") long playlistId, @RequestParam("source")  Source source) {
        try {
            PlaylistTO playlist = null;

            if (source == Source.DEEZER) {
                return DEEZER_API.getPlaylist(playlistId);
            } else if (source == Source.MOODIFY) {

                playlist = TO_OBJECT_ASSEMBLER.generatePlaylistTOFrom(DATABASE_SERVICE.findPlaylistById(playlistId));
                playlist.setSource(Source.MOODIFY);

                return playlist;
            } else {
                throw new SourceNotFoundException();
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
     * This method is a GET request that returns an album based on the provided albumId.
     * It uses the Deezer API to fetch the album.
     * If the album cannot be fetched, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param albumId The ID of the album to be fetched.
     * @return The fetched album as an AlbumTO object.
     */
    @GetMapping({"album/{albumId}"})
    @ResponseStatus(HttpStatus.OK)
    public AlbumTO getAlbum(@PathVariable("albumId") long albumId) {
        try {
            return DEEZER_API.getAlbum(albumId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    /**
     * This method is a GET request that returns a list of tracks based on the provided search query.
     * It uses the Deezer API and the Moodify database to fetch the tracks.
     * If the tracks cannot be fetched, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param query The search query to be used to fetch the tracks.
     * @return The fetched tracks as a list of TrackTO objects.
     */
    @GetMapping({"search/tracks/{searchQuery}"})
    @ResponseStatus(HttpStatus.OK)
    public List<TrackTO> search(@PathVariable ("searchQuery") String query) {
        try {
            List<TrackTO> deezerTracks = DEEZER_API.getTrackSearch(query, DEFAULT_LIMIT);

            List<TrackTO> moodifySingles = this.TO_OBJECT_ASSEMBLER.generateTrackTOListFromMoodifySingleDOList(DATABASE_SERVICE.searchSingles(query));
            moodifySingles.forEach(track -> track.setSource(Source.MOODIFY));

            return Stream.concat(moodifySingles.stream(), deezerTracks.stream()).toList();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
     * This method is a GET request that returns a list of albums based on the provided search query.
     * It uses the Deezer API to fetch the albums.
     * If the albums cannot be fetched, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param query The search query to be used to fetch the albums.
     * @return The fetched albums as a list of AlbumTO objects.
     */
    @GetMapping({"search/albums/{searchQuery}"})
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumTO> searchAlbum(@PathVariable ("searchQuery") String query) {
        try {
            return DEEZER_API.getAlbums(query, DEFAULT_LIMIT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
     * This method is a GET request that returns a list of artists based on the provided search query.
     * It uses the Deezer API and the Moodify database to fetch the artists.
     * If the artists cannot be fetched, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param query The search query to be used to fetch the artists.
     * @return The fetched artists as a list of ArtistTO objects.
     */
    @GetMapping({"search/artists/{searchQuery}"})
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistTO> searchArtist(@PathVariable ("searchQuery") String query) {
        try {
            List<ArtistTO> deezerArtists = DEEZER_API.getArtists(query, DEFAULT_LIMIT);

            List<UserDO> artists = this.DATABASE_SERVICE.searchArtists(query);
            List<ArtistTO> moodifyArtists = TO_OBJECT_ASSEMBLER.generateArtistTOFromUserDO(artists);
            moodifyArtists.forEach(artist -> artist.setSource(Source.MOODIFY));


            return Stream.concat(moodifyArtists.stream(), deezerArtists.stream()).toList();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
     * This method is a GET request that returns a list of playlists based on the provided search query.
     * It uses the Deezer API and the Moodify database to fetch the playlists.
     * If the playlists cannot be fetched, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param query The search query to be used to fetch the playlists.
     * @return The fetched playlists as a list of PlaylistTO objects.
     */
    @GetMapping({"search/playlists/{searchQuery}"})
    @ResponseStatus(HttpStatus.OK)
    public List<PlaylistTO> searchPlaylist(@PathVariable ("searchQuery") String query) {
        try {
            List<PlaylistDO>  usersPlaylistsDOs = this.DATABASE_SERVICE.searchPlaylists(query);
            List<PlaylistTO> usersPlaylistsTOs = this.TO_OBJECT_ASSEMBLER.generatePlaylistTOListFrom(usersPlaylistsDOs);
            usersPlaylistsTOs.forEach(pl -> pl.setSource(Source.MOODIFY));


            List<PlaylistTO> deezerPlaylistsTOs = this.DEEZER_API.getPlaylists(query, DEFAULT_LIMIT);

            return Stream.concat(usersPlaylistsTOs.stream(), deezerPlaylistsTOs.stream()).toList();

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
     * This method is a GET request that returns a list of recommended tracks for a user based on the provided userId.
     * It uses the Deezer API and the Moodify database to fetch the tracks.
     * If the tracks cannot be fetched, it throws a ResponseStatusException with a BAD_REQUEST status.
     *
     * @param userId The ID of the user for whom the recommendations are to be fetched.
     * @return The recommended tracks as a list of TrackTO objects.
     */
    @GetMapping({"recommendations/{userId}"})
    @ResponseStatus(HttpStatus.OK)
    public List<TrackTO> getRecommendationsFor(@PathVariable ("userId") long userId) {
        try {

            String mostPopularArtist = this.DATABASE_SERVICE.findMostPopularArtist(userId);
            List<TrackTO> recommendedTracks = new ArrayList<TrackTO>();
            if (mostPopularArtist != null) {
                recommendedTracks = this.DEEZER_API.getTrackSearch(mostPopularArtist, 15);
            }

            return recommendedTracks;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}
