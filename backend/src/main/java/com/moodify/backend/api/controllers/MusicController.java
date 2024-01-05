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

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/music")
public class MusicController {

    private final DeezerApi API_SERVICE;
    private final PostgresService DATABASE_SERVICE;
    private final TOAssembler TO_OBJECT_ASSEMBLER;
    @Autowired
    public MusicController(DeezerApi API_SERVICE,
                           PostgresService DATABASE_SERVICE,
                           TOAssembler TO_OBJECT_ASSEMBLER) {
        this.API_SERVICE = API_SERVICE;
        this.DATABASE_SERVICE = DATABASE_SERVICE;
        this.TO_OBJECT_ASSEMBLER = TO_OBJECT_ASSEMBLER;
    }

    @GetMapping({"track/{trackId}"})
    @ResponseStatus(HttpStatus.OK)
    public TrackTO getTrack(@PathVariable("trackId") long trackId) {
        try {
            return API_SERVICE.getTrack(trackId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping({"artist/{artistId}"})
    @ResponseStatus(HttpStatus.OK)
    public ArtistTO getArtist(@PathVariable("artistId") long artistId, @RequestParam("source")  Source source) {
        try {
            if (source == Source.DEEZER) {
                return API_SERVICE.getArtist(artistId);
            } else if (source == Source.MOODIFY) {
                UserDO moodifyArtist = DATABASE_SERVICE.getArtist(artistId);

                return this.TO_OBJECT_ASSEMBLER.generateArtistTOFromMoodifyArtist(moodifyArtist);
            } else {
                throw new SourceNotFoundException();
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping({"playlist/{playlistId}"})
    @ResponseStatus(HttpStatus.OK)
    public PlaylistTO getPlaylist(@PathVariable("playlistId") long playlistId, @RequestParam("source")  Source source) {
        try {
            if (source == Source.DEEZER) {
                return API_SERVICE.getPlaylist(playlistId);
            } else if (source == Source.MOODIFY) {
                return TO_OBJECT_ASSEMBLER.generatePlaylistTOFrom(DATABASE_SERVICE.findPlaylistById(playlistId));
            } else {
                throw new SourceNotFoundException();
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping({"album/{albumId}"})
    @ResponseStatus(HttpStatus.OK)
    public AlbumTO getAlbum(@PathVariable("albumId") long albumId) {
        try {
            return API_SERVICE.getAlbum(albumId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }

    @GetMapping({"search/tracks/{searchQuery}"})
    @ResponseStatus(HttpStatus.OK)
    public List<TrackTO> search(@PathVariable ("searchQuery") String query) {
        try {
            List<TrackTO> deezerTracks = API_SERVICE.getTrackSearch(query);
            deezerTracks.forEach(track -> track.setSource(Source.DEEZER));

            List<TrackTO> moodifySingles = this.TO_OBJECT_ASSEMBLER.generateTrackTOListFromMoodifySingleDOList(DATABASE_SERVICE.searchSingles(query));
            moodifySingles.forEach(track -> track.setSource(Source.MOODIFY));



            return Stream.concat(moodifySingles.stream(), deezerTracks.stream()).toList();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping({"search/albums/{searchQuery}"})
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumTO> searchAlbum(@PathVariable ("searchQuery") String query) {
        try {
            return API_SERVICE.getAlbums(query);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping({"search/artists/{searchQuery}"})
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistTO> searchArtist(@PathVariable ("searchQuery") String query) {
        try {
            List<ArtistTO> deezerArtists = API_SERVICE.getArtists(query);
            deezerArtists.forEach(artist -> artist.setSource(Source.DEEZER));

            List<UserDO> artists = this.DATABASE_SERVICE.searchArtists(query);
            List<ArtistTO> moodifyArtists = TO_OBJECT_ASSEMBLER.generateArtistTOFromUserDO(artists);
            moodifyArtists.forEach(artist -> artist.setSource(Source.MOODIFY));


            return Stream.concat(moodifyArtists.stream(), deezerArtists.stream()).toList();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping({"search/playlists/{searchQuery}"})
    @ResponseStatus(HttpStatus.OK)
    public List<PlaylistTO> searchPlaylist(@PathVariable ("searchQuery") String query) {
        try {
            List<PlaylistDO>  usersPlaylistsDOs = this.DATABASE_SERVICE.searchPlaylists(query);
            List<PlaylistTO> usersPlaylistsTOs = this.TO_OBJECT_ASSEMBLER.generatePlaylistTOListFrom(usersPlaylistsDOs);
            usersPlaylistsTOs.forEach(pl -> pl.setSource(Source.MOODIFY));


            List<PlaylistTO> deezerPlaylistsTOs = this.API_SERVICE.getPlaylists(query);
            deezerPlaylistsTOs.forEach(pl -> pl.setSource(Source.DEEZER));

            return Stream.concat(usersPlaylistsTOs.stream(), deezerPlaylistsTOs.stream()).toList();

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}
