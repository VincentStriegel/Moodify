package com.moodify.backend.services.database.objects;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a playlist in the Moodify application.
 * It is annotated with @Entity, indicating that it is a JPA entity.
 * The @Table annotation specifies the name of the database table that the entity is mapped to.
 * The class provides getter and setter methods for its properties.
 * It also provides a no-arg constructor that initializes the tracks property with an empty list,
 * and constructors that initialize the title and isLikedTrackPlaylist properties.
 * The properties of the class represent the columns of the table.
 * The @Id annotation indicates that the id property is the primary key of the table.
 * The @GeneratedValue annotation specifies that the value of the id property is generated automatically.
 * The @Column annotation is used to specify the details of the column that a field is mapped to.
 * The @OneToMany annotation is used to establish a one-to-many relationship between the PlaylistDO and TrackDO entities.
 * The @JoinColumn annotation specifies the column that is used for joining the two entities.
 * The class also provides methods for incrementing and decrementing the number of songs in the playlist.
 */
@Getter
@Setter
@Entity
@Table(name = "playlists")
public class PlaylistDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(nullable = false, length = 20)
    private String title;

    @Column()
    private String picture_small;

    @Column()
    private String picture_medium;

    @Column()
    private String picture_big;

    @Column()
    private boolean isLikedTrackPlaylist = false;

    @Column()
    @Setter(AccessLevel.NONE)
    private int number_of_songs;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "playlist_id")
    private List<TrackDO> tracks;

    public PlaylistDO(String title, boolean isLikedTrackPlaylist) {
        this(title);
        this.isLikedTrackPlaylist = isLikedTrackPlaylist;
    }

    public PlaylistDO(String title) {
        this();
        this.title = title;
    }


    public PlaylistDO() {
        this.tracks = new ArrayList<TrackDO>();
    }

    public void incrementNumberOfSongs() {
        this.number_of_songs++;
    }

    public void decrementNumberOfSongs() {
        this.number_of_songs--;
    }
}
