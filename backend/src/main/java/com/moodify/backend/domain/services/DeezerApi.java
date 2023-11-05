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
    private final String DEEZER_API_URL = "https://api.deezer.com/";

    @Autowired
    public DeezerApi(DeezerApiRequester DEEZER_API_REQUESTER) {
        this.DEEZER_API_REQUESTER = DEEZER_API_REQUESTER;
    }

    public PlaylistTO getPlaylist(long id) {
        String url = DEEZER_API_URL + "playlist/" + id;

        ResponseEntity<String> responsePlaylist = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        Map<String, Object> playlistMap = getMapFrom(responsePlaylist);

        String trackListURL = (String) playlistMap.get("tracklist");
        ResponseEntity<String> responseTrackList = this.DEEZER_API_REQUESTER.makeApiRequest(trackListURL);
        Map<String, Object> trackListMap = getMapFrom(responseTrackList);
        List<Map<String, Object>> tracksList = (List<Map<String, Object>>) trackListMap.get("data");


        PlaylistTO playlistTO = Mapper.toPlaylistTO(playlistMap, tracksList);

        return playlistTO;
    }

    public AlbumTO getAlbum(long id) {
        String url = DEEZER_API_URL + "album/" + id;

        ResponseEntity<String> responseAlbum = this.DEEZER_API_REQUESTER.makeApiRequest(url);

        Map<String, Object> albumMap = getMapFrom(responseAlbum);


        String trackListURL = (String) albumMap.get("tracklist");
        ResponseEntity<String> responseTrackList = this.DEEZER_API_REQUESTER.makeApiRequest(trackListURL);
        Map<String, Object> trackListMap = getMapFrom(responseTrackList);
        List<Map<String, Object>> tracksList = (List<Map<String, Object>>) trackListMap.get("data");


        AlbumTO albumTO = Mapper.toAlbumTO(albumMap, tracksList);

        return albumTO;
    }

    public ArtistTO getArtist(long id) {
        String url = DEEZER_API_URL + "artist/" + id;

        ResponseEntity<String> responseArtist = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        Map<String, Object> artistMap = getMapFrom(responseArtist);

        String trackListURL = (String) artistMap.get("tracklist");
        ResponseEntity<String> responseTrackList = this.DEEZER_API_REQUESTER.makeApiRequest(trackListURL);
        Map<String, Object> trackListMap = getMapFrom(responseTrackList);
        List<Map<String, Object>> tracksList = (List<Map<String, Object>>) trackListMap.get("data");

        String albumListURL = url + "/albums";
        ResponseEntity<String> responseAlbumList = this.DEEZER_API_REQUESTER.makeApiRequest(albumListURL);
        Map<String, Object> albumListMap = getMapFrom(responseAlbumList);
        List<Map<String, Object>> albumList = (List<Map<String, Object>>) albumListMap.get("data");


        ArtistTO artistTO = Mapper.toArtistTO(artistMap, tracksList, albumList);


        return artistTO;


    }

    public TrackTO getTrack(long id) {
        String url = DEEZER_API_URL + "track/" + id;

        ResponseEntity<String> responseArtist = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        Map<String, Object> trackMap = getMapFrom(responseArtist);
        TrackTO trackTO = Mapper.toTrackTO(trackMap);

        return trackTO;
    }

    public List<TrackTO> getTrackSearch(String query) {
        String url = DEEZER_API_URL + "search?q=" + query;

        ResponseEntity<String> responseTracks = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        Map<String, Object> jsonMap = getMapFrom(responseTracks);

        List<Map<String, Object>> tracksList = (List<Map<String, Object>>) jsonMap.get("data");
        List<TrackTO> trackTOs = new ArrayList<>();

        for (Map<String, Object> trackMap : tracksList) {
            TrackTO trackTO = Mapper.toTrackTO(trackMap);
            trackTOs.add(trackTO);
        }

        return trackTOs;
    }

    public List<ArtistTO> getArtists(String query) {
        String url = DEEZER_API_URL + "search/artist?q=" + query;

        ResponseEntity<String> responseArtists = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        Map<String, Object> jsonMap = getMapFrom(responseArtists);

        List<Map<String, Object>> artistList = (List<Map<String, Object>>) jsonMap.get("data");
        List<ArtistTO> artistTOs = new ArrayList<>();

        for (Map<String, Object> artistMap : artistList) {
            ArtistTO artistTO = Mapper.toArtistTO(artistMap);
            artistTOs.add(artistTO);
        }

        return artistTOs;
    }

    public List<PlaylistTO> getPlaylists(String query) {
        String url = DEEZER_API_URL + "search/playlist?q=" + query;

        ResponseEntity<String> responsePlaylist = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        Map<String, Object> playlistMap = getMapFrom(responsePlaylist);

        List<Map<String, Object>> playlistList = (List<Map<String, Object>>) playlistMap.get("data");
        List<PlaylistTO> playlistTOs = new ArrayList<>();

        for (Map<String, Object> playlist : playlistList) {
            PlaylistTO playlistTO = Mapper.toPlaylistTO(playlist);
            playlistTOs.add(playlistTO);
        }

        return playlistTOs;
    }

    public List<AlbumTO> getAlbums(String query) {
        String url = DEEZER_API_URL + "search/album?q=" + query;

        ResponseEntity<String> responseAlbums = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        Map<String, Object> jsonMap = getMapFrom(responseAlbums);
        List<Map<String, Object>> albumList = (List<Map<String, Object>>) jsonMap.get("data");
        List<AlbumTO> albumTOs = new ArrayList<>();

        for (Map<String, Object> albumMap : albumList) {
            AlbumTO albumTO = Mapper.toAlbumTO(albumMap);
            albumTOs.add(albumTO);
        }

        return albumTOs;
    }

    private Map<String, Object> getMapFrom(ResponseEntity<String> responseEntity) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(responseEntity.getBody(), new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
