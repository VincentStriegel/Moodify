package com.moodify.backend.domain.services.database;

import com.moodify.backend.api.transferobjects.AlbumTO;
import com.moodify.backend.api.transferobjects.ArtistTO;
import com.moodify.backend.api.transferobjects.PlaylistTO;
import com.moodify.backend.api.transferobjects.TrackTO;
import com.moodify.backend.domain.services.database.databaseobjects.*;
import com.moodify.backend.domain.services.exceptions.login.UserCredentialsException;
import com.moodify.backend.domain.services.exceptions.login.WrongPasswordException;
import com.moodify.backend.domain.services.exceptions.profiles.*;
import com.moodify.backend.domain.services.exceptions.registration.RegisteredEmailException;
import com.moodify.backend.domain.services.exceptions.registration.RegisteredUsernameException;
import com.moodify.backend.domain.services.security.EmailValidator;
import com.moodify.backend.domain.services.security.PasswordEncoder;
import com.moodify.backend.domain.services.security.PasswordValidator;
import com.moodify.backend.domain.services.security.UsernameValidator;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Transactional
public class PostgresService implements DatabaseService {
    private final PostgresRepository DATABASE_REPOSITORY;
    private final PasswordEncoder ENCODER;
    private final ObjectTransformer OBJECT_TRANSFORMER;


    @Autowired
    public PostgresService(PostgresRepository DATABASE_REPOSITORY,
                           PasswordEncoder ENCODER,
                           ObjectTransformer OBJECT_TRANSFORMER) {
        this.DATABASE_REPOSITORY = DATABASE_REPOSITORY;
        this.ENCODER = ENCODER;
        this.OBJECT_TRANSFORMER = OBJECT_TRANSFORMER;
    }

    @Override
    public UserDO createUser(UserDO userDO) throws Exception {
        EmailValidator.validateEmail(userDO.getEmail());
        UsernameValidator.validateUsername(userDO.getUsername());
        PasswordValidator.validatePassword(userDO.getPassword());


        if (this.DATABASE_REPOSITORY.existsUserByEmail(userDO.getEmail())) {
            throw new RegisteredEmailException();
        }

        if (this.DATABASE_REPOSITORY.existsUserByUsername(userDO.getUsername())) {
            throw new RegisteredUsernameException();
        }


        String hashed = this.ENCODER.encode(userDO.getPassword());
        userDO.setPassword(hashed);

        return userDO;
    }

    @Override
    public void saveUser(UserDO userDO) {

        this.DATABASE_REPOSITORY.save(userDO);
    }

    @Override
    public long loginUser(@RequestBody LoginUser loginUser) throws Exception {

        String loginEmail = loginUser.getCredential();
        String loginUsername = loginUser.getCredential();
        String loginPassword = loginUser.getPassword();

        UserDO userDOByEmail = this.DATABASE_REPOSITORY.getUserByEmail(loginEmail);
        if (exists(userDOByEmail)) {

            String hashedPassword = userDOByEmail.getPassword();

            boolean passNotEqual = !this.ENCODER.compare(loginPassword, hashedPassword);
            if (passNotEqual) {
                throw new WrongPasswordException();
            }
            return userDOByEmail.getId();
        }

        UserDO userDOByUsername = this.DATABASE_REPOSITORY.getUserByUsername(loginUsername);
        if (exists(userDOByUsername)) {

            String hashedPassword = userDOByUsername.getPassword();

            boolean passNotEqual = !this.ENCODER.compare(loginPassword, hashedPassword);
            if (passNotEqual) {
                throw new WrongPasswordException();
            }

            return userDOByUsername.getId();
        }

        throw new UserCredentialsException();

    }

    @Override
    public UserDO getUser(long userId) throws Exception {
        UserDO userDO = this.findUserById(userId);
        userDO.setPassword("");
        return userDO;
    }

    @Override
    public Long addCustomPlaylist(long userId, String playlistTitle) throws Exception {
        UserDO userDO = this.findUserById(userId);


        PlaylistDO playlistDO = new PlaylistDO(playlistTitle);

        userDO.getPersonalLibrary().getPlaylists().add(playlistDO);
        this.DATABASE_REPOSITORY.save(userDO);

        return userDO.getPersonalLibrary().getPlaylists().get(userDO.getPersonalLibrary().getPlaylists().size() - 1).getId();
    }

