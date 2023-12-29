package com.moodify.backend.services.database;

import com.moodify.backend.api.transferobjects.AlbumTO;
import com.moodify.backend.api.transferobjects.ArtistTO;
import com.moodify.backend.api.transferobjects.TrackTO;
import com.moodify.backend.services.database.exceptions.login.UserCredentialsException;
import com.moodify.backend.services.database.exceptions.login.WrongPasswordException;
import com.moodify.backend.services.database.exceptions.profiles.*;
import com.moodify.backend.services.database.exceptions.registration.*;
import com.moodify.backend.services.database.objects.PlaylistDO;
import com.moodify.backend.services.database.objects.UserDO;
import com.moodify.backend.services.database.util.LoginUser;

import java.util.List;

public interface DatabaseService {

    UserDO createUser(UserDO user) throws
            InvalidEmailException,
            InvalidUsernameException,
            InvalidPasswordException,
            RegisteredEmailException,
            RegisteredUsernameException;

    void saveUser(UserDO user);

    long loginUser(LoginUser loginUser) throws UserCredentialsException, WrongPasswordException;

    UserDO findUserById(long userId) throws UserNotFoundException;

    List<UserDO> searchUsers(String query);

    List<PlaylistDO> searchPlaylists(String query);

    Long createCustomPlaylist(long userId, String playlistTitle) throws UserNotFoundException;

    void deleteCustomPlaylist(long userId, long playlistId) throws
            UserNotFoundException,
            PlaylistNotFoundException,
            DefaultPlaylistException;

    void addToCustomPlaylist(TrackTO trackTO, long userId, long playlistId) throws
            UserNotFoundException,
            PlaylistNotFoundException,
            DuplicateTracksException;

    void deleteFromCustomPlaylist(long userId, long playlistId, long trackId) throws
            UserNotFoundException,
            PlaylistNotFoundException,
            TrackNotFoundException;

    void addToLikedTracks(TrackTO trackTO, long userId) throws
            UserNotFoundException,
            DefaultPlaylistNotFoundException,
            DuplicateTracksException;

    void deleteFromLikedTracks(long userId, long trackId) throws
            UserNotFoundException,
            DefaultPlaylistNotFoundException,
            TrackNotFoundException;

    void addToLikedArtists(ArtistTO artistTO, long userId) throws UserNotFoundException, DuplicateArtistsException;

    void deleteFromLikedArtists(long artistId, long userId) throws UserNotFoundException, ArtistNotFoundException;

    void addToLikedAlbums(AlbumTO albumTO, long userId) throws UserNotFoundException, DuplicateAlbumsException;

    void deleteFromLikedAlbums(long albumId, long userId) throws UserNotFoundException, AlbumNotFoundException;

    PlaylistDO findPlaylistByIdFromUser(long playlistId, long userId) throws UserNotFoundException, PlaylistNotFoundException;

    PlaylistDO findPlaylistById(long playlistId) throws PlaylistNotFoundException;
}
