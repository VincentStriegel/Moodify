import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TrackTO } from '../types/trackTO';
import { MusicPlayerService } from '../services/music-player.service';
import { Router } from '@angular/router';
import { BackendCommunicationService } from '../services/backend-communication.service';
import { SnackbarService } from '../services/snackbar.service';

/**
 * Track element component, which is used to display and interact with a track.
 */
@Component({
    selector: 'app-track-element',
    templateUrl: './track-element.component.html',
    styleUrls: ['./track-element.component.css'],
})
export class TrackElementComponent {
    trackCover?: string;
    isPlaying = false;
    Math = Math;

    /**
     * The input property for the track.
     */
    @Input({ required: true }) track!: TrackTO;

    /**
     * The input property for the image source.
     */
    @Input() imageSrc?: string;

    /**
     * The input property for the playing track.
     */
    @Input() isPlayingTrack?: boolean;

    /**
     * The input property for the party room.
     */
    @Input() isPartyRoom?: boolean;

    /**
     * The input property for the square layout.
     */
    @Input() squareLayout?: boolean;

    /**
     * The input property for the playlist ID.
     */
    @Input() playlistId?: number;

    /**
     * The input property for the tiny layout.
     */
    @Input() tinyLayout?: boolean;

    /**
     * The input property for the album.
     */
    @Input() isAlbum?: boolean;

    /**
     * The output event for suggesting a song in the party room.
     */
    @Output() suggestSongPartyRoom: EventEmitter<void> = new EventEmitter<void>();

    /**
     * The output event for removing a track from the playlist.
     */
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

    /**
     * Toggles the play state of the track.
     */
    togglePlay() {
        this.musicPlayerService.playTrack(this.track, this.imageSrc);
    }

    /**
     * Navigates to the artist profile page.
     * @param artistId - The ID of the artist.
     * @param artistSource - The source of the artist.
     */
    goToArtistProfile(artistId: number, artistSource: string) {
        this.router.navigateByUrl(`/artist/${artistId}/${artistSource}`);
    }

    /**
     * Emits the suggestSongPartyRoom event.
     */
    suggestSong(): void {
        this.suggestSongPartyRoom.emit();
    }

    /**
     * Likes the song and adds it to the liked tracks.
     */
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

    /**
     * Checks if the track is liked by the user.
     */
    checkIfLiked(): void {
        if (this.backendCommunicationService.userProfile == undefined || this.isFavorite) return;
        this.isFavorite = this.backendCommunicationService.userProfile.personalLibrary.likedTracks.some((track) => {
            if (track.id === this.track.id) {
                return true;
            }
            return false;
        });
    }

    /**
     * Closes the playlist popup.
     */
    closePopup(): void {
        this.playlistPopup = false;
    }

    /**
     * Removes the track from the playlist.
     */
    removeFromPlaylist(): void {
        this.backendCommunicationService.removeFromCustomPlaylist(this.playlistId!, this.track.id).subscribe(
            () => {
                this.removeFromPlaylistEvent.emit();
                this.snackbarService.openSuccessSnackBar(
                    this.track.album.cover_small,
                    this.track.title,
                    'removed from playlist',
                );
                this.backendCommunicationService.userProfile.personalLibrary.customPlaylists.forEach((playlist) => {
                    if (playlist.id === this.playlistId) {
                        playlist.number_of_songs = playlist.trackTOList.length;
                    }
                });
            },
            (error) => {
                console.error('Error:', error);
            },
        );
    }

    /**
     * Adds the track to the music player queue.
     */
    queueTrack(): void {
        this.musicPlayerService.addTrack(this.track);
        this.snackbarService.openSuccessSnackBar(this.track.album.cover_small, this.track.title, 'added to queue');
    }
}
