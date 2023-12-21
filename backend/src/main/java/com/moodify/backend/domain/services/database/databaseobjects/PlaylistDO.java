package com.moodify.backend.domain.services.database.databaseobjects;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "playlists")
public class PlaylistDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(nullable = false, length = 20)
    private String title;

    @Column()
    private String picture_small;

    @Column()
    private String picture_medium;

    @Column()
    private String picture_big;

    @Column()
    private boolean isLikedTrackPlaylist = false;

    @Column()
    @Setter(AccessLevel.NONE)
    private int number_of_songs;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "playlist_id")
    private List<TrackDO> tracks;

    public PlaylistDO(String title, boolean isLikedTrackPlaylist) {
        this(title);
        this.isLikedTrackPlaylist = isLikedTrackPlaylist;
    }

    public PlaylistDO(String title) {
        this();
        this.title = title;
    }


    public PlaylistDO() {
        this.tracks = new ArrayList<TrackDO>();
    }

    public void incrementNumberOfSongs() {
        this.number_of_songs++;
    }

    public void decrementNumberOfSongs() {
        this.number_of_songs--;
    }
}
