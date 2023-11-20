package com.moodify.backend.domain.services.database.databaseobjects;

import jakarta.persistence.*;

@Entity
@Table(name = "liked_tracks")
public class LikedTrackDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(nullable = false, length = 64)
    private String title;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private String preview;

    @Column(nullable = false)
    private String release_date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDO user;

}
