package com.moodify.backend.domain.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodify.backend.api.transferobjects.AlbumTO;
import com.moodify.backend.api.transferobjects.ArtistTO;
import com.moodify.backend.api.transferobjects.TrackTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ApiService {
    private final String DEEZER_API_URL = "https://deezerdevs-deezer.p.rapidapi.com/";
    private final String RAPID_API_HOST = "deezerdevs-deezer.p.rapidapi.com";

    private final String RAPID_DEEZER_API_KEY;

    @Autowired
    public ApiService(@Value("${deezerApiKey}") String RAPID_DEEZER_API_KEY) {
        this.RAPID_DEEZER_API_KEY = RAPID_DEEZER_API_KEY;
    }

    public TrackTO getTrack(long id) {
        String url = DEEZER_API_URL + "track/" + id;

        ResponseEntity<String> responseEntity = makeApiRequest(url);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> jsonMap = objectMapper.readValue(responseEntity.getBody(), new TypeReference<Map<String, Object>>() { });
            TrackTO trackTO = mapToTrackTO(jsonMap);
            return trackTO;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle the exception appropriately
        }
    }

    public List<TrackTO> getTrackSearch(String query) {
        String url = DEEZER_API_URL + "search?q=" + query;

        ResponseEntity<String> responseEntity = makeApiRequest(url);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, Object> jsonMap = objectMapper.readValue(responseEntity.getBody(), new TypeReference<Map<String, Object>>() { });
            List<Map<String, Object>> tracksList = (List<Map<String, Object>>) jsonMap.get("data");
            List<TrackTO> trackTOs = new ArrayList<>();

            for (Map<String, Object> trackMap : tracksList) {
                TrackTO trackTO = mapToTrackTO(trackMap);
                trackTOs.add(trackTO);
            }

            return trackTOs;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList(); // Handle the exception appropriately
        }
    }

    private ResponseEntity<String> makeApiRequest(String url) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("X-RapidAPI-Key", RAPID_DEEZER_API_KEY);
        headers.add("X-RapidAPI-Host", RAPID_API_HOST);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    private TrackTO mapToTrackTO(Map<String, Object> trackMap) {
        TrackTO trackTO = new TrackTO();
        Number trackId = (Number) trackMap.get("id");
        trackTO.setId(trackId.longValue());
        trackTO.setTitle((String) trackMap.get("title"));
        trackTO.setDuration(((Number) trackMap.get("duration")).intValue());
        trackTO.setPreview((String) trackMap.get("preview"));
        trackTO.setRelease_date((String) trackMap.get("release_date"));
        trackTO.setAlbum(mapToAlbumTO((Map<String, Object>) trackMap.get("album")));
        trackTO.setArtist(mapToArtistTO((Map<String, Object>) trackMap.get("artist")));

        return trackTO;
    }

    private AlbumTO mapToAlbumTO(Map<String, Object> albumMap) {
        AlbumTO albumTO = new AlbumTO();
        albumTO.setId(((Number) albumMap.get("id")).intValue());
        albumTO.setTitle((String) albumMap.get("title"));
        albumTO.setCover_small((String) albumMap.get("cover_small"));
        albumTO.setCover_big((String) albumMap.get("cover_big"));
        albumTO.setRelease_date((String) albumMap.get("release_date"));

        return albumTO;
    }

    private ArtistTO mapToArtistTO(Map<String, Object> artistMap) {
        ArtistTO artistTO = new ArtistTO();
        artistTO.setId(((Number) artistMap.get("id")).intValue());
        artistTO.setName((String) artistMap.get("name"));
        artistTO.setPicture_small((String) artistMap.get("picture_small"));
        artistTO.setPicture_big((String) artistMap.get("picture_big"));

        return artistTO;
    }
}
