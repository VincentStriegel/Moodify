package com.moodify.backend.domain.services.database.databaseobjects;

import jakarta.persistence.*;

@Entity
@Table(name = "liked_artists")
public class LikedArtistDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(nullable = false)
    private int number_of_songs;

    @Column(nullable = false)
    private int nb_fans;
    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false)
    private String picture_small;

    @Column(nullable = false)
    private String picture_big;

/*    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDO user;*/

}