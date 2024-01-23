package com.moodify.backend.services.database.postgres;

import com.moodify.backend.api.transferobjects.*;
import com.moodify.backend.services.database.DatabaseService;
import com.moodify.backend.services.database.exceptions.login.UserCredentialsException;
import com.moodify.backend.services.database.exceptions.login.WrongPasswordException;
import com.moodify.backend.services.database.exceptions.profiles.*;
import com.moodify.backend.services.database.exceptions.registration.*;
import com.moodify.backend.services.database.objects.*;
import com.moodify.backend.services.database.security.EmailValidator;
import com.moodify.backend.services.database.security.PasswordEncoder;
import com.moodify.backend.services.database.security.PasswordValidator;
import com.moodify.backend.services.database.security.UsernameValidator;
import com.moodify.backend.services.database.util.DOAssembler;
import com.moodify.backend.services.database.util.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * This class is the implementation of the DatabaseService interface for a PostgreSQL database.
 * It is annotated with @Component to indicate that it is an autodetectable bean for Spring.
 * It is also annotated with @Transactional to indicate that all its methods are transactional.
 */
@Component
@Transactional
public class PostgresService implements DatabaseService {
    private final UserRepository USER_REPOSITORY;
    private final PlaylistRepository PLAYLIST_REPOSITORY;

    private final MoodifySingleRepository SINGLE_REPOSITORY;
    private final PasswordEncoder ENCODER;
    private final DOAssembler DO_OBJECT_ASSEMBLER;


    @Autowired
    public PostgresService(UserRepository USER_REPOSITORY,
                           PasswordEncoder ENCODER,
                           DOAssembler DO_OBJECT_ASSEMBLER,
                           PlaylistRepository PLAYLIST_REPOSITORY,
                           MoodifySingleRepository SINGLE_REPOSITORY) {
        this.USER_REPOSITORY = USER_REPOSITORY;
        this.ENCODER = ENCODER;
        this.DO_OBJECT_ASSEMBLER = DO_OBJECT_ASSEMBLER;
        this.PLAYLIST_REPOSITORY = PLAYLIST_REPOSITORY;
        this.SINGLE_REPOSITORY = SINGLE_REPOSITORY;
    }



    /**
     * This method is used to create a new user in the system.
     * It first validates the email, username, and password of the user.
     * If any of these validations fail, it throws the corresponding exception.
     * Then it checks if the email and username already exist in the system.
     * If they do, it throws the corresponding exception.
     * Finally, it hashes the password and sets it to the user object.
     *
     * @param user The UserDO object containing the user's details.
     * @return UserDO The UserDO object with the hashed password.
     * @throws InvalidEmailException If the email is not valid.
     * @throws InvalidUsernameException If the username is not valid.
     * @throws InvalidPasswordException If the password is not valid.
     * @throws RegisteredEmailException If the email is already registered.
     * @throws RegisteredUsernameException If the username is already registered.
     */
    @Override
    public UserDO createUser(UserDO user) throws
            InvalidEmailException,
            InvalidUsernameException,
            InvalidPasswordException,
            RegisteredEmailException,
            RegisteredUsernameException {

        EmailValidator.validateEmail(user.getEmail());
        UsernameValidator.validateUsername(user.getUsername());
        PasswordValidator.validatePassword(user.getPassword());


        if (this.USER_REPOSITORY.existsUserByEmail(user.getEmail())) {
            throw new RegisteredEmailException();
        }

        if (this.USER_REPOSITORY.existsUserByUsername(user.getUsername())) {
            throw new RegisteredUsernameException();
        }


        String hashed = this.ENCODER.encode(user.getPassword());
        user.setPassword(hashed);

        return user;
    }


    /**
     * This method is used to save a user in the system.
     * It takes a UserDO object as a parameter and saves it to the UserRepository.
     *
     * @param user The UserDO object containing the user's details.
     */
    @Override
    public void saveUser(UserDO user) {
        this.USER_REPOSITORY.save(user);
    }


