import { Component } from '@angular/core';
import { BackendCommunicationService } from '../services/backend-communication.service';
import { userTO } from '../types/userTO';

@Component({
    selector: 'app-user-library',
    templateUrl: './user-library.component.html',
    styleUrls: ['./user-library.component.css'],
})
export class UserLibraryComponent {
    userProfile!: userTO;
    playlistName = '';
    newPlaylistId?: number;
    playlistPopup = false;
    constructor(private backendCommunicationService: BackendCommunicationService) {
        this.backendCommunicationService.getUserPersonalLibrary().subscribe((data) => (this.userProfile = data));
    }

    createPlaylist(): void {
        this.backendCommunicationService.createPlaylist(this.playlistName).subscribe(
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
            () => {
                this.playlistName = '';
            },
            () => {
                //TODO: handle error
            },
        );
    }
}
