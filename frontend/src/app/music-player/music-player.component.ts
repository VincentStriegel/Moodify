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
    volumeSymbol = 'volume_up';
    volumeMute = false;
    currentVolume = 1;
    repeat = false;
    isFavorite = false;
    imageSrc?: string;

    constructor(private musicPlayerService: MusicPlayerService) {
        this.musicPlayerService.nextTrack$.subscribe((track) => {
            if (!track) return;

            this.isShuffle = this.musicPlayerService.isShuffled;
            this.currentTrack = track;
            this.imageSrc = this.currentTrack.album?.cover_small || this.musicPlayerService.imageSrc;
            this.setupAudioPlayer();
            this.togglePlay();
        });
    }

    setupAudioPlayer(): void {
        if (this.audio) {
            this.resetAudioPlayer();
        } else {
            this.audio = new Audio(this.currentTrack.preview);
        }

        this.audio.addEventListener('timeupdate', this.updateProgress.bind(this));
    }

    resetAudioPlayer(): void {
        this.audio.src = this.currentTrack.preview;
        this.audio.removeEventListener('timeupdate', this.updateProgress.bind(this));
        this.audio.pause();
        this.isPlaying = false;
    }

    updateProgress(): void {
        this.progress = (this.audio.currentTime / this.audio.duration) * 100;

        if (this.progress === 100) {
            this.repeat ? this.repeatTrack() : this.musicPlayerService.getNextTrack();
        }
    }

    repeatTrack(): void {
        this.audio.currentTime = 0;
        this.audio.play();
    }

    togglePlay() {
        this.isPlaying ? this.audio.pause() : this.audio.play();
        this.isPlaying = !this.isPlaying;
    }

    toggleShuffle() {
        this.isShuffle = !this.isShuffle;
        this.musicPlayerService.toggleShuffle();
    }

    toggleRepeat() {
        this.repeat = !this.repeat;
    }

    skip() {
        this.musicPlayerService.getNextTrack();
    }

    previous() {
        this.musicPlayerService.getPreviousTrack();
    }

    toggleVolumeSlider() {
        this.showVolumeSlider = !this.showVolumeSlider;
    }

    adjustPosition() {
        const currentAdjustment = (this.trackPosition * this.audio.duration) / 100;
        this.audio.currentTime = currentAdjustment;
    }

    toggleMute() {
        if (!this.volumeMute) {
            this.currentVolume = this.audio.volume;
        }
        this.volumeMute ? (this.audio.volume = this.currentVolume) : (this.audio.volume = 0);
        this.volumeMute = !this.volumeMute;
    }
}
