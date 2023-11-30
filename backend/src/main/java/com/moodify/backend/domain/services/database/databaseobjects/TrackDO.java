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
    private long id_deezer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private String preview;

    @Column()
    private String release_date;

    @Column(nullable = false)
    private long artist_id_deezer;

    @Column(nullable = false)
    private String artist_name_deezer;

    @Column(nullable = false)
    private String album_cover_big_deezer;

    @Column(nullable = false)
    private String album_cover_small_deezer;


    public TrackDO() {

    }

}
