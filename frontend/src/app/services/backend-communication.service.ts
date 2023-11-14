import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, shareReplay } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TrackTO } from '../types/trackTO';
import { AlbumTO } from '../types/albumTO';
import { ArtistTO } from '../types/artistTO';
import { PlaylistTO } from '../types/playlistTO';

@Injectable({
    providedIn: 'root',
})
export class BackendCommunicationService {
    private apiServerURL = environment.apiServerURL;
    constructor(private http: HttpClient) {}

    getSong(songId: number): Observable<TrackTO> {
        return this.http.get<TrackTO>(`${this.apiServerURL}/music/track/${songId}`).pipe(shareReplay(1));
    }

    getArtist(artistId: number): Observable<ArtistTO> {
        return this.http.get<ArtistTO>(`${this.apiServerURL}/music/artist/${artistId}`).pipe(shareReplay(1));
    }

    getAlbum(albumId: number): Observable<AlbumTO> {
        return this.http.get<AlbumTO>(`${this.apiServerURL}/music/album/${albumId}`).pipe(shareReplay(1));
    }

    getPlaylist(playlistId: number): Observable<PlaylistTO> {
        return this.http.get<PlaylistTO>(`${this.apiServerURL}/music/playlist/${playlistId}`).pipe(shareReplay(1));
    }

    getSearchResultsTracks(searchQuery: string): Observable<TrackTO[]> {
        return this.http.get<TrackTO[]>(`${this.apiServerURL}/music/search/tracks/${searchQuery}`).pipe(shareReplay(1));
    }

    getSearchResultsAlbums(searchQuery: string): Observable<AlbumTO[]> {
        return this.http.get<AlbumTO[]>(`${this.apiServerURL}/music/search/albums/${searchQuery}`).pipe(shareReplay(1));
    }

    getSearchResultsArtists(searchQuery: string): Observable<ArtistTO[]> {
        return this.http
            .get<ArtistTO[]>(`${this.apiServerURL}/music/search/artists/${searchQuery}`)
            .pipe(shareReplay(1));
    }

    getSearchResultsPlaylists(searchQuery: string): Observable<PlaylistTO[]> {
        return this.http
            .get<PlaylistTO[]>(`${this.apiServerURL}/music/search/playlists/${searchQuery}`)
            .pipe(shareReplay(1));
    }


    // Login
    login(username: string, password: string): Observable<any> {
        return this.http.post<any>(`${this.apiServerURL}/login`, { username, password });
    }

    // Register
    register(username: string, password: string , email: string): Observable<any> {
        return this.http.post<any>(`${this.apiServerURL}/register/submit`, { username, password, email });
    }
}
