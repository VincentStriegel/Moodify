package com.moodify.backend.services.database.objects;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents an artist in the application.
 * It is annotated with @Entity, indicating that it is a JPA entity.
 * The @Table annotation specifies the name of the database table that the entity is mapped to.
 * The class provides getter and setter methods for its properties.
 * It also provides a no-arg constructor and a constructor that initializes all properties except for the ID, which is generated automatically.
 * The properties of the class represent the columns of the table.
 * The @Id annotation indicates that the id property is the primary key of the table.
 * The @GeneratedValue annotation specifies that the value of the id property is generated automatically.
 * The @Column annotation is used to specify the details of the column that a field is mapped to.
 */
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
    private long artist_id_source;

    public ArtistDO(int nb_fans, String name, String picture_small, String picture_big, long artist_id_source) {

        this.nb_fans = nb_fans;
        this.name = name;
        this.picture_small = picture_small;
        this.picture_big = picture_big;
        this.artist_id_source = artist_id_source;
    }

    public ArtistDO() {

    }
}
