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
    /**
     * The current track being played.
     */
    currentTrack!: TrackTO;

    /**
     * The HTML audio element used for playing the track.
     */
    audio!: HTMLAudioElement;

    /**
     * Indicates whether the track is currently playing.
     */
    isPlaying = false;

    /**
     * Indicates whether shuffle mode is enabled.
     */
    isShuffle = false;

    /**
     * The progress of the track playback, in percentage.
     */
    progress = 0;

    /**
     * The volume level of the audio player.
     */
    volume = 1;

    /**
     * Indicates whether the volume slider is visible.
     */
    showVolumeSlider = false;

    /**
     * The position of the track in seconds.
     */
    trackPosition = 0;

    /**
     * The icon representing the volume level.
     */
    volumeSymbol = 'volume_up';

    /**
     * Indicates whether the volume is muted.
     */
    volumeMute = false;

    /**
     * The current volume level before muting.
     */
    currentVolume = 1;

    /**
     * Indicates whether repeat mode is enabled.
     */
    repeat = false;

    /**
     * Indicates whether the current track is marked as favorite.
     */
    isFavorite = false;

    /**
     * The source URL of the track's album cover image.
     */
    imageSrc?: string;

    /**
     * Indicates whether the music player is visible.
     */
    showMusicPlayer = true;

    /**
     * Indicates whether the queue is visible.
     */
    showQueue = false;

    /**
     * The queue of tracks to be played.
     */
    trackQueue: TrackTO[] = [];

    /**
     * The index of the current track in the track queue.
     */
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
            this.trackQueue = musicPlayerService.trackArr;
            this.currentTrackIndex = musicPlayerService.index;
        });
    }

    ngOnInit() {
        this.router.events
            .pipe(filter((event): event is NavigationEnd => event instanceof NavigationEnd))
            .subscribe((event: NavigationEnd) => {
                this.showMusicPlayer = !event.urlAfterRedirects.includes('/party-room/');
                if (!this.showMusicPlayer && this.audio) {
                    this.audio.pause();
                }
            });
    }

    /**
     * Sets up the audio player for the current track.
     */
    setupAudioPlayer(): void {
        if (this.audio) {
            this.resetAudioPlayer();
        } else {
            this.audio = new Audio(this.currentTrack.preview);
        }

        this.audio.addEventListener('timeupdate', this.updateProgress.bind(this));
    }

    /**
     * Resets the audio player to its initial state.
     */
    resetAudioPlayer(): void {
        this.audio.src = this.currentTrack.preview;
        this.audio.removeEventListener('timeupdate', this.updateProgress.bind(this));
        this.audio.pause();
        this.isPlaying = false;
    }

    /**
     * Updates the progress of the track playback.
     */
    updateProgress(): void {
        this.progress = (this.audio.currentTime / this.audio.duration) * 100;

        if (this.progress === 100) {
            this.repeat ? this.repeatTrack() : this.musicPlayerService.getNextTrack();
        }
    }

    /**
     * Repeats the current track from the beginning.
     */
    repeatTrack(): void {
        this.audio.currentTime = 0;
        this.audio.play();
    }

    /**
     * Toggles the play/pause state of the audio player.
     */
    togglePlay() {
        this.isPlaying ? this.audio.pause() : this.audio.play();
        this.isPlaying = !this.isPlaying;
    }

    /**
     * Toggles shuffle mode.
     */
    toggleShuffle() {
        this.isShuffle = !this.isShuffle;
        this.musicPlayerService.toggleShuffle();
    }

    /**
     * Toggles repeat mode.
     */
    toggleRepeat() {
        this.repeat = !this.repeat;
    }

    /**
     * Skips to the next track in the queue.
     */
    skip() {
        this.musicPlayerService.getNextTrack();
    }

    /**
     * Skips to the previous track in the queue.
     */
    previous() {
        this.musicPlayerService.getPreviousTrack();
    }

    /**
     * Toggles the visibility of the volume slider.
     */
    toggleVolumeSlider() {
        this.showVolumeSlider = !this.showVolumeSlider;
    }

    /**
     * Adjusts the position of the track based on the track position slider.
     */
    adjustPosition() {
        const currentAdjustment = (this.trackPosition * this.audio.duration) / 100;
        this.audio.currentTime = currentAdjustment;
    }

    /**
     * Toggles the mute state of the audio player.
     */
    toggleMute() {
        if (!this.volumeMute) {
            this.currentVolume = this.audio.volume;
        }
        this.volumeMute ? (this.audio.volume = this.currentVolume) : (this.audio.volume = 0);
        this.volumeMute = !this.volumeMute;
    }

    /**
     * Likes the current track.
     */
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

    /**
     * Checks if the current track is liked by the user.
     */
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
