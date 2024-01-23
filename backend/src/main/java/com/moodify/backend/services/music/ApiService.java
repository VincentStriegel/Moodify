package com.moodify.backend.services.music;

import com.moodify.backend.api.transferobjects.*;

import java.util.List;

/**
 * This interface defines the contract for a service that interacts with a music API.
 * It contains methods for fetching tracks, albums, playlists, and artists by their IDs.
 * It also contains methods for searching for tracks, artists, playlists, and albums based on a search query.
 * Each method returns a transfer object containing the fetched or searched data.
 * The search methods also take a limit parameter that specifies the maximum number of results to return.
 */
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