    @Override
    public PersonalLibraryDO removeCustomPlaylist(long userId, long playlistId) throws Exception {
        UserDO userDO = findUserById(userId);

        this.removeCustomPlaylist(userDO, playlistId);

        this.DATABASE_REPOSITORY.save(userDO);

        return userDO.getPersonalLibrary();
    }

    @Override
    public PersonalLibraryDO addToCustomPlaylist(TrackTO trackTO, long userId, long playlistId) throws Exception {
        UserDO userDO = this.findUserById(userId);


        PlaylistDO customPlaylist = this.findCustomPlaylistFrom(userDO, playlistId);
        TrackDO newTrack = this.OBJECT_TRANSFORMER.generateTrackDOFrom(trackTO);

        this.addToPlaylist(customPlaylist, newTrack);

        this.DATABASE_REPOSITORY.save(userDO);


        return userDO.getPersonalLibrary();
    }

    @Override
    public PersonalLibraryDO removeFromCustomPlaylist(long userId, long playlistId, long trackId) throws Exception {
        UserDO userDO = this.findUserById(userId);

        PlaylistDO playlistDO = this.findCustomPlaylistFrom(userDO, playlistId);

        TrackDO toRemove = findTrackFromPlaylist(playlistDO, trackId);

        playlistDO.getTracks().remove(toRemove);


        this.DATABASE_REPOSITORY.save(userDO);

        return userDO.getPersonalLibrary();
    }

    @Override
    public PersonalLibraryDO addToLikedTracks(TrackTO trackTO, long userId) throws Exception {
        UserDO userDO = this.findUserById(userId);


        TrackDO newTrack = this.OBJECT_TRANSFORMER.generateTrackDOFrom(trackTO);

        PlaylistDO likedTracks = findLikedTracksPlaylist(userDO);

        this.addToPlaylist(likedTracks, newTrack);

        this.DATABASE_REPOSITORY.save(userDO);

        return userDO.getPersonalLibrary();
    }

    @Override
    public PersonalLibraryDO removeFromLikedTracks(long userId, long trackId) throws Exception {
        UserDO userDO = this.findUserById(userId);
        PlaylistDO likedTracks = findLikedTracksPlaylist(userDO);
        TrackDO toRemove = this.findTrackFromPlaylist(likedTracks, trackId);
        likedTracks.getTracks().remove(toRemove);

        return  userDO.getPersonalLibrary();
    }

    @Override
    public PersonalLibraryDO addToLikedArtists(ArtistTO artistTO, long userId) throws Exception {
        UserDO userDO = this.findUserById(userId);

        ArtistDO newArtist = this.OBJECT_TRANSFORMER.generateArtistDOFrom(artistTO);
        this.addToLikedArtists(userDO, newArtist);

        this.DATABASE_REPOSITORY.save(userDO);

        return userDO.getPersonalLibrary();
    }

    @Override
    public PersonalLibraryDO removeFromLikedArtists(long artistId, long userId) throws Exception {
        UserDO userDO = this.findUserById(userId);
        ArtistDO artistDO = this.findArtistById(artistId, userDO);

        userDO.getPersonalLibrary().getLikedArtists().remove(artistDO);

        this.DATABASE_REPOSITORY.save(userDO);

        return userDO.getPersonalLibrary();
    }

    @Override
    public PersonalLibraryDO addToLikedAlbums(AlbumTO albumTO, long userId) throws Exception {
        UserDO userDO = this.findUserById(userId);
        AlbumDO newAlbum = this.OBJECT_TRANSFORMER.generateAlbumDoFrom(albumTO);

        userDO.getPersonalLibrary().getLikedAlbums().add(newAlbum);
        this.DATABASE_REPOSITORY.save(userDO);

        return userDO.getPersonalLibrary();
    }

    @Override
    public PersonalLibraryDO removeFromLikedAlbums(long albumId, long userId) throws Exception {
        UserDO userDO = this.findUserById(userId);
        AlbumDO albumDO = this.findAlbumById(userDO, albumId);

        userDO.getPersonalLibrary().getLikedAlbums().remove(albumDO);
        this.DATABASE_REPOSITORY.save(userDO);

        return userDO.getPersonalLibrary();
    }

