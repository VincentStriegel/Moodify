package com.moodify.backend.domain.services.database.databaseobjects;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
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

    public UserDO() {
        this.personalLibrary = new PersonalLibraryDO();
    }

}
