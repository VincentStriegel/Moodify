package com.moodify.backend.domain.services.database.databaseobjects;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "custom_playlists")
public class CustomPlaylistDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = true)
    private String picture_small;

    @Column(nullable = true)
    private String picture_medium;

    @Column(nullable = true)
    private String picture_big;

    @Column(nullable = true)
    private int number_of_songs;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "playlist_id")
    List<TrackDO> tracks;


    public CustomPlaylistDO(String title) {
        this.title = title;
        this.tracks = new ArrayList<TrackDO>();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture_small() {
        return picture_small;
    }

    public void setPicture_small(String picture_small) {
        this.picture_small = picture_small;
    }

    public String getPicture_medium() {
        return picture_medium;
    }

    public void setPicture_medium(String picture_medium) {
        this.picture_medium = picture_medium;
    }

    public String getPicture_big() {
        return picture_big;
    }

    public void setPicture_big(String picture_big) {
        this.picture_big = picture_big;
    }

    public int getNumber_of_songs() {
        return number_of_songs;
    }

    public void setNumber_of_songs(int number_of_songs) {
        this.number_of_songs = number_of_songs;
    }

    public List<TrackDO> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackDO> tracks) {
        this.tracks = tracks;
    }
}
