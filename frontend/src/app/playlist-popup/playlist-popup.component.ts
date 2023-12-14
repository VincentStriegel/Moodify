import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TrackTO } from '../types/trackTO';
import { PersonalLibraryTO } from '../types/personalLibraryTO';
import { BackendCommunicationService } from '../services/backend-communication.service';

@Component({
    selector: 'app-playlist-popup',
    templateUrl: './playlist-popup.component.html',
    styleUrls: ['./playlist-popup.component.css'],
})
export class PlaylistPopupComponent {
    @Input() track!: TrackTO;
    @Output() closePopup: EventEmitter<{playlistId?: number, playlistTitle: string }> = new EventEmitter<{ playlistId?: number, playlistTitle: string }>();
    personalLibrary!: PersonalLibraryTO;
    playlistName = '';
    @Input() isPartyRoomMenu?: boolean;

    constructor(private backendCommunicationService: BackendCommunicationService) {
        this.backendCommunicationService
            .getUserPersonalLibrary()
            .subscribe((data) => (this.personalLibrary = data.personalLibrary));
    }

    createPlaylist(): void {
        this.backendCommunicationService.createPlaylist(this.playlistName).subscribe(
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
            (response) => {
                const newPlaylistId = response as any;
                this.personalLibrary.customPlaylists.push({
                    id: newPlaylistId,
                    title: this.playlistName,
                    picture_small: '',
                    picture_medium: '',
                    picture_big: '',
                    number_of_songs: 0,
                    trackTOList: [],
                });
                this.playlistName = '';
            },
            () => {
                //TODO: handle error
            },
        );
    }

    playlistAction(playlistId: number, playlistTitle?: string): void {
        if (this.isPartyRoomMenu && playlistTitle) {
            this.passPlaylistId(playlistId, playlistTitle);
        } else {
            this.addTrackToPlaylist(playlistId);
        }
    }

    addTrackToPlaylist(playlistId: number): void {
        this.backendCommunicationService.addToCustomPlaylist(playlistId, this.track).subscribe(
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
            () => {
                this.backendCommunicationService.userProfile.personalLibrary.customPlaylists.forEach((playlist) => {
                    if (playlist.id === playlistId) {
                        playlist.trackTOList.push(this.track);
                        playlist.number_of_songs = playlist.trackTOList.length;
                        if (playlist.trackTOList.length === 1) {
                            playlist.picture_small = playlist.trackTOList[0].album.cover_small;
                            playlist.picture_big = playlist.trackTOList[0].album.cover_big;
                        }
                    }
                });
                this.closePopup.emit();
            },
            () => {
                this.closePopup.emit();
            },
        );
    }

    passPlaylistId(playlistId: number, playlistTitle: string): void {
        this.closePopup.emit({ playlistId, playlistTitle });
    }
}
