package com.moodify.backend.api.controllers;

import com.moodify.backend.api.transferobjects.*;
import com.moodify.backend.domain.services.DeezerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/music")
public class MusicController {

    private final DeezerApi apiService;

    @Autowired
    public MusicController(DeezerApi apiService){
        this.apiService = apiService;
    }

    @GetMapping({"track/{trackId}"})
    @ResponseStatus(HttpStatus.OK)
    public TrackTO getTrack(@PathVariable("trackId") long trackId){
        return apiService.getTrack(trackId);
    }

    @GetMapping({"search/{searchQuery}"})
    @ResponseStatus(HttpStatus.OK)
    public List<TrackTO> search(@PathVariable ("searchQuery") String query){
        return apiService.getTrackSearch(query);
    }

    @GetMapping({"album/{searchQuery}"})
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumTO> searchAlbum(@PathVariable ("searchQuery") String query){
        return apiService.getAlbums(query);
    }

    @GetMapping({"artist/{searchQuery}"})
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistTO> searchArtist(@PathVariable ("searchQuery") String query){
        return apiService.getArtists(query);
    }

    @GetMapping({"playlist/{searchQuery}"})
    @ResponseStatus(HttpStatus.OK)
    public List<PlaylistTO> searchPlaylist(@PathVariable ("searchQuery") String query){
        return apiService.getPlaylists(query);
    }

    @GetMapping({"user/{searchQuery}"})
    @ResponseStatus(HttpStatus.OK)
    public List<UserTO> searchUser(@PathVariable ("searchQuery") String query){
        return apiService.getUsers(query);
    }



}
