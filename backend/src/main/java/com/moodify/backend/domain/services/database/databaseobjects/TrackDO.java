package com.moodify.backend.domain.services.database.databaseobjects;

import jakarta.persistence.*;

@Entity
@Table(name = "tracks")
public class TrackDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    private String artist_id_deezer;

    @Column(nullable = false)
    private String artist_name_deezer;

    @Column(nullable = false)
    private String album_cover_big_deezer;

    @Column(nullable = false)
    private String album_cover_small_deezer;

    //Track : title, duration, artist id , artist name , ablum cover_big, album_cover_small

    public TrackDO(String title,
                   int duration,
                   String preview,
                   String release_date,
                   String artist_id_deezer,
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
}
