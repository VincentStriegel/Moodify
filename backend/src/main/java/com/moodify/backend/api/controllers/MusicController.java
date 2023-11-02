package com.moodify.backend.api.controllers;

import com.moodify.backend.api.transferobjects.TrackTO;
import com.moodify.backend.domain.services.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/music")
public class MusicController {

    private final ApiService apiService;

    @Autowired
    public MusicController(ApiService apiService){
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

}
