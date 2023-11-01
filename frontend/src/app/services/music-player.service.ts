import { Injectable } from '@angular/core';
import { TrackTO } from '../types/trackTO';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MusicPlayerService {

  private nextTrackSubject: BehaviorSubject<TrackTO | null> = new BehaviorSubject<TrackTO | null>(null);
  nextTrack$: Observable<TrackTO | null> = this.nextTrackSubject.asObservable();

  trackArr: TrackTO[] = [];

  constructor() { }

  addTrack(track: TrackTO) {
    this.trackArr.push(track)
  }

  playTrack(track: TrackTO) {
    this.trackArr.push(track);
    this.nextTrackSubject.next(track);
  }
  
  getNextTrack() {
    if(this.trackArr && this.trackArr.length >= 0){
      const next = this.trackArr[this.trackArr.length -1];
      this.trackArr.pop();
      this.nextTrackSubject.next(next);  // Push the new track to all subscribers
      return next;
    } else {
      this.nextTrackSubject.next(null);  // Indicate no next track
      return null;
    }
  }
}
