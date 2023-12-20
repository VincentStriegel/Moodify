package com.moodify.backend.domain.services.database;

import com.moodify.backend.api.transferobjects.*;
import com.moodify.backend.domain.services.database.databaseobjects.*;
import com.moodify.backend.domain.services.exceptions.login.*;
import com.moodify.backend.domain.services.exceptions.profiles.*;
import com.moodify.backend.domain.services.exceptions.registration.*;
import com.moodify.backend.domain.services.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Transactional
public class PostgresService implements DatabaseService {
    private final PostgresRepository DATABASE_REPOSITORY;
    private final PasswordEncoder ENCODER;
    private final DOAssembler DO_OBJECT_ASSEMBLER;


    @Autowired
    public PostgresService(PostgresRepository DATABASE_REPOSITORY,
                           PasswordEncoder ENCODER,
                           DOAssembler DO_OBJECT_ASSEMBLER) {
        this.DATABASE_REPOSITORY = DATABASE_REPOSITORY;
        this.ENCODER = ENCODER;
        this.DO_OBJECT_ASSEMBLER = DO_OBJECT_ASSEMBLER;
    }
    @Override
    public UserDO createUser(UserDO user) throws Exception {
        EmailValidator.validateEmail(user.getEmail());
        UsernameValidator.validateUsername(user.getUsername());
        PasswordValidator.validatePassword(user.getPassword());


        if (this.DATABASE_REPOSITORY.existsUserByEmail(user.getEmail())) {
            throw new RegisteredEmailException();
        }

        if (this.DATABASE_REPOSITORY.existsUserByUsername(user.getUsername())) {
            throw new RegisteredUsernameException();
        }


        String hashed = this.ENCODER.encode(user.getPassword());
        user.setPassword(hashed);

        return user;
    }

    @Override
    public void saveUser(UserDO user) {
        this.DATABASE_REPOSITORY.save(user);
    }

    @Override
    public long loginUser(@RequestBody LoginUser loginUser) throws Exception {

        String loginEmail = loginUser.getCredential();
        String loginUsername = loginUser.getCredential();
        String loginPassword = loginUser.getPassword();

        UserDO userByEmail = this.DATABASE_REPOSITORY.getUserByEmail(loginEmail);
        if (exists(userByEmail)) {

            String hashedPassword = userByEmail.getPassword();

            boolean passNotEqual = !this.ENCODER.compare(loginPassword, hashedPassword);
            if (passNotEqual) {
                throw new WrongPasswordException();
            }
            return userByEmail.getId();
        }

        UserDO userByUsername = this.DATABASE_REPOSITORY.getUserByUsername(loginUsername);
        if (exists(userByUsername)) {

            String hashedPassword = userByUsername.getPassword();

            boolean passNotEqual = !this.ENCODER.compare(loginPassword, hashedPassword);
            if (passNotEqual) {
                throw new WrongPasswordException();
            }

            return userByUsername.getId();
        }

        throw new UserCredentialsException();

    }

