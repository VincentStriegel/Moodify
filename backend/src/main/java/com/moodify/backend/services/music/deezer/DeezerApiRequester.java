package com.moodify.backend.services.music.deezer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * This class is responsible for making API requests to the Deezer API.
 * It is annotated with @Component, indicating that it is a Spring Bean and can be autowired into other components.
 * The class contains a method for making API requests to a specified URL and returning the response.
 * The API requests are made using the RestTemplate class from Spring's web client module.
 * The headers of the requests are set to accept JSON and include the RapidAPI key and host.
 * The RapidAPI key is injected into the class through the constructor using Spring's @Value annotation.
 */
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

    /**
     * Makes an API request to the specified URL and returns the response.
     * The request is made using the RestTemplate class from Spring's web client module.
     * The headers of the request are set to accept JSON and include the RapidAPI key and host.
     * The method uses the HTTP GET method to make the request.
     *
     * @param url The URL to which the API request is to be made.
     * @return The response from the API request as a ResponseEntity<String>.
     */
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
