package com.moodify.backend.services.database.objects;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents an album in the application.
 * It is annotated with @Entity, indicating that it is a JPA entity.
 * The @Table annotation specifies the name of the database table that the entity is mapped to.
 * The class provides getter and setter methods for its properties.
 * It also provides a no-arg constructor and a constructor that initializes all properties except for the ID, which is generated automatically.
 * The properties of the class represent the columns of the table.
 * The @Id annotation indicates that the id property is the primary key of the table.
 * The @GeneratedValue annotation specifies that the value of the id property is generated automatically.
 * The @Column annotation is used to specify the details of the column that a field is mapped to.
 */
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
    private long album_id_source;

    public AlbumDO() {

    }

    public AlbumDO(String title, String cover_small, String cover_big, String release_date, int number_of_songs, long album_id_source) {
        this.title = title;
        this.cover_small = cover_small;
        this.cover_big = cover_big;
        this.release_date = release_date;
        this.number_of_songs = number_of_songs;
        this.album_id_source = album_id_source;
    }
}
