package com.moodify.backend.domain.services;

import com.moodify.backend.api.transferobjects.*;

import java.util.List;

public interface ApiService {

    TrackTO getTrack (long id);
    List<TrackTO> getTrackSearch (String query);

    List<ArtistTO> getArtists(String query);

    List<AlbumTO> getAlbums(String query);

    List<PlaylistTO> getPlaylists(String query);

}
