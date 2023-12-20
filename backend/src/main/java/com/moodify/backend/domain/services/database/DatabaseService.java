package com.moodify.backend.domain.services.database;

import com.moodify.backend.api.transferobjects.AlbumTO;
import com.moodify.backend.api.transferobjects.ArtistTO;
import com.moodify.backend.api.transferobjects.TrackTO;
import com.moodify.backend.domain.services.database.databaseobjects.PlaylistDO;
import com.moodify.backend.domain.services.database.databaseobjects.UserDO;

import java.util.List;

public interface DatabaseService {

    UserDO createUser(UserDO user) throws Exception;

    void saveUser(UserDO user);

    long loginUser(LoginUser loginUser) throws Exception;

    UserDO findUserById(long userId) throws Exception;

    List<UserDO> searchUsers(String query) throws Exception;

    Long createCustomPlaylist(long userId, String playlistTitle) throws Exception;

    void deleteCustomPlaylist(long userId, long playlistId) throws Exception;

    void addToCustomPlaylist(TrackTO trackTO, long userId, long playlistId) throws Exception;

    void deleteFromCustomPlaylist(long userId, long playlistId, long trackId) throws Exception;

    void addToLikedTracks(TrackTO trackTO, long userId) throws Exception;

    void deleteFromLikedTracks(long userId, long trackId) throws Exception;

    void addToLikedArtists(ArtistTO artistTO, long userId) throws Exception;

    void deleteFromLikedArtists(long artistId, long userId) throws Exception;

    void addToLikedAlbums(AlbumTO albumTO, long userId) throws Exception;

    void deleteFromLikedAlbums(long albumId, long userId) throws Exception;

    PlaylistDO findPlaylistById(long playlistId, long userId) throws Exception;
}
