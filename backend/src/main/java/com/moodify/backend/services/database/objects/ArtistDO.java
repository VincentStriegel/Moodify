package com.moodify.backend.services.database.objects;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "liked_artists")
@Getter
@Setter
public class ArtistDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(nullable = false)
    private int nb_fans;
    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false)
    private String picture_small;

    @Column(nullable = false)
    private String picture_big;

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private long artist_id_deezer;

    public ArtistDO(int nb_fans, String name, String picture_small, String picture_big, long artist_id_deezer) {

        this.nb_fans = nb_fans;
        this.name = name;
        this.picture_small = picture_small;
        this.picture_big = picture_big;
        this.artist_id_deezer = artist_id_deezer;
    }

    public ArtistDO() {

    }
}
