package com.moodify.backend.services.database.objects;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a track in the Moodify application.
 * It is annotated with @Entity, indicating that it is a JPA entity.
 * The @Table annotation specifies the name of the database table that the entity is mapped to.
 * The class provides getter and setter methods for its properties.
 * It also provides a no-arg constructor.
 * The properties of the class represent the columns of the table.
 * The @Id annotation indicates that the id property is the primary key of the table.
 * The @GeneratedValue annotation specifies that the value of the id property is generated automatically.
 * The @Column annotation is used to specify the details of the column that a field is mapped to.
 */
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
