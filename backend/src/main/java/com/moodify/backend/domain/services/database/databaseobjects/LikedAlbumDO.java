package com.moodify.backend.domain.services.database.databaseobjects;

import jakarta.persistence.*;

@Entity
@Table(name = "liked_albums")
public class LikedAlbumDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(nullable = false)
    private int number_of_songs;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String cover_small;

    @Column(nullable = false)
    private String cover_big;

    @Column(nullable = false)
    private String release_date;

 /*   @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDO user;*/
}