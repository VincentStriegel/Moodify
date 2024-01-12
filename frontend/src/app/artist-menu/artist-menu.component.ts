import { Component } from '@angular/core';
import { BackendCommunicationService } from '../services/backend-communication.service';
import { DiscographyTO } from '../types/discographyTO';

@Component({
    selector: 'app-artist-menu',
    templateUrl: './artist-menu.component.html',
    styleUrls: ['./artist-menu.component.css'],
})
export class ArtistMenuComponent {
    discography?: DiscographyTO;
    songUploadPopup = false;
    picture?: string;

    constructor(private backendCommunicationService: BackendCommunicationService) {
        this.backendCommunicationService
            .getUserPersonalLibrary()
            .subscribe((data) => (this.discography = data.discography));
    }

    ngOnInit(): void {}

    promoteToArtist(): void {
        this.backendCommunicationService.promoteToArtist(this.picture!).subscribe(
            () => {
                this.backendCommunicationService.isArtist.next(true);
                this.discography = {
                    id: 0,
                    singles: [],
                };
            },
            (error) => {
                console.error('Error:', error);
            },
        );
    }
}
