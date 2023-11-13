import { Injectable } from '@angular/core';
import { TrackTO } from '../types/trackTO';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class MusicPlayerService {
    private nextTrackSubject: BehaviorSubject<TrackTO | null> = new BehaviorSubject<TrackTO | null>(null);
    nextTrack$: Observable<TrackTO | null> = this.nextTrackSubject.asObservable();

    trackArr: TrackTO[] = [];
    index: number = -1;
    isShuffled: boolean = false;
    originalTrackArr: TrackTO[] = [];
    imageSrc?: string;

    constructor() {}

    addTrack(track: TrackTO) {
        this.trackArr.push(track);
    }

    addTracks(tracks: TrackTO[]) {
        this.trackArr.push(...tracks);
    }

    playTracks(tracks: TrackTO[], imageSrc?: string) {
        this.resetPlayer(imageSrc);
        this.trackArr.push(...tracks);
        this.getNextTrack();
    }

    playTrack(track: TrackTO, imageSrc?: string) {
        this.resetPlayer(imageSrc);
        this.trackArr.push(track);
        this.getNextTrack();
    }

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

    resetPlayer(imageSrc?: string) {
        this.originalTrackArr = [];
        this.imageSrc = imageSrc;
        this.isShuffled = false;
        this.index = -1;
        this.trackArr = [];
    }
}
