import { Component } from '@angular/core';
import { TrackTO } from '../types/trackTO';
import { ActivatedRoute } from '@angular/router';
import { BackendCommunicationService } from '../services/backend-communication.service';
import { ArtistTO } from '../types/artistTO';
import { AlbumTO } from '../types/albumTO';
import { PlaylistTO } from '../types/playlistTO';

@Component({
    selector: 'app-search-result',
    templateUrl: './search-result.component.html',
    styleUrls: ['./search-result.component.css'],
})
export class SearchResultComponent {
    tracks!: TrackTO[];
    artists!: ArtistTO[];
    albums!: AlbumTO[];
    playlists!: PlaylistTO[];
    query: string;
    showSongs = true;
    showAlbums = true;
    showArtists = true;
    showPlaylists = true;

    constructor(
        private route: ActivatedRoute,
        private backendCommunicationService: BackendCommunicationService,
    ) {
        this.query = this.route.snapshot.paramMap.get('query') || '';
    }

    ngOnInit(): void {
        this.backendCommunicationService.getSearchResultsTracks(this.query).subscribe((data) => (this.tracks = data));
        this.backendCommunicationService.getSearchResultsArtists(this.query).subscribe((data) => (this.artists = data));
        this.backendCommunicationService.getSearchResultsAlbums(this.query).subscribe((data) => (this.albums = data));
        this.backendCommunicationService
            .getSearchResultsPlaylists(this.query)
            .subscribe((data) => (this.playlists = data));
    }

    toggleFilter(filter: string) {
        const filters: Record<
            string,
            { showSongs: boolean; showAlbums: boolean; showArtists: boolean; showPlaylists: boolean }
        > = {
            all: { showSongs: true, showAlbums: true, showArtists: true, showPlaylists: true },
            songs: { showSongs: true, showAlbums: false, showArtists: false, showPlaylists: false },
            albums: { showSongs: false, showAlbums: true, showArtists: false, showPlaylists: false },
            artists: { showSongs: false, showAlbums: false, showArtists: true, showPlaylists: false },
            playlists: { showSongs: false, showAlbums: false, showArtists: false, showPlaylists: true },
        };

        if (filters[filter]) {
            const selectedFilter = filters[filter];
            this.showSongs = selectedFilter.showSongs;
            this.showSongs = selectedFilter.showSongs;
            this.showAlbums = selectedFilter.showAlbums;
            this.showArtists = selectedFilter.showArtists;
            this.showPlaylists = selectedFilter.showPlaylists;
        }
    }
}
