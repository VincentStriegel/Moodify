import { AlbumTO } from './albumTO';
import { ArtistTO } from './artistTO';
import { PlaylistTO } from './playlistTO';
import { TrackTO } from './trackTO';

export type PersonalLibraryTO = {
    likedTracks: TrackTO[];
    likedArtists: ArtistTO[];
    likedAlbums: AlbumTO[];
    likedPlaylists: PlaylistTO[];
    customPlaylists: PlaylistTO[];
};
