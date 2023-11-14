import { AlbumTO } from './albumTO';
import { TrackTO } from './trackTO';

export type ArtistTO = {
    id: number;
    nb_fans: number;
    name: string;
    picture_small: string;
    picture_big: string;
    albumTOList: AlbumTO[];
    trackTOList: TrackTO[];
};