    @Override
    @Transactional
    public PlaylistDO getPlaylistById(long playlistId, long userId) throws Exception {
        UserDO userDO = this.findUserById(userId);
        PlaylistDO customPlaylist = this.findCustomPlaylistFrom(userDO, playlistId);

        return customPlaylist;
    }

    private AlbumDO findAlbumById(UserDO userDO, long albumId) throws Exception {
        AlbumDO albumDO = userDO
                .getPersonalLibrary()
                .getLikedAlbums()
                .stream()
                .filter(al -> al.getAlbum_id_deezer() == albumId)
                .findFirst()
                .orElse(null);
        if (albumDO == null) {
            throw  new AlbumNotFoundException();
        }

        return albumDO;
    }

    private void addToLikedArtists(UserDO userDO, ArtistDO newArtist) throws Exception {
        if (userDO.getPersonalLibrary().getLikedArtists().stream()
                .anyMatch(ar -> ar.getArtist_id_deezer() == newArtist.getArtist_id_deezer())) {
            throw new DuplicateArtistsException();
        }
        userDO.getPersonalLibrary().getLikedArtists().add(newArtist);
    }

    private void addToPlaylist(PlaylistDO playlistDO, TrackDO trackDO) throws Exception {

        if (playlistDO
                .getTracks()
                .stream()
                .anyMatch(tr -> tr.getPreview().equals(trackDO.getPreview()))) {
            throw new DuplicateTracksException();
        }

        playlistDO.getTracks().add(trackDO);
    }

    private void removeCustomPlaylist(UserDO userDO, long playlistId) throws Exception {

        PlaylistDO playlistDOS = userDO.getPersonalLibrary().getPlaylists().stream().filter(ps -> ps.getId() == playlistId).findFirst().orElse(null);
        if (playlistDOS == null) {
            throw new PlaylistNotFoundException();
        }

        if (playlistDOS.isLikedTrackPlaylist()) {
            throw new DefaultPlaylistException();
        }

        userDO.getPersonalLibrary().getPlaylists().remove(playlistDOS);

    }

    private boolean exists(UserDO userDO) {
        return userDO != null;
    }

    private UserDO findUserById(long userId) throws Exception {
        UserDO userDO = this.DATABASE_REPOSITORY.findById(userId);
        if (userDO == null) {
            throw new UserNotFoundException();
        }
        return userDO;
    }

    private TrackDO findTrackFromPlaylist(PlaylistDO playlistDO, long trackId) throws Exception {
        TrackDO track = playlistDO.getTracks().stream().filter(trackDO -> trackDO.getId_deezer() == trackId).findFirst().orElse(null);
        if (track == null) {
            throw new TrackNotFoundException();
        }
        return track;
    }

    private PlaylistDO findLikedTracksPlaylist(UserDO userDO) throws Exception {
        PlaylistDO likedTracks = userDO.getPersonalLibrary().getPlaylists().stream().filter(pl -> pl.isLikedTrackPlaylist()).findFirst().orElse(null);
        if (likedTracks == null) {
            throw  new DefaultPlaylistNotFoundException();
        }
        return likedTracks;
    }


    private PlaylistDO findCustomPlaylistFrom(UserDO userDO, long playlistId) throws  Exception {
        PlaylistDO playlistDO = userDO
                .getPersonalLibrary()
                .getPlaylists()
                .stream()
                .filter(p -> p.getId() == playlistId && !p.isLikedTrackPlaylist())
                .findFirst()
                .orElse(null);
        if (playlistDO == null) {
            throw new PlaylistNotFoundException();
        }
        return playlistDO;
    }

    private ArtistDO findArtistById(long artistId, UserDO userDO) throws Exception {
        ArtistDO artist = userDO
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

    private PlaylistDO findPlaylistById(long playlistId, UserDO userDO) throws Exception{
        PlaylistDO playlistDO = userDO
                .getPersonalLibrary()
                .getPlaylists()
                .stream()
                .filter(pl -> pl.getId() == playlistId)
                .findFirst()
                .orElse(null);

        if (playlistDO == null) {
            throw new PlaylistNotFoundException();
        }

        return playlistDO;
    }

}
