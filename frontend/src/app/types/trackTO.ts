import { AlbumTO } from './albumTO';
import { ArtistTO } from './artistTO';

export type TrackTO = {
    id: number;
    title: string;
    duration: number;
    preview: string;
    release_date: string;
    cover_small: string;
    cover_big: string;
    source: string;
    artist: ArtistTO;
    album: AlbumTO;
};
