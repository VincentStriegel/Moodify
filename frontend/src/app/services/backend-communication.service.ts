import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, shareReplay, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TrackTO } from '../types/trackTO';
import { AlbumTO } from '../types/albumTO';
import { ArtistTO } from '../types/artistTO';
import { PlaylistTO } from '../types/playlistTO';
import { userTO } from '../types/userTO';

@Injectable({
    providedIn: 'root',
})
export class BackendCommunicationService {
    userId?: number;
    userProfile!: userTO;
    private apiServerURL = environment.apiServerURL;
    constructor(private http: HttpClient) {
        if (!environment.production) {
            this.userId = 1;
        }
    }

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

    getUserPersonalLibrary(): Observable<userTO> {
        if (this.userProfile) {
            return of(this.userProfile);
        }
        return this.http.get<userTO>(`${this.apiServerURL}/users/getUser/${this.userId}`).pipe(
            tap((userProfile: userTO) => (this.userProfile = userProfile)),
            shareReplay(1),
        );
    }

    addToLikedTracks(trackTO: TrackTO): Observable<HttpResponse<any>> {
        return this.http.post<any>(`${this.apiServerURL}/users/getUser/${this.userId}/addToLikedTracks`, trackTO, {
            observe: 'response',
        });
    }

    removeFromLikedTracks(trackId: number): Observable<HttpResponse<any>> {
        return this.http
            .delete<any>(`${this.apiServerURL}/users/getUser/${this.userId}/removeFromLikedTracks/${trackId}`)
            .pipe(shareReplay(1));
    }

    addToLikedArtists(artistTO: ArtistTO): Observable<HttpResponse<any>> {
        return this.http.post<any>(`${this.apiServerURL}/users/getUser/${this.userId}/addToLikedArtists`, artistTO, {
            observe: 'response',
        });
    }

    removeLikedArtists(artistId: number): Observable<HttpResponse<any>> {
        return this.http
            .delete<any>(`${this.apiServerURL}/users/getUser/${this.userId}/removeFromLikedArtists/${artistId}`)
            .pipe(shareReplay(1));
    }

    addToLikedAlbums(albumTO: AlbumTO): Observable<HttpResponse<any>> {
        return this.http.post<any>(`${this.apiServerURL}/users/getUser/${this.userId}/addToLikedAlbums`, albumTO, {
            observe: 'response',
        });
    }

    removeLikedAlbums(albumId: number): Observable<HttpResponse<any>> {
        return this.http
            .delete<any>(`${this.apiServerURL}/users/getUser/${this.userId}/removeFromLikedAlbums/${albumId}`)
            .pipe(shareReplay(1));
    }

    addToLikedPlaylists(playlistTO: PlaylistTO): Observable<HttpResponse<any>> {
        return this.http.post<any>(
            `${this.apiServerURL}/users/getUser/${this.userId}/addToLikedPlaylists`,
            playlistTO,
            { observe: 'response' },
        );
    }

    removeLikedPlaylists(playlistId: number): Observable<HttpResponse<any>> {
        return this.http
            .delete<any>(`${this.apiServerURL}/users/getUser/${this.userId}/removeFromLikedPlaylists/${playlistId}`)
            .pipe(shareReplay(1));
    }

    createPlaylist(title: string): Observable<HttpResponse<number>> {
        return this.http.post<any>(`${this.apiServerURL}/users/getUser/${this.userId}/addCustomPlaylist/${title}`, {});
    }

    addToCustomPlaylist(playlistId: number, trackTO: TrackTO): Observable<HttpResponse<any>> {
        return this.http.post<any>(
            `${this.apiServerURL}/users/getUser/${this.userId}/addToCustomPlaylist/${playlistId}`,
            trackTO,
            { observe: 'response' },
        );
    }

    removeFromCustomPlaylist(playlistId: number, trackId: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(
            `${this.apiServerURL}/users/getUser/${this.userId}/removeFromCustomPlaylist/${playlistId}/track/${trackId}`,
        );
    }

    // Login
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    login(credential: string, password: string): Observable<HttpResponse<any>> {
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        return this.http.post<any>(`${this.apiServerURL}/sign/in`, { credential, password }, { observe: 'response' });
    }

    // Register
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    register(username: string, password: string, email: string): Observable<HttpResponse<any>> {
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        return this.http.post<any>(
            `${this.apiServerURL}/sign/up`,
            { username, password, email },
            { observe: 'response' },
        );
    }

    setUserId(userId: number) {
        this.userId = userId;
    }
}
