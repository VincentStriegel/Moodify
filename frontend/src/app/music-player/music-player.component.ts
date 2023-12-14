import { Component } from '@angular/core';
import { MusicPlayerService } from '../services/music-player.service';
import { TrackTO } from '../types/trackTO';
import { BackendCommunicationService } from '../services/backend-communication.service';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';

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
    showMusicPlayer = true;
    showQueue = false;
    trackQueue : TrackTO[] = [];
    currentTrackIndex = 0;

    constructor(
        private musicPlayerService: MusicPlayerService,
        private backendCommunicationService: BackendCommunicationService,
        private router: Router,
    ) {
        this.musicPlayerService.nextTrack$.subscribe((track) => {
            if (!track) return;

            this.isShuffle = this.musicPlayerService.isShuffled;
            this.currentTrack = track;
            this.imageSrc = this.currentTrack.album?.cover_small;
            this.setupAudioPlayer();
            this.togglePlay();
            this.isFavorite = false;
            this.checkIfLiked();
            this.trackQueue = musicPlayerService.trackArr
            this.currentTrackIndex = musicPlayerService.index
        });
    }

    ngOnInit() {
        this.router.events.pipe(
            filter((event): event is NavigationEnd => event instanceof NavigationEnd)
        ).subscribe((event: NavigationEnd) => {
            this.showMusicPlayer = !event.urlAfterRedirects.includes('/party-room/');
            if (!this.showMusicPlayer && this.audio) {
              this.audio.pause();
            }
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
    likeSong(): void {
        if (!this.isFavorite) {
            this.backendCommunicationService.addToLikedTracks(this.currentTrack).subscribe(
                () => {
                    this.backendCommunicationService.userProfile.personalLibrary.likedTracks.push(this.currentTrack);
                    this.isFavorite = true;
                },
                (error) => {
                    console.error('Error:', error);
                },
            );
        } else {
            this.backendCommunicationService.removeFromLikedTracks(this.currentTrack.id).subscribe(
                () => {
                    this.backendCommunicationService.userProfile.personalLibrary.likedTracks.splice(
                        this.backendCommunicationService.userProfile.personalLibrary.likedTracks.findIndex(
                            (track) => track.id === this.currentTrack.id,
                        ),
                        1,
                    );
                    this.isFavorite = false;
                },
                (error) => {
                    console.error('Error:', error);
                },
            );
        }
    }

    checkIfLiked(): void {
        if (this.backendCommunicationService.userProfile == undefined || this.isFavorite) return;
        this.isFavorite = this.backendCommunicationService.userProfile.personalLibrary.likedTracks.some((track) => {
            if (track.id === this.currentTrack.id) {
                return true;
            }
            return false;
        });
    }
}
