package com.moodify.backend.services.database.objects;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a discography in the application.
 * It is annotated with @Entity, indicating that it is a JPA entity.
 * The @Table annotation specifies the name of the database table that the entity is mapped to.
 * The class provides getter and setter methods for its properties.
 * It also provides a no-arg constructor that initializes the singles property with an empty list.
 * The properties of the class represent the columns of the table.
 * The @Id annotation indicates that the id property is the primary key of the table.
 * The @GeneratedValue annotation specifies that the value of the id property is generated automatically.
 * The @Column annotation is used to specify the details of the column that a field is mapped to.
 * The @OneToMany annotation is used to establish a one-to-many relationship between the DiscographyDO and MoodifySingleDO entities.
 * The @JoinColumn annotation specifies the column that is used for joining the two entities.
 */
@Entity
@Table(name = "discographies")
@Getter
@Setter
public class DiscographyDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "discography_id")
    private List<MoodifySingleDO> singles;

    @Column(nullable = false)
    private String picture_small;

    @Column(nullable = false)
    private String picture_big;

    public DiscographyDO() {
        this.singles = new ArrayList<MoodifySingleDO>();
    }
}
