package com.moodify.backend.services.music;

import com.moodify.backend.api.transferobjects.*;

import java.util.List;

public interface ApiService {
    TrackTO getTrack(long id);

    AlbumTO getAlbum(long id);

    PlaylistTO getPlaylist(long id);

    ArtistTO getArtist(long id);

    List<TrackTO> getTrackSearch(String query, long limit);

    List<ArtistTO> getArtists(String query, long limit);

    List<AlbumTO> getAlbums(String query, long limit);

    List<PlaylistTO> getPlaylists(String query, long limit);

}
