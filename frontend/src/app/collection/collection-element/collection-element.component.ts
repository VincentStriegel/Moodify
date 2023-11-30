import { Component, Input } from '@angular/core';
import { AlbumTO } from '../../types/albumTO';
import { ArtistTO } from '../../types/artistTO';
import { PlaylistTO } from '../../types/playlistTO';
import { Router } from '@angular/router';
import { BackendCommunicationService } from 'src/app/services/backend-communication.service';
import { MusicPlayerService } from 'src/app/services/music-player.service';

@Component({
    selector: 'app-collection-element',
    templateUrl: './collection-element.component.html',
    styleUrls: ['./collection-element.component.css'],
})
export class CollectionElementComponent {
    @Input() artist?: ArtistTO;
    @Input() album?: AlbumTO;
    @Input() playlist?: PlaylistTO;
    @Input() isCustomPlaylist = false;

    constructor(
        private router: Router,
        private backendCommunicationService: BackendCommunicationService,
        private musicPlayerService: MusicPlayerService,
    ) {}

    goToArtistProfile(artistId: number) {
        this.router.navigateByUrl(`/artist/${artistId}`);
    }

    navigateTo(type: string, id: number) {
        this.isCustomPlaylist
            ? this.router.navigateByUrl(`/collection/custom-playlist/${id}`)
            : this.router.navigateByUrl(`/collection/${type}/${id}`);
    }

    onPlay() {
        const id = this.album?.id || this.playlist?.id || 0;

        if (this.album) {
            this.backendCommunicationService
                .getAlbum(id)
                .subscribe((data) => this.musicPlayerService.playTracks(data.trackTOList, data.cover_small));
        } else if (this.isCustomPlaylist) {
            this.musicPlayerService.playTracks(this.playlist!.trackTOList);
        } else {
            this.backendCommunicationService
                .getPlaylist(id)
                .subscribe((data) => this.musicPlayerService.playTracks(data.trackTOList));
        }
    }

    formatFansCount(fans: number): string {
        if (fans >= 1000000) {
            return (fans / 1000000).toFixed(1) + 'M';
        } else if (fans >= 1000) {
            return (fans / 1000).toFixed(1) + 'K';
        } else {
            return fans.toString();
        }
    }
}