    @Override
    public UserDO findUserById(long userId) throws Exception {
        UserDO user = this.DATABASE_REPOSITORY.findById(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public Long createCustomPlaylist(long userId, String playlistTitle) throws Exception {
        UserDO user = this.findUserById(userId);
        PlaylistDO customPlaylist = new PlaylistDO(playlistTitle);

        user.getPersonalLibrary().getPlaylists().add(customPlaylist);
        this.DATABASE_REPOSITORY.save(user);

        int lastIndex = user.getPersonalLibrary().getPlaylists().size() - 1;
        return user.getPersonalLibrary().getPlaylists().get(lastIndex).getId();
    }

    @Override
    public void deleteCustomPlaylist(long userId, long playlistId) throws Exception {
        UserDO user = findUserById(userId);
        PlaylistDO customPlaylist = this.findPlaylistById(playlistId, user);

        if (customPlaylist.isLikedTrackPlaylist()) {
            throw new DefaultPlaylistException();
        }

        user.getPersonalLibrary().getPlaylists().remove(customPlaylist);

        this.DATABASE_REPOSITORY.save(user);
    }

    @Override
    public void addToCustomPlaylist(TrackTO trackTO, long userId, long playlistId) throws Exception {
        UserDO user = this.findUserById(userId);
        PlaylistDO customPlaylist = this.findPlaylistById(playlistId, user);
        TrackDO track = this.DO_OBJECT_ASSEMBLER.generateTrackDOFrom(trackTO);

        this.addTrackTo(customPlaylist, track);

        this.DATABASE_REPOSITORY.save(user);
    }

    @Override
    public void deleteFromCustomPlaylist(long userId, long playlistId, long trackId) throws Exception {
        UserDO user = this.findUserById(userId);
        PlaylistDO customPlaylist = this.findPlaylistById(playlistId, user);
        TrackDO track = this.findTrackById(customPlaylist, trackId);

        this.deleteTrackFrom(customPlaylist, track);

        this.DATABASE_REPOSITORY.save(user);
    }

    @Override
    public void addToLikedTracks(TrackTO trackTO, long userId) throws Exception {
        UserDO user = this.findUserById(userId);
        PlaylistDO likedTracks = this.findLikedTracksOf(user);
        TrackDO track = this.DO_OBJECT_ASSEMBLER.generateTrackDOFrom(trackTO);

        this.addTrackTo(likedTracks, track);

        this.DATABASE_REPOSITORY.save(user);
    }

    @Override
    public void deleteFromLikedTracks(long userId, long trackId) throws Exception {
        UserDO user = this.findUserById(userId);
        PlaylistDO likedTracks = this.findLikedTracksOf(user);
        TrackDO track = this.findTrackById(likedTracks, trackId);

        this.deleteTrackFrom(likedTracks, track);
    }

    @Override
    public void addToLikedArtists(ArtistTO artistTO, long userId) throws Exception {
        UserDO user = this.findUserById(userId);

        ArtistDO artist = this.DO_OBJECT_ASSEMBLER.generateArtistDOFrom(artistTO);
        if (user.getPersonalLibrary().getLikedArtists().stream()
                .anyMatch(ar -> ar.getArtist_id_deezer() == artist.getArtist_id_deezer())) {
            throw new DuplicateArtistsException();
        }

        user.getPersonalLibrary().getLikedArtists().add(artist);

        this.DATABASE_REPOSITORY.save(user);
    }

    @Override
    public void deleteFromLikedArtists(long artistId, long userId) throws Exception {
        UserDO user = this.findUserById(userId);
        ArtistDO artist = this.findArtistById(artistId, user);

        user.getPersonalLibrary().getLikedArtists().remove(artist);

        this.DATABASE_REPOSITORY.save(user);
    }

    @Override
    public void addToLikedAlbums(AlbumTO albumTO, long userId) throws Exception {
        UserDO user = this.findUserById(userId);
        AlbumDO album = this.DO_OBJECT_ASSEMBLER.generateAlbumDoFrom(albumTO);

        if (user
                .getPersonalLibrary()
                .getLikedArtists()
                .stream()
                .anyMatch(ar -> ar.getArtist_id_deezer() == album.getAlbum_id_deezer())) {
            throw new DuplicateAlbumsException();
        }

        user.getPersonalLibrary().getLikedAlbums().add(album);

        this.DATABASE_REPOSITORY.save(user);
    }

    @Override
    public void deleteFromLikedAlbums(long albumId, long userId) throws Exception {
        UserDO user = this.findUserById(userId);
        AlbumDO album = this.findAlbumById(user, albumId);

        user.getPersonalLibrary().getLikedAlbums().remove(album);

        this.DATABASE_REPOSITORY.save(user);
    }

    @Override
    @Transactional
    public PlaylistDO findPlaylistById(long playlistId, long userId) throws Exception {
        UserDO user = this.findUserById(userId);

        return this.findPlaylistById(playlistId, user);
    }

    private boolean exists(UserDO user) {
        return user != null;
    }

    private AlbumDO findAlbumById(UserDO user, long albumId) throws Exception {
        AlbumDO album = user
                .getPersonalLibrary()
                .getLikedAlbums()
                .stream()
                .filter(al -> al.getAlbum_id_deezer() == albumId)
                .findFirst()
                .orElse(null);
        if (album == null) {
            throw  new AlbumNotFoundException();
        }

        return album;
    }

    private TrackDO findTrackById(PlaylistDO playlist, long trackId) throws Exception {
        TrackDO track = playlist
                .getTracks()
                .stream()
                .filter(trackDO -> trackDO.getId_deezer() == trackId)
                .findFirst()
                .orElse(null);
        if (track == null) {
            throw new TrackNotFoundException();
        }
        return track;
    }

    private PlaylistDO findLikedTracksOf(UserDO user) throws Exception {
        PlaylistDO likedTracks = user
                .getPersonalLibrary()
                .getPlaylists()
                .stream().
                filter(PlaylistDO::isLikedTrackPlaylist)
                .findFirst()
                .orElse(null);

        if (likedTracks == null) {
            throw  new DefaultPlaylistNotFoundException();
        }
        return likedTracks;
    }

    private ArtistDO findArtistById(long artistId, UserDO user) throws Exception {
        ArtistDO artist = user
                .getPersonalLibrary()
                .getLikedArtists()
                .stream()
                .filter(ar -> ar.getArtist_id_deezer() == artistId)
                .findFirst()
                .orElse(null);

        if (artist == null) {
            throw new ArtistNotFoundException();
        }

        return artist;
    }

    private PlaylistDO findPlaylistById(long playlistId, UserDO user) throws Exception {
        PlaylistDO playlist = user
                .getPersonalLibrary()
                .getPlaylists()
                .stream()
                .filter(pl -> pl.getId() == playlistId)
                .findFirst()
                .orElse(null);

        if (playlist == null) {
            throw new PlaylistNotFoundException();
        }

        return playlist;
    }

    private void addTrackTo(PlaylistDO playlist, TrackDO track) throws Exception {

        if (playlist
                .getTracks()
                .stream()
                .anyMatch(tr -> tr.getPreview().equals(track.getPreview()))) {
            throw new DuplicateTracksException();
        }

        playlist.getTracks().add(track);
        playlist.incrementNumberOfSongs();

        if (playlist.getNumber_of_songs() == 1) {
            playlist.setPicture_big(track.getAlbum_cover_big_deezer());
            playlist.setPicture_small(track.getAlbum_cover_small_deezer());
        }

    }

    private void deleteTrackFrom(PlaylistDO playlist, TrackDO track) throws Exception {

        if (playlist
                .getTracks()
                .stream()
                .noneMatch(tr -> tr.getPreview().equals(track.getPreview()))) {
            throw new TrackNotFoundException();
        }

        playlist.getTracks().remove(track);
        playlist.decrementNumberOfSongs();

        if (playlist.getNumber_of_songs() == 0) {
            playlist.setPicture_big(null); //TODO cab be changed to default picture
            playlist.setPicture_small(null); //TODO cab be changed to default picture
        }
    }

}
