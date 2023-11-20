package com.moodify.backend.domain.services.database;

import com.moodify.backend.api.transferobjects.UserTO;
import com.moodify.backend.domain.services.database.databaseobjects.UserDO;
import org.springframework.web.bind.annotation.RequestBody;

public interface DatabaseService {

    UserDO createUser(UserDO userDO) throws Exception;
    void saveUser(UserDO userDO);

    long loginUser(LoginUser loginUser) throws Exception;

    UserTO getUser(long id);

    long addCustomPlaylist(long userId, String playlistTitle);
    //removeCustomPlaylist()
    //addToCustomPlaylist()
    //removeFromCustomPlaylist()

    //addToLikedTracks()
    //addToLikedPlaylists()
    //addToLikedAlbums()
    //addToLikedArtists()

    //removeFromLikedTracks()
    //removeFromLikedArtists()
    //removeFromLikedAlbums()
    //removeFromLikedPlaylists()
}
