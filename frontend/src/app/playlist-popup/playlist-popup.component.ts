import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TrackTO } from '../types/trackTO';
import { PersonalLibraryTO } from '../types/personalLibraryTO';
import { BackendCommunicationService } from '../services/backend-communication.service';
import { SnackbarService } from '../services/snackbar.service';

/**
 * Used to create playlists and or add songs to playlists.
 */
@Component({
    selector: 'app-playlist-popup',
    templateUrl: './playlist-popup.component.html',
    styleUrls: ['./playlist-popup.component.css'],
})
export class PlaylistPopupComponent {
    /**
     * The input track.
     */
    @Input() track!: TrackTO;

    /**
     * The output event emitter for closing the popup.
     */
    @Output() closePopup: EventEmitter<{ playlistId?: number; playlistTitle: string }> = new EventEmitter<{
        playlistId?: number;
        playlistTitle: string;
    }>();

    personalLibrary!: PersonalLibraryTO;
    playlistName = '';
    @Input() isPartyRoomMenu?: boolean;
    @Input() creationOnly?: boolean;
    constructor(
        private backendCommunicationService: BackendCommunicationService,
        private snackbarService: SnackbarService,
    ) {
        this.backendCommunicationService
            .getUserPersonalLibrary()
            .subscribe((data) => (this.personalLibrary = data.personalLibrary));
    }

    /**
     * Creates a new playlist.
     */
    createPlaylist(): void {
        this.backendCommunicationService.createPlaylist(this.playlistName).subscribe(
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
            (response) => {
                const newPlaylistId = response.body as any;
                this.personalLibrary.customPlaylists.push({
                    id: newPlaylistId,
                    title: this.playlistName,
                    picture_small: '',
                    picture_medium: '',
                    picture_big: '',
                    number_of_songs: 0,
                    trackTOList: [],
                    source: '',
                });
                if (this.creationOnly) {
                    this.closePopup.emit();
                }
                this.snackbarService.openSuccessSnackBar(
                    'assets/music-placeholder.png',
                    `Playlist ${this.playlistName}`,
                    'created',
                );
                this.playlistName = '';
            },
            () => {
                //TODO: handle error
            },
        );
    }

    /**
     * Performs an action on the playlist.
     * @param playlistId - The playlist ID.
     * @param playlistTitle - The playlist title.
     */
    playlistAction(playlistId: number, playlistTitle?: string): void {
        if (this.isPartyRoomMenu && playlistTitle) {
            this.passPlaylistId(playlistId, playlistTitle);
        } else {
            this.addTrackToPlaylist(playlistId);
        }
    }

    /**
     * Adds the track to the playlist.
     * @param playlistId - The playlist ID.
     */
    addTrackToPlaylist(playlistId: number): void {
        let currentPlaylistName = '';
        this.backendCommunicationService.addToCustomPlaylist(playlistId, this.track).subscribe(
            () => {
                this.backendCommunicationService.userProfile.personalLibrary.customPlaylists.forEach((playlist) => {
                    if (playlist.id === playlistId) {
                        playlist.trackTOList.push(this.track);
                        playlist.number_of_songs = playlist.trackTOList.length;
                        currentPlaylistName = playlist.title;
                        if (playlist.trackTOList.length === 1) {
                            playlist.picture_small = playlist.trackTOList[0].album.cover_small;
                            playlist.picture_big = playlist.trackTOList[0].album.cover_big;
                        }
                    }
                });
                this.snackbarService.openSuccessSnackBar(
                    this.track.album.cover_small,
                    this.track.title,
                    `added to ${currentPlaylistName}`,
                );
                this.closePopup.emit();
            },
            () => {
                this.closePopup.emit();
            },
        );
    }

    /**
     * Passes the playlist ID and title.
     * @param playlistId - The playlist ID.
     * @param playlistTitle - The playlist title.
     */
    passPlaylistId(playlistId: number, playlistTitle: string): void {
        this.closePopup.emit({ playlistId, playlistTitle });
    }
}
