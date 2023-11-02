package com.moodify.backend.domain.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodify.backend.api.transferobjects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Component
public class DeezerApi implements ApiService {
    private final DeezerApiRequester DEEZER_API_REQUESTER;
    private final String DEEZER_API_URL = "https://deezerdevs-deezer.p.rapidapi.com/";

    @Autowired
    public DeezerApi(DeezerApiRequester DEEZER_API_REQUESTER) {
        this.DEEZER_API_REQUESTER = DEEZER_API_REQUESTER;
    }

    public TrackTO getTrack(long id) {
        String url = DEEZER_API_URL + "track/" + id;

        ResponseEntity<String> responseEntity = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, Object> trackMap = objectMapper.readValue(responseEntity.getBody(), new TypeReference<Map<String, Object>>() {
            });
            TrackTO trackTO = Mapper.toTrackTO(trackMap);
            return trackTO;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle the exception appropriately
        }
    }

    public List<TrackTO> getTrackSearch(String query) {
        String url = DEEZER_API_URL + "search?q=" + query;

        ResponseEntity<String> responseEntity = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        ObjectMapper objectMapper = new ObjectMapper();


        try {
            Map<String, Object> jsonMap = objectMapper.readValue(responseEntity.getBody(), new TypeReference<Map<String, Object>>() {
            });
            List<Map<String, Object>> tracksList = (List<Map<String, Object>>) jsonMap.get("data");
            List<TrackTO> trackTOs = new ArrayList<>();

            for (Map<String, Object> trackMap : tracksList) {
                TrackTO trackTO = Mapper.toTrackTO(trackMap);
                trackTOs.add(trackTO);
            }

            return trackTOs;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList(); // Handle the exception appropriately
        }
    }

    public List<ArtistTO> getArtists(String query) {
        String url = DEEZER_API_URL + "search/artist?q=" + query;

        ResponseEntity<String> responseEntity = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, Object> jsonMap = objectMapper.readValue(responseEntity.getBody(), new TypeReference<Map<String, Object>>() {
            });
            List<Map<String, Object>> artistList = (List<Map<String, Object>>) jsonMap.get("data");
            List<ArtistTO> artistTOs = new ArrayList<>();

            for (Map<String, Object> artistMap : artistList) {
                ArtistTO artistTO = Mapper.toArtistTO(artistMap);
                artistTOs.add(artistTO);
            }

            return artistTOs;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList(); // Handle the exception appropriately
        }

    }

    public List<PlaylistTO> getPlaylists(String query) {
        String url = DEEZER_API_URL + "search/playlist?q=" + query;

        ResponseEntity<String> responseEntity = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, Object> jsonMap = objectMapper.readValue(responseEntity.getBody(), new TypeReference<Map<String, Object>>() {
            });
            List<Map<String, Object>> playlistList = (List<Map<String, Object>>) jsonMap.get("data");
            List<PlaylistTO> playlistTOs = new ArrayList<>();

            for (Map<String, Object> playlistMap : playlistList) {
                PlaylistTO playlistTO = Mapper.toPlaylistTO(playlistMap);
                playlistTOs.add(playlistTO);
            }

            return playlistTOs;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList(); // Handle the exception appropriately
        }
    }

    public List<AlbumTO> getAlbums(String query) {
        String url = DEEZER_API_URL + "search/album?q=" + query;

        ResponseEntity<String> responseEntity = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, Object> jsonMap = objectMapper.readValue(responseEntity.getBody(), new TypeReference<Map<String, Object>>() {
            });
            List<Map<String, Object>> albumList = (List<Map<String, Object>>) jsonMap.get("data");
            List<AlbumTO> albumTOs = new ArrayList<>();

            for (Map<String, Object> albumMap : albumList) {
                AlbumTO albumTO = Mapper.toAlbumTO(albumMap);
                albumTOs.add(albumTO);
            }

            return albumTOs;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList(); // Handle the exception appropriately
        }

    }

    public List<UserTO> getUsers(String query) {
        String url = DEEZER_API_URL + "search/user?q=" + query;

        ResponseEntity<String> responseEntity = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, Object> jsonMap = objectMapper.readValue(responseEntity.getBody(), new TypeReference<Map<String, Object>>() {
            });
            List<Map<String, Object>> userList = (List<Map<String, Object>>) jsonMap.get("data");
            List<UserTO> userTOs = new ArrayList<>();

            for (Map<String, Object> userMap : userList) {
                UserTO userTOTO = Mapper.toUserTO(userMap);
                userTOs.add(userTOTO);
            }

            return userTOs;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList(); // Handle the exception appropriately
        }
    }

    //TODO
    //public List<TrackTO> getTracksFromPlaylist()

}
