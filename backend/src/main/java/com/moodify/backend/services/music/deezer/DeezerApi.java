package com.moodify.backend.services.music.deezer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodify.backend.api.transferobjects.*;
import com.moodify.backend.api.util.Source;
import com.moodify.backend.services.music.ApiService;
import com.moodify.backend.services.music.util.TransferObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class implements the ApiService interface and provides the functionality to interact with the Deezer API.
 * It contains methods for fetching playlists, albums, artists, and tracks from the Deezer API by their IDs.
 * It also contains methods for searching for tracks, artists, playlists, and albums on the Deezer API based on a search query.
 * Each method returns a transfer object containing the fetched or searched data.
 * The class is annotated with @Component to indicate that it is an autodetectable bean for Spring.
 */
@Component
public class DeezerApi implements ApiService {
    private final DeezerApiRequester DEEZER_API_REQUESTER;
    private final String DEEZER_API_URL = "https://api.deezer.com/";

    private final String DELIMETER = "&";

    private final  String LIMIT = "limit=";

    private final Source SOURCE = Source.DEEZER;

    @Autowired
    public DeezerApi(DeezerApiRequester DEEZER_API_REQUESTER) {
        this.DEEZER_API_REQUESTER = DEEZER_API_REQUESTER;
    }

    /**
     * Fetches a playlist from the Deezer API.
     * @param id The ID of the playlist to fetch.
     * @return A PlaylistTO object containing the playlist data.
     */
    public PlaylistTO getPlaylist(long id) {
        String url = DEEZER_API_URL + "playlist/" + id;

        ResponseEntity<String> responsePlaylist = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        Map<String, Object> playlistMap = getMapFrom(responsePlaylist);

        String trackListURL = (String) playlistMap.get("tracklist");
        ResponseEntity<String> responseTrackList = this.DEEZER_API_REQUESTER.makeApiRequest(trackListURL);
        Map<String, Object> trackListMap = getMapFrom(responseTrackList);
        List<Map<String, Object>> tracksList = (List<Map<String, Object>>) trackListMap.get("data");

        PlaylistTO playlistTO = TransferObjectMapper.toPlaylistTO(playlistMap, tracksList);
        playlistTO.setSource(SOURCE);

        return playlistTO;
    }

    /**
     * Fetches an album from the Deezer API.
     * @param id The ID of the album to fetch.
     * @return An AlbumTO object containing the album data.
     */
    public AlbumTO getAlbum(long id) {
        String url = DEEZER_API_URL + "album/" + id;

        ResponseEntity<String> responseAlbum = this.DEEZER_API_REQUESTER.makeApiRequest(url);

        Map<String, Object> albumMap = getMapFrom(responseAlbum);


        String trackListURL = (String) albumMap.get("tracklist");
        ResponseEntity<String> responseTrackList = this.DEEZER_API_REQUESTER.makeApiRequest(trackListURL);
        Map<String, Object> trackListMap = getMapFrom(responseTrackList);
        List<Map<String, Object>> tracksList = (List<Map<String, Object>>) trackListMap.get("data");


        AlbumTO albumTO = TransferObjectMapper.toAlbumTO(albumMap, tracksList);
        albumTO.setSource(SOURCE);
        return albumTO;
    }

    /**
     * Fetches an artist from the Deezer API.
     * @param id The ID of the artist to fetch.
     * @return An ArtistTO object containing the artist data.
     */
    public ArtistTO getArtist(long id) {
        String url = DEEZER_API_URL + "artist/" + id;

        ResponseEntity<String> responseArtist = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        Map<String, Object> artistMap = getMapFrom(responseArtist);

        String trackListURL = (String) artistMap.get("tracklist");
        ResponseEntity<String> responseTrackList = this.DEEZER_API_REQUESTER.makeApiRequest(trackListURL + "0");
        Map<String, Object> trackListMap = getMapFrom(responseTrackList);
        List<Map<String, Object>> tracksList = (List<Map<String, Object>>) trackListMap.get("data");

        String albumListURL = url + "/albums";
        ResponseEntity<String> responseAlbumList = this.DEEZER_API_REQUESTER.makeApiRequest(albumListURL);
        Map<String, Object> albumListMap = getMapFrom(responseAlbumList);
        List<Map<String, Object>> albumList = (List<Map<String, Object>>) albumListMap.get("data");


        ArtistTO artistTO = TransferObjectMapper.toArtistTO(artistMap, tracksList, albumList);
        artistTO.setSource(SOURCE);

        return artistTO;

    }

