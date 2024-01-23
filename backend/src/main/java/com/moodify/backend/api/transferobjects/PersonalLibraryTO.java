package com.moodify.backend.api.transferobjects;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a personal library in the Moodify application.
 * It is a transfer object (TO), which is used to send data across processes or across the network.
 * The class provides getter and setter methods for its properties.
 * It also provides a no-arg constructor that initializes the likedTracks, likedArtists, likedAlbums, and customPlaylists properties with empty lists.
 * The properties of the class represent the data that is transferred.
 * The likedTracks property represents the list of tracks liked by the user.
 * The likedArtists property represents the list of artists liked by the user.
 * The likedAlbums property represents the list of albums liked by the user.
 * The customPlaylists property represents the list of custom playlists created by the user.
 */
@Getter
@Setter
public class PersonalLibraryTO implements Serializable {

    private List<TrackTO>  likedTracks;
    private List<ArtistTO>  likedArtists;
    private List<AlbumTO> likedAlbums;
    private List<PlaylistTO> customPlaylists;

    public PersonalLibraryTO() {
        this.likedTracks = new ArrayList<TrackTO>();
        this.likedArtists = new ArrayList<ArtistTO>();
        this.likedAlbums = new ArrayList<AlbumTO>();
        this.customPlaylists = new ArrayList<PlaylistTO>();
    }

}
