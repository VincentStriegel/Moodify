package com.moodify.backend.services.database.objects;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a personal library in the Moodify application.
 * It is annotated with @Entity, indicating that it is a JPA entity.
 * The @Table annotation specifies the name of the database table that the entity is mapped to.
 * The class provides getter and setter methods for its properties.
 * It also provides a no-arg constructor that initializes the likedArtists, likedAlbums, and playlists properties with empty lists.
 * The properties of the class represent the columns of the table.
 * The @Id annotation indicates that the id property is the primary key of the table.
 * The @GeneratedValue annotation specifies that the value of the id property is generated automatically.
 * The @Column annotation is used to specify the details of the column that a field is mapped to.
 * The @OneToMany annotation is used to establish a one-to-many relationship between the PersonalLibraryDO and ArtistDO, AlbumDO, and PlaylistDO entities.
 * The @JoinColumn annotation specifies the column that is used for joining the entities.
 * The class also provides methods for getting custom playlists and the liked tracks playlist.
 */
@Entity
@Table(name = "personal_libraries")
@Getter
@Setter
public class PersonalLibraryDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private long id;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "personal_library_id")
    private List<ArtistDO> likedArtists;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "personal_library_id")
    private List<AlbumDO> likedAlbums;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "personal_library_id")
    private List<PlaylistDO> playlists;

    public PersonalLibraryDO() {
        this.likedArtists = new ArrayList<ArtistDO>();
        this.likedAlbums = new ArrayList<AlbumDO>();
        this.playlists = new ArrayList<PlaylistDO>();
        this.playlists.add(new PlaylistDO("LikedTracks", true));
    }

    public List<PlaylistDO> getCustomPlaylists() {
        return this.playlists.stream().filter(pl -> !pl.isLikedTrackPlaylist()).toList();
    }

    public PlaylistDO getLikedTracksPlaylist() {
        return this.playlists
                .stream()
                .filter(pl -> pl.isLikedTrackPlaylist())
                .findAny()
                .orElse(null);
    }

}
