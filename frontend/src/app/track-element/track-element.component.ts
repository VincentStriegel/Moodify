import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TrackTO } from '../types/trackTO';
import { MusicPlayerService } from '../services/music-player.service';
import { Router } from '@angular/router';
import { BackendCommunicationService } from '../services/backend-communication.service';

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
    @Output() suggestSongPartyRoom: EventEmitter<void> = new EventEmitter<void>();
    @Output() removeFromPlaylistEvent: EventEmitter<void> = new EventEmitter<void>();
    playlistPopup = false;

    constructor(
        private musicPlayerService: MusicPlayerService,
        private router: Router,
        private backendCommunicationService: BackendCommunicationService,
    ) {}

    ngOnInit(): void {}

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
        this.backendCommunicationService.addToLikedTracks(this.track).subscribe(
            () => {
                this.backendCommunicationService.userProfile.personalLibrary.likedTracks.push(this.track);
            },
            (error) => {
                console.error('Error:', error);
            },
        );
    }

    closePopup(): void {
        this.playlistPopup = false;
    }

    removeFromPlaylist(): void {
        this.backendCommunicationService.removeFromCustomPlaylist(this.playlistId!, this.track.id).subscribe(
            () => {
                this.removeFromPlaylistEvent.emit();
            },
            (error) => {
                console.error('Error:', error);
            },
        );
    }
}
