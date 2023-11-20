package com.moodify.backend.domain.services.database.databaseobjects;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column (nullable = false, unique = true, length = 20)
    private String username;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    List<LikedTrackDO> likedTracks;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    List<LikedArtistDO> likedArtists;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    List<LikedAlbumDO> likedAlbums;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    List<LikedPlaylistDO> likedPlaylists;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    List<CustomPlaylistDO> customPlaylists;


    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public UserDO() {
        this.likedTracks = new ArrayList<LikedTrackDO>();
        this.likedArtists = new ArrayList<LikedArtistDO>();
        this.likedAlbums = new ArrayList<LikedAlbumDO>();
        this.likedPlaylists = new ArrayList<LikedPlaylistDO>();
        this.customPlaylists = new ArrayList<CustomPlaylistDO>();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<LikedTrackDO> getLikedTracks() {
        return likedTracks;
    }

    public void setLikedTracks(List<LikedTrackDO> likedTracks) {
        this.likedTracks = likedTracks;
    }

    public List<LikedArtistDO> getLikedArtists() {
        return likedArtists;
    }

    public void setLikedArtists(List<LikedArtistDO> likedArtists) {
        this.likedArtists = likedArtists;
    }

    public List<LikedAlbumDO> getLikedAlbums() {
        return likedAlbums;
    }

    public void setLikedAlbums(List<LikedAlbumDO> likedAlbums) {
        this.likedAlbums = likedAlbums;
    }

    public List<LikedPlaylistDO> getLikedPlaylists() {
        return likedPlaylists;
    }

    public void setLikedPlaylists(List<LikedPlaylistDO> likedPlaylists) {
        this.likedPlaylists = likedPlaylists;
    }

    public List<CustomPlaylistDO> getCustomPlaylists() {
        return customPlaylists;
    }

    public void setCustomPlaylists(List<CustomPlaylistDO> customPlaylists) {
        this.customPlaylists = customPlaylists;
    }
}
