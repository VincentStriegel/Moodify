import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'app-party-room-menu',
    templateUrl: './party-room-menu.component.html',
    styleUrls: ['./party-room-menu.component.css'],
})
export class PartyRoomMenuComponent {
    roomID = '';

    constructor(private router: Router) {}

    createRoom() {
        this.router.navigateByUrl('/party-room/test');
    }

    navigateToRoom() {
        if (this.roomID != '') {
            this.router.navigateByUrl(`/party-room/${this.roomID}`);
        }
    }
}
