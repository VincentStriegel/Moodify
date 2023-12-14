import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  playlistId?: number;

  constructor() { }

  setPlaylistID(playlistID: number) {
    this.playlistId = playlistID;
  }
  getPlaylistID() {
    return this.playlistId!;
  }
}
