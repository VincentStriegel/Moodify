package com.moodify.backend.domain.services.database.databaseobjects;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    List<ArtistDO> likedArtists;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "personal_library_id")
    List<AlbumDO> likedAlbums;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "personal_library_id")
    List<PlaylistDO> playlists;

    public PersonalLibraryDO() {
        this.likedArtists = new ArrayList<ArtistDO>();
        this.likedAlbums = new ArrayList<AlbumDO>();
        this.playlists = new ArrayList<PlaylistDO>();
        this.playlists.add(new PlaylistDO("LikedTracks", true));
    }



}
