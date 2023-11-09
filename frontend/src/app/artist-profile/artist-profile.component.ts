import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BackendCommunicationService } from '../services/backend-communication.service';
import { ArtistTO } from '../types/artistTO';

@Component({
    selector: 'app-artist-profile',
    templateUrl: './artist-profile.component.html',
    styleUrls: ['./artist-profile.component.css'],
})
export class ArtistProfileComponent {
    query!: string;
    artist!: ArtistTO;
    constructor(
        private route: ActivatedRoute,
        private backendCommunicationService: BackendCommunicationService,
    ) {
        this.query = this.route.snapshot.paramMap.get('artistId')!;
        this.backendCommunicationService.getArtist(parseInt(this.query)).subscribe((data) => (this.artist = data));
    }
    ngOnInit(): void {}
}
