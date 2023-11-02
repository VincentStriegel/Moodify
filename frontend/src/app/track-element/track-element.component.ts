import { Component, Input, OnInit } from '@angular/core';
import { TrackTO } from '../types/trackTO';
import { MusicPlayerService } from '../services/music-player.service';

@Component({
  selector: 'app-track-element',
  templateUrl: './track-element.component.html',
  styleUrls: ['./track-element.component.css']
})
export class TrackElementComponent {
  trackCover?: string;
  isPlaying = false;
  duration!: string;
  Math = Math;
  @Input({ required: true }) track!: TrackTO;
  
  constructor( private musicPlayerService: MusicPlayerService) {}

  ngOnInit(): void {
    this.duration = Math.floor(this.track.duration / 60) + ':' + (this.track.duration % 60).toString().padStart(2, '0');
  }

  togglePlay(){
    //this.isPlaying = !this.isPlaying;
    this.musicPlayerService.playTrack(this.track);
  }
}