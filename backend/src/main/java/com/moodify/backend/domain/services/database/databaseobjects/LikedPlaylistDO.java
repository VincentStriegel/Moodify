package com.moodify.backend.domain.services.database.databaseobjects;

import jakarta.persistence.*;

@Entity
@Table(name = "liked_playlists")
public class LikedPlaylistDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String picture_small;

    @Column(nullable = false)
    private String picture_medium;

    @Column(nullable = false)
    private String picture_big;

    @Column(nullable = false)
    private int number_of_songs;


    /*@ManyToOne
    @JoinColumn(name = "user_id")
    private UserDO user;*/
}
