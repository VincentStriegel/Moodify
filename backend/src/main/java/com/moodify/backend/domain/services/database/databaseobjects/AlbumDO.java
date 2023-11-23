package com.moodify.backend.domain.services.database.databaseobjects;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "liked_albums")
public class AlbumDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String cover_small;

    @Column(nullable = false)
    private String cover_big;

    @Column(nullable = false)
    private String release_date;

    @Column(nullable = false)
    private int number_of_songs;

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private long album_id_deezer;

    public AlbumDO() {

    }

    public AlbumDO(String title, String cover_small, String cover_big, String release_date, int number_of_songs, long album_id_deezer) {
        this.title = title;
        this.cover_small = cover_small;
        this.cover_big = cover_big;
        this.release_date = release_date;
        this.number_of_songs = number_of_songs;
        this.album_id_deezer = album_id_deezer;
    }
}