    /**
     * This method is used to log in a user.
     * It takes a LoginUser object as a parameter and checks if the user exists by email or username.
     * If the user exists, it compares the hashed password with the provided password.
     * If the passwords match, it returns the user's ID.
     * If the passwords do not match or the user does not exist, it throws the corresponding exception.
     *
     * @param loginUser The LoginUser object containing the user's login credentials.
     * @return long The ID of the logged-in user.
     * @throws UserCredentialsException If the user does not exist.
     * @throws WrongPasswordException If the password is incorrect.
     */
    @Override
    public long loginUser(@RequestBody LoginUser loginUser) throws UserCredentialsException, WrongPasswordException {

        String loginEmail = loginUser.getCredential();
        String loginUsername = loginUser.getCredential();
        String loginPassword = loginUser.getPassword();

        UserDO userByEmail = this.USER_REPOSITORY.getUserByEmail(loginEmail);
        if (exists(userByEmail)) {

            String hashedPassword = userByEmail.getPassword();

            boolean passNotEqual = !this.ENCODER.compare(loginPassword, hashedPassword);
            if (passNotEqual) {
                throw new WrongPasswordException();
            }
            return userByEmail.getId();
        }

        UserDO userByUsername = this.USER_REPOSITORY.getUserByUsername(loginUsername);
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

    /**
     * This method is used to find a user by their ID.
     * It takes a long as a parameter representing the user's ID and returns the UserDO object of the user.
     * If the user does not exist, it throws a UserNotFoundException.
     *
     * @param userId The ID of the user.
     * @return UserDO The UserDO object of the user.
     * @throws UserNotFoundException If the user does not exist.
     */
    @Override
    public UserDO findUserById(long userId) throws UserNotFoundException {
        UserDO user = this.USER_REPOSITORY.findById(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    /**
     * This method is used to search for artists by a query.
     * It takes a string as a parameter representing the query and returns a list of UserDO objects of the artists.
     *
     * @param query The query to search for artists.
     * @return List<UserDO> The list of UserDO objects of the artists.
     */
    @Override
    public List<UserDO> searchArtists(String query) {
        String wildcard = "%" + query + "%";
        return this.USER_REPOSITORY.findAllByUsernameLikeAndDiscographyIsNotNull(wildcard);
    }

    /**
     * This method is used to get an artist by their ID.
     * It takes a long as a parameter representing the artist's ID and returns the UserDO object of the artist.
     * If the artist does not exist, it throws an ArtistNotFoundException.
     *
     * @param artistId The ID of the artist.
     * @return UserDO The UserDO object of the artist.
     * @throws ArtistNotFoundException If the artist does not exist.
     */
    @Override
    public UserDO getArtist(long artistId) throws ArtistNotFoundException {
        UserDO artist = this.USER_REPOSITORY.findByIdAndDiscographyIsNotNull(artistId);
        if (artist == null) {
            throw new ArtistNotFoundException();
        }

        return artist;
    }

    /**
     * This method is used to search for playlists by a query.
     * It takes a string as a parameter representing the query and returns a list of PlaylistDO objects of the playlists.
     *
     * @param query The query to search for playlists.
     * @return List<PlaylistDO> The list of PlaylistDO objects of the playlists.
     */
    @Override
    public List<PlaylistDO> searchPlaylists(String query) {
        String wildcard = "%" + query + "%";
        return this.PLAYLIST_REPOSITORY.findAllByTitleLike(wildcard);
    }

    /**
     * This method is used to create a custom playlist for a user.
     * It takes a long and a string as parameters representing the user's ID and the playlist title respectively.
     * It first checks if the user already has a playlist with the same title.
     * If they do, it throws a DuplicatePlaylistsException.
     * Then it creates a new PlaylistDO object with the provided title and adds it to the user's playlists.
     * Finally, it saves the user and returns the ID of the new playlist.
     *
     * @param userId The ID of the user.
     * @param playlistTitle The title of the playlist.
     * @return Long The ID of the new playlist.
     * @throws UserNotFoundException If the user does not exist.
     * @throws DuplicatePlaylistsException If the user already has a playlist with the same title.
     */
    @Override
    public Long createCustomPlaylist(long userId, String playlistTitle) throws UserNotFoundException, DuplicatePlaylistsException {
        UserDO user = this.findUserById(userId);
        if (user.getPersonalLibrary().getCustomPlaylists().stream().anyMatch(pl -> pl.getTitle().equals(playlistTitle))) {
            throw new DuplicatePlaylistsException();
        }

        PlaylistDO customPlaylist = new PlaylistDO(playlistTitle);
        user.getPersonalLibrary().getPlaylists().add(customPlaylist);
        this.USER_REPOSITORY.save(user);

        int lastIndex = user.getPersonalLibrary().getPlaylists().size() - 1;
        return user.getPersonalLibrary().getPlaylists().get(lastIndex).getId();
    }

    /**
     * This method is used to delete a custom playlist of a user.
     * It takes two longs as parameters representing the user's ID and the playlist's ID respectively.
     * It first checks if the playlist is a liked track playlist.
     * If it is, it throws a DefaultPlaylistException.
     * Then it removes the playlist from the user's playlists and saves the user.
     *
     * @param userId The ID of the user.
     * @param playlistId The ID of the playlist.
     * @throws UserNotFoundException If the user does not exist.
     * @throws PlaylistNotFoundException If the playlist does not exist.
     * @throws DefaultPlaylistException If the playlist is a liked track playlist.
     */
    @Override
    public void deleteCustomPlaylist(long userId, long playlistId) throws
            UserNotFoundException,
            PlaylistNotFoundException,
            DefaultPlaylistException {
        UserDO user = findUserById(userId);
        PlaylistDO customPlaylist = this.findPlaylistById(playlistId, user);

        if (customPlaylist.isLikedTrackPlaylist()) {
            throw new DefaultPlaylistException();
        }

        user.getPersonalLibrary().getPlaylists().remove(customPlaylist);

        this.USER_REPOSITORY.save(user);
    }

    /**
     * This method is used to add a track to a custom playlist of a user.
     * It takes a TrackTO object and two longs as parameters representing the track, the user's ID and the playlist's ID respectively.
     * It first generates a TrackDO object from the TrackTO object.
     * Then it adds the track to the playlist and saves the user.
     *
     * @param trackTO The TrackTO object containing the track's details.
     * @param userId The ID of the user.
     * @param playlistId The ID of the playlist.
     * @throws UserNotFoundException If the user does not exist.
     * @throws PlaylistNotFoundException If the playlist does not exist.
     * @throws DuplicateTracksException If the playlist already contains the track.
     */
    @Override
    public void addToCustomPlaylist(TrackTO trackTO, long userId, long playlistId) throws
            UserNotFoundException,
            PlaylistNotFoundException,
            DuplicateTracksException {
        UserDO user = this.findUserById(userId);
        PlaylistDO customPlaylist = this.findPlaylistById(playlistId, user);
        TrackDO track = this.DO_OBJECT_ASSEMBLER.generateTrackDOFrom(trackTO);

        this.addTrackTo(customPlaylist, track);

        this.USER_REPOSITORY.save(user);
    }

    /**
     * This method is used to delete a track from a custom playlist of a user.
     * It takes two longs as parameters representing the user's ID, the playlist's ID and the track's ID respectively.
     * It first finds the track in the playlist.
     * Then it removes the track from the playlist and saves the user.
     *
     * @param userId The ID of the user.
     * @param playlistId The ID of the playlist.
     * @param trackId The ID of the track.
     * @throws UserNotFoundException If the user does not exist.
     * @throws PlaylistNotFoundException If the playlist does not exist.
     * @throws TrackNotFoundException If the track does not exist in the playlist.
     */
    @Override
    public void deleteFromCustomPlaylist(long userId, long playlistId, long trackId) throws
            UserNotFoundException,
            PlaylistNotFoundException,
            TrackNotFoundException {
        UserDO user = this.findUserById(userId);
        PlaylistDO customPlaylist = this.findPlaylistById(playlistId, user);
        TrackDO track = this.findTrackById(customPlaylist, trackId);

        this.deleteTrackFrom(customPlaylist, track);

        this.USER_REPOSITORY.save(user);
    }

    /**
     * This method is used to add a track to the liked tracks of a user.
     * It takes a TrackTO object and a long as parameters representing the track and the user's ID respectively.
     * It first generates a TrackDO object from the TrackTO object.
     * Then it finds the liked tracks playlist of the user and adds the track to it.
     * Finally, it saves the user.
     *
     * @param trackTO The TrackTO object containing the track's details.
     * @param userId The ID of the user.
     * @throws UserNotFoundException If the user does not exist.
     * @throws DefaultPlaylistNotFoundException If the liked tracks playlist does not exist.
     * @throws DuplicateTracksException If the liked tracks playlist already contains the track.
     */
    @Override
    public void addToLikedTracks(TrackTO trackTO, long userId) throws
            UserNotFoundException,
            DefaultPlaylistNotFoundException,
            DuplicateTracksException {
        UserDO user = this.findUserById(userId);
        PlaylistDO likedTracks = this.findLikedTracksOf(user);
        TrackDO track = this.DO_OBJECT_ASSEMBLER.generateTrackDOFrom(trackTO);

        this.addTrackTo(likedTracks, track);

        this.USER_REPOSITORY.save(user);
    }

    /**
     * This method is used to remove a track from the liked tracks of a user.
     * It takes the user's ID and the track's ID as parameters.
     * It first finds the user and the liked tracks playlist of the user.
     * Then it finds the track in the liked tracks playlist.
     * Finally, it removes the track from the liked tracks playlist.
     *
     * @param userId The ID of the user.
     * @param trackId The ID of the track.
     * @throws UserNotFoundException If the user does not exist.
     * @throws DefaultPlaylistNotFoundException If the liked tracks playlist does not exist.
     * @throws TrackNotFoundException If the track does not exist in the liked tracks playlist.
     */
    @Override
    public void deleteFromLikedTracks(long userId, long trackId) throws
            UserNotFoundException,
            DefaultPlaylistNotFoundException,
            TrackNotFoundException {
        UserDO user = this.findUserById(userId);
        PlaylistDO likedTracks = this.findLikedTracksOf(user);
        TrackDO track = this.findTrackById(likedTracks, trackId);

        this.deleteTrackFrom(likedTracks, track);
    }

    /**
     * This method is used to add an artist to the liked artists of a user.
     * It takes an ArtistTO object and the user's ID as parameters.
     * It first finds the user.
     * Then it generates an ArtistDO object from the ArtistTO object.
     * If the user already likes the artist, it throws a DuplicateArtistsException.
     * Finally, it adds the artist to the liked artists of the user and saves the user.
     *
     * @param artistTO The ArtistTO object containing the artist's details.
     * @param userId The ID of the user.
     * @throws UserNotFoundException If the user does not exist.
     * @throws DuplicateArtistsException If the user already likes the artist.
     */
    @Override
    public void addToLikedArtists(ArtistTO artistTO, long userId) throws UserNotFoundException, DuplicateArtistsException {
        UserDO user = this.findUserById(userId);

        ArtistDO artist = this.DO_OBJECT_ASSEMBLER.generateArtistDOFrom(artistTO);
        if (user.getPersonalLibrary().getLikedArtists().stream()
                .anyMatch(ar -> ar.getArtist_id_source() == artist.getArtist_id_source())) {
            throw new DuplicateArtistsException();
        }

        user.getPersonalLibrary().getLikedArtists().add(artist);

        this.USER_REPOSITORY.save(user);
    }

    /**
     * This method is used to remove an artist from the liked artists of a user.
     * It takes the artist's ID and the user's ID as parameters.
     * It first finds the user and the artist.
     * Then it removes the artist from the liked artists of the user and saves the user.
     *
     * @param artistId The ID of the artist.
     * @param userId The ID of the user.
     * @throws UserNotFoundException If the user does not exist.
     * @throws ArtistNotFoundException If the artist does not exist in the liked artists of the user.
     */
    @Override
    public void deleteFromLikedArtists(long artistId, long userId) throws UserNotFoundException, ArtistNotFoundException {
        UserDO user = this.findUserById(userId);
        ArtistDO artist = this.findArtistById(artistId, user);

        user.getPersonalLibrary().getLikedArtists().remove(artist);

        this.USER_REPOSITORY.save(user);
    }

    /**
     * This method is used to add an album to the liked albums of a user.
     * It takes an AlbumTO object and the user's ID as parameters.
     * It first finds the user.
     * Then it generates an AlbumDO object from the AlbumTO object.
     * If the user already likes the album, it throws a DuplicateAlbumsException.
     * Finally, it adds the album to the liked albums of the user and saves the user.
     *
     * @param albumTO The AlbumTO object containing the album's details.
     * @param userId The ID of the user.
     * @throws UserNotFoundException If the user does not exist.
     * @throws DuplicateAlbumsException If the user already likes the album.
     */
    @Override
    public void addToLikedAlbums(AlbumTO albumTO, long userId) throws UserNotFoundException, DuplicateAlbumsException {
        UserDO user = this.findUserById(userId);
        AlbumDO album = this.DO_OBJECT_ASSEMBLER.generateAlbumDoFrom(albumTO);

        if (user
                .getPersonalLibrary()
                .getLikedArtists()
                .stream()
                .anyMatch(ar -> ar.getArtist_id_source() == album.getAlbum_id_source())) {
            throw new DuplicateAlbumsException();
        }

        user.getPersonalLibrary().getLikedAlbums().add(album);

        this.USER_REPOSITORY.save(user);
    }

    /**
     * This method is used to remove an album from the liked albums of a user.
     * It takes the album's ID and the user's ID as parameters.
     * It first finds the user and the album.
     * Then it removes the album from the liked albums of the user and saves the user.
     *
     * @param albumId The ID of the album.
     * @param userId The ID of the user.
     * @throws UserNotFoundException If the user does not exist.
     * @throws AlbumNotFoundException If the album does not exist in the liked albums of the user.
     */
    @Override
    public void deleteFromLikedAlbums(long albumId, long userId) throws UserNotFoundException, AlbumNotFoundException {
        UserDO user = this.findUserById(userId);
        AlbumDO album = this.findAlbumById(user, albumId);

        user.getPersonalLibrary().getLikedAlbums().remove(album);

        this.USER_REPOSITORY.save(user);
    }

    /**
     * This method is used to find a playlist by its ID from a user.
     * It takes two longs as parameters representing the playlist's ID and the user's ID respectively.
     * It first finds the user.
     * Then it finds the playlist in the user's playlists.
     * Finally, it returns the playlist.
     *
     * @param playlistId The ID of the playlist.
     * @param userId The ID of the user.
     * @return PlaylistDO The PlaylistDO object of the playlist.
     * @throws UserNotFoundException If the user does not exist.
     * @throws PlaylistNotFoundException If the playlist does not exist in the user's playlists.
     */
    @Override
    @Transactional
    public PlaylistDO findPlaylistByIdFromUser(long playlistId, long userId) throws UserNotFoundException, PlaylistNotFoundException {
        UserDO user = this.findUserById(userId);

        return this.findPlaylistById(playlistId, user);
    }

    /**
     * This method is used to find a playlist by its ID.
     * It takes a long as a parameter representing the playlist's ID and returns the PlaylistDO object of the playlist.
     * If the playlist does not exist, it throws a PlaylistNotFoundException.
     *
     * @param playlistId The ID of the playlist.
     * @return PlaylistDO The PlaylistDO object of the playlist.
     * @throws PlaylistNotFoundException If the playlist does not exist.
     */
    @Override
    public PlaylistDO findPlaylistById(long playlistId) throws PlaylistNotFoundException {
        PlaylistDO playlist = this.PLAYLIST_REPOSITORY.findPlaylistDOById(playlistId);
        if (playlist == null) {
            throw new PlaylistNotFoundException();
        }
        return playlist;
    }

    /**
     * This method is used to promote a user to an artist.
     * It takes the user's ID and two strings representing the big picture and the small picture of the artist as parameters.
     * It first finds the user.
     * If the user is already an artist, it throws a UserAlreadyPromotedException.
     * Then it sets the discography of the user and the pictures of the artist.
     * Finally, it saves the user.
     *
     * @param userId The ID of the user.
     * @param picture_big The big picture of the artist.
     * @param picture_small The small picture of the artist.
     * @throws UserNotFoundException If the user does not exist.
     * @throws UserAlreadyPromotedException If the user is already an artist.
     */
    @Override
    public void promoteUserToArtist(long userId, String picture_big, String picture_small) throws UserNotFoundException, UserAlreadyPromotedException {
        UserDO newArtist = this.findUserById(userId);
        if (newArtist.getDiscography() != null) {
            throw new UserAlreadyPromotedException();
        }

        newArtist.setDiscography(new DiscographyDO());
        newArtist.getDiscography().setPicture_big(picture_big);
        newArtist.getDiscography().setPicture_small(picture_small);
        this.saveUser(newArtist);
    }

    /**
     * This method is used to add a single to the discography of an artist.
     * It takes a TrackTO object and the artist's ID as parameters.
     * It first finds the artist.
     * If the artist is not an artist, it throws a UserNotArtistException.
     * If the discography of the artist already contains the single, it throws a DuplicateTracksException.
     * Then it generates a MoodifySingleDO object from the TrackTO object and adds it to the discography of the artist.
     * Finally, it saves the artist.
     *
     * @param track The TrackTO object containing the single's details.
     * @param userId The ID of the artist.
     * @throws UserNotFoundException If the artist does not exist.
     * @throws UserNotArtistException If the artist is not an artist.
     * @throws DuplicateTracksException If the discography of the artist already contains the single.
     */
    @Override
    public void addSingleToDiscography(long userId, TrackTO track) throws
            UserNotFoundException,
            UserNotArtistException,
            DuplicateTracksException {
        UserDO artist = this.findUserById(userId);
        if (artist.getDiscography() == null) {
            throw new UserNotArtistException();
        }
        if (artist
                .getDiscography()
                .getSingles()
                .stream()
                .anyMatch(s -> s.getPreview().equals(track.getPreview()))) {
            throw new DuplicateTracksException();
        }

        MoodifySingleDO single = new MoodifySingleDO();
        single.setTitle(track.getTitle());
        single.setDuration(track.getDuration());
        single.setPreview(track.getPreview());
        single.setRelease_date(track.getRelease_date());
        single.setCover_small(track.getCover_small());
        single.setCover_big(track.getCover_big());
        single.setArtist_id(artist.getId());
        single.setArtist_name(artist.getUsername());


        artist.getDiscography().getSingles().add(single);
        this.saveUser(artist);
    }

    /**
     * This method is used to search for singles by a query.
     * It takes a string as a parameter representing the query.
     * It generates a wildcard from the query and finds all singles by title like the wildcard from the SingleRepository.
     *
     * @param query The query to search for singles.
     * @return List<MoodifySingleDO> The list of MoodifySingleDO objects of the singles.
     */
    @Override
    public List<MoodifySingleDO> searchSingles(String query) {
        String wildcard = "%" + query + "%";
        return this.SINGLE_REPOSITORY.findAllByTitleLike(wildcard);
    }

    /**
     * This method is used to find the most popular artist of a user.
     * It takes the user's ID as a parameter.
     * It first finds the user.
     * Then it finds the most frequent artist by the user's ID from the UserRepository.
     *
     * @param userId The ID of the user.
     * @return String The name of the most popular artist.
     * @throws UserNotFoundException If the user does not exist.
     */
    @Override
    public String findMostPopularArtist(long userId) throws UserNotFoundException {
        this.findUserById(userId);
        return this.USER_REPOSITORY.findMostFrequentArtistByUserId(userId);
    }

    /**
     * This method checks if a UserDO object is not null.
     *
     * @param user The UserDO object to be checked.
     * @return boolean Returns true if the UserDO object is not null, false otherwise.
     */
    private boolean exists(UserDO user) {
        return user != null;
    }

    /**
     * This method finds an album by its ID from a user's liked albums.
     *
     * @param user The UserDO object of the user.
     * @param albumId The ID of the album.
     * @return AlbumDO The AlbumDO object of the album.
     * @throws AlbumNotFoundException If the album does not exist in the user's liked albums.
     */
    private AlbumDO findAlbumById(UserDO user, long albumId) throws AlbumNotFoundException {
        AlbumDO album = user
                .getPersonalLibrary()
                .getLikedAlbums()
                .stream()
                .filter(al -> al.getAlbum_id_source() == albumId)
                .findFirst()
                .orElse(null);
        if (album == null) {
            throw  new AlbumNotFoundException();
        }

        return album;
    }

    /**
     * This method finds a track by its ID from a playlist.
     *
     * @param playlist The PlaylistDO object of the playlist.
     * @param trackId The ID of the track.
     * @return TrackDO The TrackDO object of the track.
     * @throws TrackNotFoundException If the track does not exist in the playlist.
     */
    private TrackDO findTrackById(PlaylistDO playlist, long trackId) throws TrackNotFoundException {
        TrackDO track = playlist
                .getTracks()
                .stream()
                .filter(trackDO -> trackDO.getId_source() == trackId)
                .findFirst()
                .orElse(null);
        if (track == null) {
            throw new TrackNotFoundException();
        }
        return track;
    }

    /**
     * This method finds the liked tracks playlist of a user.
     *
     * @param user The UserDO object of the user.
     * @return PlaylistDO The PlaylistDO object of the liked tracks playlist.
     * @throws DefaultPlaylistNotFoundException If the liked tracks playlist does not exist.
     */
    private PlaylistDO findLikedTracksOf(UserDO user) throws DefaultPlaylistNotFoundException {
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

    /**
     * This method finds an artist by their ID from a user's liked artists.
     *
     * @param artistId The ID of the artist.
     * @param user The UserDO object of the user.
     * @return ArtistDO The ArtistDO object of the artist.
     * @throws ArtistNotFoundException If the artist does not exist in the user's liked artists.
     */
    private ArtistDO findArtistById(long artistId, UserDO user) throws ArtistNotFoundException {
        ArtistDO artist = user
                .getPersonalLibrary()
                .getLikedArtists()
                .stream()
                .filter(ar -> ar.getArtist_id_source() == artistId)
                .findFirst()
                .orElse(null);

        if (artist == null) {
            throw new ArtistNotFoundException();
        }

        return artist;
    }

    /**
     * This method finds a playlist by its ID from a user's playlists.
     *
     * @param playlistId The ID of the playlist.
     * @param user The UserDO object of the user.
     * @return PlaylistDO The PlaylistDO object of the playlist.
     * @throws PlaylistNotFoundException If the playlist does not exist in the user's playlists.
     */
    private PlaylistDO findPlaylistById(long playlistId, UserDO user) throws PlaylistNotFoundException {
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

    /**
     * This method adds a track to a playlist.
     * It first checks if the playlist already contains the track.
     * If it does, it throws a DuplicateTracksException.
     * Then it adds the track to the playlist and increments the number of songs in the playlist.
     * If the playlist only contains one song after the addition, it sets the big and small pictures of the playlist to the cover of the track.
     *
     * @param playlist The PlaylistDO object of the playlist.
     * @param track The TrackDO object of the track.
     * @throws DuplicateTracksException If the playlist already contains the track.
     */
    private void addTrackTo(PlaylistDO playlist, TrackDO track) throws DuplicateTracksException {

        if (playlist
                .getTracks()
                .stream()
                .anyMatch(tr -> tr.getPreview().equals(track.getPreview()))) {
            throw new DuplicateTracksException();
        }

        playlist.getTracks().add(track);
        playlist.incrementNumberOfSongs();

        if (playlist.getNumber_of_songs() == 1) {
            playlist.setPicture_big(track.getCover_big());
            playlist.setPicture_small(track.getCover_small());
        }

    }

    /**
     * This method removes a track from a playlist.
     * It first checks if the playlist contains the track.
     * If it does not, it throws a TrackNotFoundException.
     * Then it removes the track from the playlist and decrements the number of songs in the playlist.
     * If the playlist does not contain any songs after the removal, it sets the big and small pictures of the playlist to null.
     *
     * @param playlist The PlaylistDO object of the playlist.
     * @param track The TrackDO object of the track.
     * @throws TrackNotFoundException If the track does not exist in the playlist.
     */
    private void deleteTrackFrom(PlaylistDO playlist, TrackDO track) throws TrackNotFoundException {

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
