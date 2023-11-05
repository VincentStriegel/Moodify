package com.moodify.backend.domain.services;

import com.moodify.backend.api.transferobjects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Mapper {
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

    public static ArtistTO toArtistTO(Map<String, Object> artistMap, List<Map<String, Object>> tracksList, List<Map<String, Object>> albumList) {
        ArtistTO artistTO = toArtistTO(artistMap, tracksList);

        List<AlbumTO> artistAlbumlist = new ArrayList<AlbumTO>();
        for (Map<String, Object> album : albumList) {
            AlbumTO albumTO = Mapper.toAlbumTO(album);
            artistAlbumlist.add(albumTO);
        }
        artistTO.setAlbumTOList(artistAlbumlist);

        return artistTO;
    }

    public static ArtistTO toArtistTO(Map<String, Object> artistMap, List<Map<String, Object>> tracksList) {
        ArtistTO artistTO = toArtistTO(artistMap);
        List<TrackTO> artistTracklist = new ArrayList<TrackTO>();
        for (Map<String, Object> track : tracksList) {
            TrackTO trackTO = Mapper.toTrackTO(track);
            artistTracklist.add(trackTO);
        }
        artistTO.setTrackTOList(artistTracklist);

        return artistTO;
    }

    public static ArtistTO toArtistTO(Map<String, Object> artistMap) {
        ArtistTO artistTO = new ArtistTO();
        artistTO.setId(((Number) artistMap.get("id")).intValue());
        artistTO.setName((String) artistMap.get("name"));
        artistTO.setPicture_small((String) artistMap.get("picture_small"));
        artistTO.setPicture_big((String) artistMap.get("picture_big"));


        return artistTO;
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

    public static AlbumTO toAlbumTO(Map<String, Object> albumMap, List<Map<String, Object>> trackList) {

        AlbumTO albumTO = toAlbumTO(albumMap);

        List<TrackTO> albumTracklist = new ArrayList<TrackTO>();
        for (Map<String, Object> track : trackList) {
            TrackTO trackTO = Mapper.toTrackTO(track);
            albumTracklist.add(trackTO);

        }
        albumTO.setTrackTOList(albumTracklist);

        return albumTO;
    }

    public static PlaylistTO toPlaylistTO(Map<String, Object> playlistMap) {
        PlaylistTO playlistTO = new PlaylistTO();

        playlistTO.setId(((Number) playlistMap.get("id")).intValue());
        playlistTO.setTitle((String) playlistMap.get("title"));
        playlistTO.setPicture_small((String) playlistMap.get("picture_small"));
        playlistTO.setPicture_medium((String) playlistMap.get("picture_medium"));
        playlistTO.setPicture_big((String) playlistMap.get("picture_big"));
        playlistTO.setNumber_of_songs(((Number) playlistMap.get("nb_tracks")).intValue());


        return playlistTO;
    }

    public static PlaylistTO toPlaylistTO(Map<String, Object> playlistMap, List<Map<String, Object>> tracklist) {
        PlaylistTO playlistTO = toPlaylistTO(playlistMap);

        List<TrackTO> playlistTracklist = new ArrayList<TrackTO>();
        for (Map<String, Object> track : tracklist) {
            TrackTO trackTO = Mapper.toTrackTO(track);
            playlistTracklist.add(trackTO);
        }
        playlistTO.setTrackTOList(playlistTracklist);

        return playlistTO;
    }

}
