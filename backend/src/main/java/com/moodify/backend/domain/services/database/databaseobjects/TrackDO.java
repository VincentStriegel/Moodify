package com.moodify.backend.domain.services.database.databaseobjects;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tracks")
@Getter
@Setter
public class TrackDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private String preview;

    @Column(nullable = false)
    private String release_date;

    @Column(nullable = false)
    private long artist_id_deezer;

    @Column(nullable = false)
    private String artist_name_deezer;

    @Column(nullable = false)
    private String album_cover_big_deezer;

    @Column(nullable = false)
    private String album_cover_small_deezer;

    public TrackDO(String title,
                   int duration,
                   String preview,
                   String release_date,
                   long artist_id_deezer,
                   String artist_name_deezer,
                   String album_cover_big_deezer,
                   String album_cover_small_deezer) {
        this.title = title;
        this.duration = duration;
        this.preview = preview;
        this.release_date = release_date;
        this.artist_id_deezer = artist_id_deezer;
        this.artist_name_deezer = artist_name_deezer;
        this.album_cover_big_deezer = album_cover_big_deezer;
        this.album_cover_small_deezer = album_cover_small_deezer;
    }

    public TrackDO() {

    }

}
