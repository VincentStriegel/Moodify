package com.moodify.backend.api.transferobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonalLibraryTO implements Serializable, Cloneable {
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

    @Override
    public PersonalLibraryTO clone() {
        try {
            PersonalLibraryTO clone = (PersonalLibraryTO) super.clone();
            for (TrackTO trackTO: this.likedTracks) {
                clone.likedTracks.add(trackTO.clone());
            }
            for (ArtistTO artistTO: this.likedArtists) {
                clone.likedArtists.add(artistTO.clone());
            }
            for (PlaylistTO playlistTO: this.likedPlaylists) {
                clone.likedPlaylists.add(playlistTO.clone());
            }
            for (AlbumTO albumTO: this.likedAlbums) {
                clone.likedAlbums.add(albumTO.clone());
            }
            for (PlaylistTO playlistTO: this.customPlaylists) {
                clone.customPlaylists.add(playlistTO.clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
