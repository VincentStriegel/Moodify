package com.moodify.backend.domain.services;

import com.moodify.backend.api.transferobjects.*;

import java.util.Map;

public class Mapper {
    public static TrackTO toTrackTO(Map<String, Object> trackMap) {
        TrackTO trackTO = new TrackTO();
        Number trackId = (Number) trackMap.get("id");
        trackTO.setId(trackId.longValue());
        trackTO.setTitle((String) trackMap.get("title"));
        trackTO.setDuration(((Number) trackMap.get("duration")).intValue());
        trackTO.setPreview((String) trackMap.get("preview"));
        trackTO.setRelease_date((String) trackMap.get("release_date"));
        trackTO.setAlbum(toAlbumTO((Map<String, Object>) trackMap.get("album")));
        trackTO.setArtist(toArtistTO((Map<String, Object>) trackMap.get("artist")));

        return trackTO;
    }

    public static AlbumTO toAlbumTO(Map<String, Object> albumMap) {
        AlbumTO albumTO = new AlbumTO();
        albumTO.setId(((Number) albumMap.get("id")).intValue());
        albumTO.setTitle((String) albumMap.get("title"));
        albumTO.setCover_small((String) albumMap.get("cover_small"));
        albumTO.setCover_big((String) albumMap.get("cover_big"));
        albumTO.setRelease_date((String) albumMap.get("release_date"));

        return albumTO;
    }

    public static ArtistTO toArtistTO(Map<String, Object> artistMap) {
        ArtistTO artistTO = new ArtistTO();
        artistTO.setId(((Number) artistMap.get("id")).intValue());
        artistTO.setName((String) artistMap.get("name"));
        artistTO.setPicture_small((String) artistMap.get("picture_small"));
        artistTO.setPicture_big((String) artistMap.get("picture_big"));


        return artistTO;
    }

    public static PlaylistTO toPlaylistTO(Map<String, Object> playlistMap) {
        PlaylistTO playlistTO = new PlaylistTO();

        playlistTO.setId(((Number) playlistMap.get("id")).intValue());
        playlistTO.setTitle((String) playlistMap.get("title"));
        playlistTO.setPicture_small((String) playlistMap.get("picture_small"));
        playlistTO.setPicture_medium((String) playlistMap.get("picture_medium"));
        playlistTO.setPicture_big((String) playlistMap.get("picture_big"));
        playlistTO.setNumber_of_songs(((Number) playlistMap.get("nb_tracks")).intValue());
        playlistTO.setTrack_list((String) playlistMap.get("tracklist"));

        return playlistTO;
    }

    public static UserTO toUserTO(Map<String, Object> artistMap) {
        UserTO userTO = new UserTO();
        userTO.setId(((Number) artistMap.get("id")).intValue());
        userTO.setName((String) artistMap.get("name"));
        userTO.setPicture_small((String) artistMap.get("picture_small"));
        userTO.setPicture_big((String) artistMap.get("picture_big"));


        return userTO;
    }
}
