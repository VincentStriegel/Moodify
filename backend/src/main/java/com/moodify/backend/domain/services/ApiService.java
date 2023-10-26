package com.moodify.backend.domain.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodify.backend.api.transferobjects.TrackTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;


@Component
public class ApiService {

    private final String deezerApiUrl = "https://deezerdevs-deezer.p.rapidapi.com/track/";
    private final String rapidApiHost = "deezerdevs-deezer.p.rapidapi.com";
    
    private final String rapidDeezerApiKey;

    @Autowired
    public ApiService(@Value("${deezerApiKey}") String rapidDeezerApiKey){
        this.rapidDeezerApiKey = rapidDeezerApiKey;
    }

    public TrackTO getTrack (int id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = deezerApiUrl + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("X-RapidAPI-Key", rapidDeezerApiKey);
        headers.add("X-RapidAPI-Host", rapidApiHost);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> jsonMap = objectMapper.readValue(responseEntity.getBody(), new TypeReference<Map<String, Object>>() {});
            TrackTO trackTO = new TrackTO();
            trackTO.setId((int) jsonMap.get("id"));
            trackTO.setTitle((String) jsonMap.get("title"));
            trackTO.setDuration((int) jsonMap.get("duration"));
            trackTO.setPreview((String) jsonMap.get("preview"));
            trackTO.setRelease_date((String) jsonMap.get("release_date"));
            return trackTO;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle the exception appropriately
        }
    }
}
