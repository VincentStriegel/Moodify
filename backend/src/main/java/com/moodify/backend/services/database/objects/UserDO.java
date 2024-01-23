package com.moodify.backend.services.database.objects;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a user in the Moodify application.
 * It is annotated with @Entity, indicating that it is a JPA entity.
 * The @Table annotation specifies the name of the database table that the entity is mapped to.
 * The class provides getter and setter methods for its properties.
 * It also provides a no-arg constructor that initializes the personalLibrary property with a new PersonalLibraryDO object.
 * The properties of the class represent the columns of the table.
 * The @Id annotation indicates that the id property is the primary key of the table.
 * The @GeneratedValue annotation specifies that the value of the id property is generated automatically.
 * The @Column annotation is used to specify the details of the column that a field is mapped to.
 * The @OneToOne annotation is used to establish a one-to-one relationship between the UserDO and PersonalLibraryDO, and UserDO and DiscographyDO entities.
 * The @JoinColumn annotation specifies the column that is used for joining the entities.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class UserDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private long id;


    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column (nullable = false, unique = true, length = 20)
    private String username;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_library_id", referencedColumnName = "id")
    private PersonalLibraryDO personalLibrary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "discography_id", referencedColumnName = "id")
    private DiscographyDO discography;

    public UserDO() {
        this.personalLibrary = new PersonalLibraryDO();
    }

}
