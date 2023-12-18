import { Injectable } from '@angular/core';
import { TrackTO } from '../types/trackTO';
import { BehaviorSubject, Observable } from 'rxjs';

/**
 * Service responsible for managing the music player functionality.
 */
@Injectable({
    providedIn: 'root',
})
export class MusicPlayerService {
    /**
     * Subject that emits the next track to be played.
     */
    private nextTrackSubject: BehaviorSubject<TrackTO | null> = new BehaviorSubject<TrackTO | null>(null);
    /**
     * Observable that emits the next track to be played.
     */
    nextTrack$: Observable<TrackTO | null> = this.nextTrackSubject.asObservable();

    /**
     * Array of tracks in the playlist.
     */
    trackArr: TrackTO[] = [];
    /**
     * Index of the currently playing track in the playlist.
     */
    index: number = -1;
    /**
     * Indicates whether the playlist is shuffled.
     */
    isShuffled: boolean = false;
    /**
     * Array of tracks in the original order before shuffling.
     */
    originalTrackArr: TrackTO[] = [];
    /**
     * The source of the image associated with the currently playing track.
     */
    imageSrc?: string;

    constructor() {}

    /**
     * Adds a track to the playlist.
     * @param track The track to be added.
     */
    addTrack(track: TrackTO) {
        this.trackArr.push(track);
    }

    /**
     * Adds multiple tracks to the playlist.
     * @param tracks The tracks to be added.
     */
    addTracks(tracks: TrackTO[]) {
        this.trackArr.push(...tracks);
    }

    /**
     * Plays a list of tracks.
     * @param tracks The tracks to be played.
     * @param imageSrc The source of the image associated with the tracks.
     */
    playTracks(tracks: TrackTO[], imageSrc?: string) {
        this.resetPlayer(imageSrc);
        this.trackArr.push(...tracks);
        this.getNextTrack();
    }

    /**
     * Plays a single track.
     * @param track The track to be played.
     * @param imageSrc The source of the image associated with the track.
     */
    playTrack(track: TrackTO, imageSrc?: string) {
        this.resetPlayer(imageSrc);
        this.trackArr.push(track);
        this.getNextTrack();
    }

    /**
     * Retrieves the next track to be played.
     * @returns The next track to be played, or null if there is no next track.
     */
    getNextTrack() {
        if (this.trackArr && this.trackArr.length >= 0 && this.index < this.trackArr.length - 1) {
            this.index++;
            const next = this.trackArr[this.index];
            this.nextTrackSubject.next(next); // Push the new track to all subscribers
            return next;
        } else {
            this.nextTrackSubject.next(null); // Indicate no next track
            return null;
        }
    }

    /**
     * Retrieves the previous track.
     * @returns The previous track, or null if there is no previous track.
     */
    getPreviousTrack() {
        if (this.trackArr && this.trackArr.length >= 0 && this.index > 0) {
            this.index--;
            const next = this.trackArr[this.index];
            this.nextTrackSubject.next(next); // Push the new track to all subscribers
            return next;
        } else {
            this.nextTrackSubject.next(null); // Indicate no next track
            return null;
        }
    }

    /**
     * Toggles the shuffle mode of the playlist.
     */
    toggleShuffle() {
        this.originalTrackArr.length > 0 ? null : (this.originalTrackArr = [...this.trackArr]);
        if (this.isShuffled) {
            this.trackArr = [...this.originalTrackArr];
        } else {
            for (let i = this.trackArr.length - 1; i > 0; i--) {
                const j = Math.floor(Math.random() * (i + 1));
                [this.trackArr[i], this.trackArr[j]] = [this.trackArr[j], this.trackArr[i]];
            }
        }
        this.isShuffled = !this.isShuffled;
    }

    /**
     * Resets the music player.
     * @param imageSrc The source of the image associated with the track.
     */
    resetPlayer(imageSrc?: string) {
        this.originalTrackArr = [];
        this.imageSrc = imageSrc;
        this.isShuffled = false;
        this.index = -1;
        this.trackArr = [];
    }
}
