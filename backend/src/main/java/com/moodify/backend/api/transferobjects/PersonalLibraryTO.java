package com.moodify.backend.api.transferobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonalLibraryTO implements Serializable {
    List<TrackTO> likedTracks;
    List<ArtistTO> likedArtists;
    List<AlbumTO> likedAlbums;
    List<PlaylistTO> likedPlaylists;
    List<PlaylistTO> customPlaylists;

    public PersonalLibraryTO(){
        this.likedTracks = new ArrayList<TrackTO>();
        this.likedArtists = new ArrayList<ArtistTO>();
        this.likedAlbums = new ArrayList<AlbumTO>();
        this.likedPlaylists = new ArrayList<PlaylistTO>();
        this.customPlaylists = new ArrayList<PlaylistTO>();
    }
    public List<TrackTO> getLikedTracks() {
        return likedTracks;
    }

    public void setLikedTracks(List<TrackTO> likedTracks) {
        this.likedTracks = likedTracks;
    }

    public List<ArtistTO> getLikedArtists() {
        return likedArtists;
    }

    public void setLikedArtists(List<ArtistTO> likedArtists) {
        this.likedArtists = likedArtists;
    }

    public List<AlbumTO> getLikedAlbums() {
        return likedAlbums;
    }

    public void setLikedAlbums(List<AlbumTO> likedAlbums) {
        this.likedAlbums = likedAlbums;
    }

    public List<PlaylistTO> getLikedPlaylists() {
        return likedPlaylists;
    }

    public void setLikedPlaylists(List<PlaylistTO> likedPlaylists) {
        this.likedPlaylists = likedPlaylists;
    }

    public List<PlaylistTO> getCustomPlaylists() {
        return customPlaylists;
    }

    public void setCustomPlaylists(List<PlaylistTO> customPlaylists) {
        this.customPlaylists = customPlaylists;
    }


}
