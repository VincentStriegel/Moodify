import { TrackTO } from './trackTO';

export type PlaylistTO = {
    id: number;
    title: string;
    picture_small: string;
    picture_medium: string;
    picture_big: string;
    number_of_songs: number;
    trackTOList: TrackTO[];
};
