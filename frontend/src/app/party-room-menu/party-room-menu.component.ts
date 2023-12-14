import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { BackendCommunicationService } from '../services/backend-communication.service';
import { PersonalLibraryTO } from '../types/personalLibraryTO';
import { DataService } from '../services/data.service';

@Component({
    selector: 'app-party-room-menu',
    templateUrl: './party-room-menu.component.html',
    styleUrls: ['./party-room-menu.component.css'],
})
export class PartyRoomMenuComponent {
    roomID = '';
    personalLibrary!: PersonalLibraryTO;
    playlistID?: number;

    constructor(
        private router: Router,
        private backendCommunicationService: BackendCommunicationService,
        private dataService: DataService,
    ) {
        this.backendCommunicationService
            .getUserPersonalLibrary()
            .subscribe((data) => (this.personalLibrary = data.personalLibrary));
    }

    createRoom() {
        this.roomID = Math.random().toString(36).substring(2, 38);
        if (this.playlistID) {
            this.dataService.setPlaylistID(this.playlistID);
        }
        this.navigateToRoom();
    }

    navigateToRoom() {
        if (this.roomID != '') {
            this.router.navigateByUrl(`/party-room/${this.roomID}`);
        }
    }
}
