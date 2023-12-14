package com.moodify.backend.domain.services.database;

import com.moodify.backend.api.transferobjects.AlbumTO;
import com.moodify.backend.api.transferobjects.ArtistTO;
import com.moodify.backend.api.transferobjects.TrackTO;
import com.moodify.backend.domain.services.database.databaseobjects.PersonalLibraryDO;
import com.moodify.backend.domain.services.database.databaseobjects.PlaylistDO;
import com.moodify.backend.domain.services.database.databaseobjects.UserDO;

public interface DatabaseService {

    UserDO createUser(UserDO userDO) throws Exception;

    void saveUser(UserDO userDO);

    long loginUser(LoginUser loginUser) throws Exception;

    UserDO getUser(long userId) throws Exception;

    Long addCustomPlaylist(long userId, String playlistTitle) throws Exception;

    PersonalLibraryDO removeCustomPlaylist(long userId, long playlistId) throws Exception;

    PersonalLibraryDO addToCustomPlaylist(TrackTO trackTO, long userId, long playlistId) throws Exception;

    PersonalLibraryDO removeFromCustomPlaylist(long userId, long playlistId, long trackId) throws Exception;

    PersonalLibraryDO addToLikedTracks(TrackTO trackTO, long userId) throws Exception;

    PersonalLibraryDO removeFromLikedTracks(long userId, long trackId) throws Exception;

    PersonalLibraryDO addToLikedArtists(ArtistTO artistTO, long userId) throws Exception;

    PersonalLibraryDO removeFromLikedArtists(long artistId, long userId) throws Exception;

    PersonalLibraryDO addToLikedAlbums(AlbumTO albumTO, long userId) throws Exception;

    PersonalLibraryDO removeFromLikedAlbums(long albumId, long userId) throws Exception;

    PlaylistDO getPlaylistById(long playlistId, long userId) throws Exception;
}
