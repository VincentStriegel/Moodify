import { TestBed } from '@angular/core/testing';
import { TrackTO } from '../types/trackTO';
import { MusicPlayerService } from './music-player.service';

describe('MusicPlayerService', () => {
    let service: MusicPlayerService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(MusicPlayerService);
    });

    const track: TrackTO = {
        id: 1,
        title: 'Track 1',
        duration: 0,
        preview: '',
        release_date: '',
        artist: {
            id: 0,
            nb_fans: 0,
            name: '',
            picture_small: '',
            picture_big: '',
            albumTOList: [],
            trackTOList: [],
        },
        album: {
            id: 0,
            title: '',
            cover_small: '',
            cover_big: '',
            release_date: '',
            trackTOList: [],
            number_of_songs: 0,
        },
    };

    const track2: TrackTO = {
        id: 2,
        title: 'Track 2',
        duration: 0,
        preview: '',
        release_date: '',
        artist: {
            id: 0,
            nb_fans: 0,
            name: '',
            picture_small: '',
            picture_big: '',
            albumTOList: [],
            trackTOList: [],
        },
        album: {
            id: 0,
            title: '',
            cover_small: '',
            cover_big: '',
            release_date: '',
            trackTOList: [],
            number_of_songs: 0,
        },
    };

    const track3: TrackTO = {
        id: 3,
        title: 'Track 3',
        duration: 0,
        preview: '',
        release_date: '',
        artist: {
            id: 0,
            nb_fans: 0,
            name: '',
            picture_small: '',
            picture_big: '',
            albumTOList: [],
            trackTOList: [],
        },
        album: {
            id: 0,
            title: '',
            cover_small: '',
            cover_big: '',
            release_date: '',
            trackTOList: [],
            number_of_songs: 0,
        },
    };

    const track4: TrackTO = {
        id: 4,
        title: 'Track 4',
        duration: 0,
        preview: '',
        release_date: '',
        artist: {
            id: 0,
            nb_fans: 0,
            name: '',
            picture_small: '',
            picture_big: '',
            albumTOList: [],
            trackTOList: [],
        },
        album: {
            id: 0,
            title: '',
            cover_small: '',
            cover_big: '',
            release_date: '',
            trackTOList: [],
            number_of_songs: 0,
        },
    };

    const tracks: TrackTO[] = [track, track2, track3, track4];

    it('should add a track to the playlist', () => {
        service.addTrack(track);
        expect(service.trackArr).toContain(track);
    });

    it('should add multiple tracks to the playlist', () => {
        service.addTracks(tracks);
        expect(service.trackArr).toEqual(tracks);
    });

    it('should play a list of tracks', () => {
        service.playTracks(tracks);
        expect(service.trackArr).toEqual(tracks);
        expect(service.index).toBe(0);
        service.nextTrack$.subscribe((track) => {
            expect(track).toEqual(tracks[0]);
        });
    });

    it('should play a single track', () => {
        service.playTrack(track);
        expect(service.trackArr).toEqual([track]);
        expect(service.index).toBe(0);
        service.nextTrack$.subscribe((track) => {
            expect(track).toEqual(tracks[0]);
        });
    });

    it('should retrieve the next track', () => {
        service.playTracks(tracks);
        const nextTrack = service.getNextTrack();
        expect(nextTrack).toEqual(tracks[1]);
        expect(service.index).toBe(1);
        service.nextTrack$.subscribe((track) => {
            expect(track).toEqual(tracks[1]);
        });
    });

    it('should retrieve the previous track', () => {
        service.playTracks(tracks);
        service.getNextTrack(); // Move to the next track
        const previousTrack = service.getPreviousTrack();
        expect(previousTrack).toEqual(tracks[0]);
        expect(service.index).toBe(0);
        service.nextTrack$.subscribe((track) => {
            expect(track).toEqual(tracks[0]);
        });
    });

    it('should toggle the shuffle mode of the playlist', () => {
        service.playTracks(tracks);
        service.toggleShuffle();
        expect(service.isShuffled).toBe(true);
        expect(service.originalTrackArr).toEqual(tracks);
        // TODO
        // expect(service.trackArr).not.toEqual(tracks);
        service.toggleShuffle();
        expect(service.isShuffled).toBe(false);
        expect(service.trackArr).toEqual(tracks);
    });

    it('should reset the music player', () => {
        service.playTracks(tracks);
        service.resetPlayer();
        expect(service.originalTrackArr).toEqual([]);
        expect(service.imageSrc).toBeUndefined();
        expect(service.isShuffled).toBe(false);
        expect(service.index).toBe(-1);
        expect(service.trackArr).toEqual([]);
    });
});
