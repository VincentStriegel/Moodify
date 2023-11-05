import { AlbumTO } from './albumTO';
import { ArtistTO } from './artistTO';

export type TrackTO = {
    id: number;
    title: string;
    duration: number;
    preview: string;
    release_date: string;

    // TODO
    // private String cover;

    // TODO
    // private String lyrics;

    // TODO
    // private String genre;

    artist: ArtistTO;

    album: AlbumTO;
};
