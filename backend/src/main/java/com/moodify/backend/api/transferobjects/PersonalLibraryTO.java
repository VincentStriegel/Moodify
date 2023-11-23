package com.moodify.backend.api.transferobjects;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PersonalLibraryTO implements Serializable {

    private List<TrackTO>  likedTracks;
    private List<ArtistTO>  likedArtists;
    private List<AlbumTO> likedAlbums;
    private List<PlaylistTO> likedPlaylists;
    private List<PlaylistTO> customPlaylists;

    public PersonalLibraryTO() {
        this.likedTracks = new ArrayList<TrackTO>();
        this.likedArtists = new ArrayList<ArtistTO>();
        this.likedAlbums = new ArrayList<AlbumTO>();
        this.likedPlaylists = new ArrayList<PlaylistTO>();
        this.customPlaylists = new ArrayList<PlaylistTO>();
    }

}
