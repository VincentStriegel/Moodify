package com.moodify.backend.services.music.util;

import com.moodify.backend.api.transferobjects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class provides utility methods for converting Map objects, which represent music data fetched from the Deezer API,
 * into transfer objects (TOs). These TOs are then used to transfer data from the API to the application.
 * The class contains methods for converting Map objects representing tracks, artists, albums, and playlists into their corresponding TOs.
 * Some methods also take a list of Map objects representing tracks or albums and include these in the created TOs.
 * All methods are static, meaning they can be called without creating an instance of the class.
 */
public class TransferObjectMapper {

    /**
     * Converts a Map object representing a track into a TrackTO object.
     * @param trackMap The Map object representing a track.
     * @return A TrackTO object containing the track data.
     */
    public static TrackTO toTrackTO(Map<String, Object> trackMap) {
        TrackTO trackTO = new TrackTO();
        trackTO.setId(((Number) trackMap.get("id")).longValue());
        trackTO.setTitle((String) trackMap.get("title"));
        trackTO.setDuration(((Number) trackMap.get("duration")).intValue());
        trackTO.setPreview((String) trackMap.get("preview"));
        trackTO.setRelease_date((String) trackMap.get("release_date"));

        if (trackMap.containsKey("artist")) {
            trackTO.setArtist(toArtistTO((Map<String, Object>) trackMap.get("artist")));
        }

        if (trackMap.containsKey("album")) {
            trackTO.setAlbum(toAlbumTO((Map<String, Object>) trackMap.get("album")));
        }


        return trackTO;
    }

    /**
     * Converts a Map object representing an artist and a list of Map objects representing tracks and albums into an ArtistTO object.
     * @param artistMap The Map object representing an artist.
     * @param tracksList The list of Map objects representing tracks.
     * @param albumList The list of Map objects representing albums.
     * @return An ArtistTO object containing the artist data.
     */
    public static ArtistTO toArtistTO(Map<String, Object> artistMap, List<Map<String, Object>> tracksList, List<Map<String, Object>> albumList) {
        ArtistTO artistTO = toArtistTO(artistMap, tracksList);

        List<AlbumTO> artistAlbumlist = new ArrayList<AlbumTO>();
        for (Map<String, Object> album : albumList) {
            AlbumTO albumTO = TransferObjectMapper.toAlbumTO(album);
            artistAlbumlist.add(albumTO);
        }
        artistTO.setAlbumTOList(artistAlbumlist);

        return artistTO;
    }

    /**
     * Converts a Map object representing an artist and a list of Map objects representing tracks into an ArtistTO object.
     * @param artistMap The Map object representing an artist.
     * @param tracksList The list of Map objects representing tracks.
     * @return An ArtistTO object containing the artist data.
     */
    public static ArtistTO toArtistTO(Map<String, Object> artistMap, List<Map<String, Object>> tracksList) {
        ArtistTO artistTO = toArtistTO(artistMap);
        List<TrackTO> artistTracklist = new ArrayList<TrackTO>();
        for (Map<String, Object> track : tracksList) {
            TrackTO trackTO = TransferObjectMapper.toTrackTO(track);
            artistTracklist.add(trackTO);
        }
        artistTO.setTrackTOList(artistTracklist);

        return artistTO;
    }

    /**
     * Converts a Map object representing an artist into an ArtistTO object.
     * @param artistMap The Map object representing an artist.
     * @return An ArtistTO object containing the artist data.
     */
    public static ArtistTO toArtistTO(Map<String, Object> artistMap) {
        ArtistTO artistTO = new ArtistTO();
        artistTO.setId(((Number) artistMap.get("id")).intValue());
        artistTO.setName((String) artistMap.get("name"));
        artistTO.setPicture_small((String) artistMap.get("picture_small"));
        artistTO.setPicture_big((String) artistMap.get("picture_big"));
        // TODO
        if (artistMap.get("nb_fan") != null) {
            artistTO.setNb_fans(((Number) artistMap.get("nb_fan")).intValue());
        }

        return artistTO;
    }

    /**
     * Converts a Map object representing an album into an AlbumTO object.
     * @param albumMap The Map object representing an album.
     * @return An AlbumTO object containing the album data.
     */
    public static AlbumTO toAlbumTO(Map<String, Object> albumMap) {
        AlbumTO albumTO = new AlbumTO();

        albumTO.setId(((Number) albumMap.get("id")).intValue());
        albumTO.setTitle((String) albumMap.get("title"));
        albumTO.setCover_small((String) albumMap.get("cover_small"));
        albumTO.setCover_big((String) albumMap.get("cover_big"));
        albumTO.setRelease_date((String) albumMap.get("release_date"));
        if (albumMap.get("nb_tracks") != null) {
            albumTO.setNumber_of_songs(((Number) albumMap.get("nb_tracks")).intValue());
        }

        return albumTO;
    }

    /**
     * Converts a Map object representing an album and a list of Map objects representing tracks into an AlbumTO object.
     * @param albumMap The Map object representing an album.
     * @param trackList The list of Map objects representing tracks.
     * @return An AlbumTO object containing the album data.
     */
    public static AlbumTO toAlbumTO(Map<String, Object> albumMap, List<Map<String, Object>> trackList) {

        AlbumTO albumTO = toAlbumTO(albumMap);

        List<TrackTO> albumTracklist = new ArrayList<TrackTO>();
        for (Map<String, Object> track : trackList) {
            TrackTO trackTO = TransferObjectMapper.toTrackTO(track);
            albumTracklist.add(trackTO);

        }
        albumTO.setTrackTOList(albumTracklist);

        return albumTO;
    }

    /**
     * Converts a Map object representing a playlist into a PlaylistTO object.
     * @param playlistMap The Map object representing a playlist.
     * @return A PlaylistTO object containing the playlist data.
     */
    public static PlaylistTO toPlaylistTO(Map<String, Object> playlistMap) {
        PlaylistTO playlistTO = new PlaylistTO();
        playlistTO.setId(((Number) playlistMap.get("id")).longValue());
        playlistTO.setTitle((String) playlistMap.get("title"));
        playlistTO.setPicture_small((String) playlistMap.get("picture_small"));
        playlistTO.setPicture_medium((String) playlistMap.get("picture_medium"));
        playlistTO.setPicture_big((String) playlistMap.get("picture_big"));
        playlistTO.setNumber_of_songs(((Number) playlistMap.get("nb_tracks")).intValue());


        return playlistTO;
    }

    /**
     * Converts a Map object representing a playlist and a list of Map objects representing tracks into a PlaylistTO object.
     * @param playlistMap The Map object representing a playlist.
     * @param tracklist The list of Map objects representing tracks.
     * @return A PlaylistTO object containing the playlist data.
     */
    public static PlaylistTO toPlaylistTO(Map<String, Object> playlistMap, List<Map<String, Object>> tracklist) {
        PlaylistTO playlistTO = toPlaylistTO(playlistMap);

        List<TrackTO> playlistTracklist = new ArrayList<TrackTO>();
        for (Map<String, Object> track : tracklist) {
            TrackTO trackTO = TransferObjectMapper.toTrackTO(track);
            playlistTracklist.add(trackTO);
        }
        playlistTO.setTrackTOList(playlistTracklist);

        return playlistTO;
    }

}
