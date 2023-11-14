import { TrackTO } from './trackTO';

export type AlbumTO = {
    id: number;
    title: string;
    cover_small: string;
    cover_big: string;
    release_date: string;
    trackTOList: TrackTO[];
    number_of_songs: number;
};
