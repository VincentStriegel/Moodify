import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TrackTO } from '../types/trackTO';
import { MusicPlayerService } from '../services/music-player.service';
import { Router } from '@angular/router';
import { BackendCommunicationService } from '../services/backend-communication.service';
import { SnackbarService } from '../services/snackbar.service';

@Component({
    selector: 'app-track-element',
    templateUrl: './track-element.component.html',
    styleUrls: ['./track-element.component.css'],
})
export class TrackElementComponent {
    trackCover?: string;
    isPlaying = false;
    Math = Math;
    @Input({ required: true }) track!: TrackTO;
    @Input() imageSrc?: string;
    @Input() isPlayingTrack?: boolean;
    @Input() isPartyRoom?: boolean;
    @Input() squareLayout?: boolean;
    @Input() playlistId?: number;
    @Input() tinyLayout?: boolean;
    @Input() isAlbum?: boolean;
    @Output() suggestSongPartyRoom: EventEmitter<void> = new EventEmitter<void>();
    @Output() removeFromPlaylistEvent: EventEmitter<void> = new EventEmitter<void>();
    playlistPopup = false;
    isFavorite = false;

    constructor(
        public musicPlayerService: MusicPlayerService,
        public router: Router,
        public backendCommunicationService: BackendCommunicationService,
        public snackbarService: SnackbarService,
    ) {
        // this.checkIfLiked();
    }

    ngOnInit(): void {
        this.checkIfLiked();
    }

    togglePlay() {
        this.musicPlayerService.playTrack(this.track, this.imageSrc);
    }

    goToArtistProfile(artistId: number) {
        this.router.navigateByUrl(`/artist/${artistId}`);
    }
    suggestSong(): void {
        this.suggestSongPartyRoom.emit();
    }
    likeSong(): void {
        if (!this.isFavorite) {
            this.backendCommunicationService.addToLikedTracks(this.track).subscribe(
                () => {
                    this.backendCommunicationService.userProfile.personalLibrary.likedTracks.push(this.track);
                    this.isFavorite = true;
                    this.snackbarService.openSuccessSnackBar(
                        this.track.album.cover_small,
                        this.track.title,
                        'added to your liked tracks',
                    );
                },
                (error) => {
                    console.error('Error:', error);
                },
            );
        } else {
            this.backendCommunicationService.removeFromLikedTracks(this.track.id).subscribe(
                () => {
                    this.backendCommunicationService.userProfile.personalLibrary.likedTracks.splice(
                        this.backendCommunicationService.userProfile.personalLibrary.likedTracks.findIndex(
                            (track) => track.id === this.track.id,
                        ),
                        1,
                    );
                    this.isFavorite = false;
                    this.snackbarService.openSuccessSnackBar(
                        this.track.album.cover_small,
                        this.track.title,
                        'removed from your liked tracks',
                    );
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
            if (track.id === this.track.id) {
                return true;
            }
            return false;
        });
    }

    closePopup(): void {
        this.playlistPopup = false;
    }

    removeFromPlaylist(): void {
        this.backendCommunicationService.removeFromCustomPlaylist(this.playlistId!, this.track.id).subscribe(
            () => {
                this.removeFromPlaylistEvent.emit();
                this.snackbarService.openSuccessSnackBar(
                    this.track.album.cover_small,
                    this.track.title,
                    'removed from playlist',
                );
            },
            (error) => {
                console.error('Error:', error);
            },
        );
    }

    queueTrack(): void {
        this.musicPlayerService.addTrack(this.track);
        this.snackbarService.openSuccessSnackBar(this.track.album.cover_small, this.track.title, 'added to queue');
    }
}
