package com.moodify.backend.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class DeezerApiRequester {

    private final String RAPID_API_HOST = "deezerdevs-deezer.p.rapidapi.com";
    private final String HEADER_RAPID_API_KEY = "X-RapidAPI-Key";
    private final String HEADER_RAPID_API_HOST = "X-RapidAPI-Host";
    private final String RAPID_DEEZER_API_KEY;

    @Autowired
    public DeezerApiRequester(@Value("${deezerApiKey}") String RAPID_DEEZER_API_KEY) {
        this.RAPID_DEEZER_API_KEY = RAPID_DEEZER_API_KEY;
    }

    public ResponseEntity<String> makeApiRequest(String url) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add(HEADER_RAPID_API_KEY, RAPID_DEEZER_API_KEY);
        headers.add(HEADER_RAPID_API_HOST, RAPID_API_HOST);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }
}
