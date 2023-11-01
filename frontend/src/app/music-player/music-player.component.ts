import { Component } from '@angular/core';
import { MusicPlayerService } from '../services/music-player.service';
import { TrackTO } from '../types/trackTO';

@Component({
  selector: 'app-music-player',
  templateUrl: './music-player.component.html',
  styleUrls: ['./music-player.component.css'],
})
export class MusicPlayerComponent {
  currentTrack!: TrackTO;
  audio!: HTMLAudioElement;
  isPlaying = false;
  isShuffle = false;
  progress = 0;
  volume = 1; 
  showVolumeSlider = false;
  trackPosition = 0;
  volumeSymbol = "volume_up"
  volumeMute = false;
  currentVolume = 1;


  constructor(private musicPlayerService: MusicPlayerService) {
    this.musicPlayerService.nextTrack$.subscribe(track => {
      if(track) {
        this.currentTrack = track;
        if(this.audio){
          this.audio.src = this.currentTrack.preview;
          this.audio.removeEventListener('timeupdate', () => {});
          this.audio.pause();
          this.isPlaying = false; 
        } else {
          this.audio = new Audio(this.currentTrack.preview);
        }
        this.audio.addEventListener('timeupdate', () => {
          this.progress = (this.audio.currentTime / this.audio.duration) * 100;
          if(this.progress === 100){ this.isPlaying = false}
      });
      this.togglePlay();
      }});
  }

  togglePlay() {
    this.isPlaying ? this.audio.pause() : this.audio.play();
    this.isPlaying = !this.isPlaying;
  }

  toggleShuffle() {
    this.isShuffle = !this.isShuffle;
    // Implement shuffle logic here.
  }

  skip() {
    // Implement skip logic.
  }

  previous() {
    // Implement previous track logic.
  }

  toggleVolumeSlider() {
    this.showVolumeSlider = !this.showVolumeSlider;
  }

  adjustPosition(){
    const currentAdjustment = (this.trackPosition * this.audio.duration) / 100 ;
    this.audio.currentTime = currentAdjustment;
  }

  toggleMute(){
    if(!this.volumeMute){
      this.currentVolume = this.audio.volume;
    }
    this.volumeMute ?  this.audio.volume = this.currentVolume: this.audio.volume = 0 ;
    this.volumeMute = !this.volumeMute
  }
}