    /**
     * Fetches a track from the Deezer API.
     * @param id The ID of the track to fetch.
     * @return A TrackTO object containing the track data.
     */
    public TrackTO getTrack(long id) {
        String url = DEEZER_API_URL + "track/" + id;

        ResponseEntity<String> responseArtist = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        Map<String, Object> trackMap = getMapFrom(responseArtist);
        TrackTO trackTO = TransferObjectMapper.toTrackTO(trackMap);
        trackTO.setSource(SOURCE);
        return trackTO;
    }

    /**
     * Searches for tracks on the Deezer API.
     * @param query The search query.
     * @param limit The maximum number of results to return.
     * @return A list of TrackTO objects matching the search query.
     */
    public List<TrackTO> getTrackSearch(String query, long limit) {
        String url = DEEZER_API_URL + "search?q=" + query + DELIMETER + LIMIT + limit;

        ResponseEntity<String> responseTracks = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        Map<String, Object> jsonMap = getMapFrom(responseTracks);

        List<Map<String, Object>> tracksList = (List<Map<String, Object>>) jsonMap.get("data");
        List<TrackTO> trackTOs = new ArrayList<>();

        for (Map<String, Object> trackMap : tracksList) {
            TrackTO trackTO = TransferObjectMapper.toTrackTO(trackMap);
            trackTOs.add(trackTO);
        }
        trackTOs.forEach(track -> track.setSource(SOURCE));
        return trackTOs;
    }

    /**
     * Searches for artists on the Deezer API.
     * @param query The search query.
     * @param limit The maximum number of results to return.
     * @return A list of ArtistTO objects matching the search query.
     */
    public List<ArtistTO> getArtists(String query, long limit) {
        String url = DEEZER_API_URL + "search/artist?q=" + query + DELIMETER + LIMIT + limit;

        ResponseEntity<String> responseArtists = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        Map<String, Object> jsonMap = getMapFrom(responseArtists);

        List<Map<String, Object>> artistList = (List<Map<String, Object>>) jsonMap.get("data");
        List<ArtistTO> artistTOs = new ArrayList<>();

        for (Map<String, Object> artistMap : artistList) {
            ArtistTO artistTO = TransferObjectMapper.toArtistTO(artistMap);
            artistTOs.add(artistTO);
        }
        artistTOs.forEach(artistTO -> artistTO.setSource(SOURCE));

        return artistTOs;
    }

    /**
     * Searches for playlists on the Deezer API.
     * @param query The search query.
     * @param limit The maximum number of results to return.
     * @return A list of PlaylistTO objects matching the search query.
     */
    public List<PlaylistTO> getPlaylists(String query, long limit) {
        String url = DEEZER_API_URL + "search/playlist?q=" + query + DELIMETER + LIMIT + limit;

        ResponseEntity<String> responsePlaylist = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        Map<String, Object> playlistMap = getMapFrom(responsePlaylist);

        List<Map<String, Object>> playlistList = (List<Map<String, Object>>) playlistMap.get("data");
        List<PlaylistTO> playlistTOs = new ArrayList<>();

        for (Map<String, Object> playlist : playlistList) {
            PlaylistTO playlistTO = TransferObjectMapper.toPlaylistTO(playlist);
            playlistTOs.add(playlistTO);
        }
        playlistTOs.forEach(playlistTO -> playlistTO.setSource(SOURCE));

        return playlistTOs;
    }

    /**
     * Searches for albums on the Deezer API.
     * @param query The search query.
     * @param limit The maximum number of results to return.
     * @return A list of AlbumTO objects matching the search query.
     */
    public List<AlbumTO> getAlbums(String query, long limit) {
        String url = DEEZER_API_URL + "search/album?q=" + query + DELIMETER + LIMIT + limit;

        ResponseEntity<String> responseAlbums = this.DEEZER_API_REQUESTER.makeApiRequest(url);
        Map<String, Object> jsonMap = getMapFrom(responseAlbums);
        List<Map<String, Object>> albumList = (List<Map<String, Object>>) jsonMap.get("data");
        List<AlbumTO> albumTOs = new ArrayList<>();

        for (Map<String, Object> albumMap : albumList) {
            AlbumTO albumTO = TransferObjectMapper.toAlbumTO(albumMap);
            albumTOs.add(albumTO);
        }
        albumTOs.forEach(albumTO -> albumTO.setSource(SOURCE));

        return albumTOs;
    }

    /**
     * Converts a ResponseEntity to a Map.
     * @param responseEntity The ResponseEntity to convert.
     * @return A Map containing the data from the ResponseEntity.
     */
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
