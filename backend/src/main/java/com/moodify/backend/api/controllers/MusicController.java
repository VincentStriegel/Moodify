package com.moodify.backend.api.controllers;

import com.moodify.backend.api.transferobjects.TrackTO;
import com.moodify.backend.domain.services.APIs.DeezerApi;
import com.moodify.backend.domain.services.apiInterfaces.ApiServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/music")
public class MusicController {

    private final ApiServices apiService;

    //Check for Autowired
    @Autowired
    public MusicController(DeezerApi apiService){
        this.apiService = apiService;
    }

    @GetMapping({"track/{trackId}"})
    @ResponseStatus(HttpStatus.OK)
    public TrackTO getTrack(@PathVariable("trackId") int trackId){
        return apiService.getTrack(trackId);
    }

}
