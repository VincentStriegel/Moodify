package com.moodify.backend.services.database.objects;

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
    private long id_source;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private String preview;

    @Column()
    private String release_date;

    @Column(nullable = false)
    private long artist_id_source;

    @Column(nullable = false)
    private String artist_name;

    @Column(nullable = false)
    private String cover_big;

    @Column(nullable = false)
    private String cover_small;


    public TrackDO() {

    }

}
