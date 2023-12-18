import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BackendCommunicationService } from '../services/backend-communication.service';
import { ArtistTO } from '../types/artistTO';
import { SnackbarService } from '../services/snackbar.service';

@Component({
    selector: 'app-artist-profile',
    templateUrl: './artist-profile.component.html',
    styleUrls: ['./artist-profile.component.css'],
})
export class ArtistProfileComponent {
    query!: string;
    artist!: ArtistTO;
    isLiked = false;
    constructor(
        private route: ActivatedRoute,
        private backendCommunicationService: BackendCommunicationService,
        private snackbarService: SnackbarService,
    ) {
        this.query = this.route.snapshot.paramMap.get('artistId')!;
        this.backendCommunicationService
            .getArtist(parseInt(this.query))
            .subscribe((data) => ((this.artist = data), this.checkIfLiked()));
    }
    ngOnInit(): void {}

    checkIfLiked(): void {
        if (this.backendCommunicationService.userProfile && this.artist) {
            this.isLiked = this.backendCommunicationService.userProfile.personalLibrary.likedArtists.some(
                (artist) => artist.id === this.artist.id,
            );
        }
    }

    likeArtist(): void {
        this.backendCommunicationService.addToLikedArtists(this.artist).subscribe(
            () => {
                this.isLiked = true;
                this.backendCommunicationService.userProfile.personalLibrary.likedArtists.push(this.artist);
                this.snackbarService.openSuccessSnackBar(
                    this.artist.picture_small,
                    this.artist.name,
                    'added to your liked artists',
                );

            },
            (error) => {
                console.error('Error:', error);
            },
        );
    }

    unlikeArtist(): void {
        this.backendCommunicationService.removeLikedArtists(this.artist.id).subscribe(
            () => {
                this.isLiked = false;
                this.backendCommunicationService.userProfile.personalLibrary.likedArtists.splice(
                    this.backendCommunicationService.userProfile.personalLibrary.likedArtists.findIndex(
                        (artist) => artist.id === this.artist.id,
                    ),
                    1,
                );
                this.snackbarService.openSuccessSnackBar(
                    this.artist.picture_small,
                    this.artist.name,
                    'removed from your liked artists',
                );
            },
            (error) => {
                console.error('Error:', error);
            },
        );
    }
}
