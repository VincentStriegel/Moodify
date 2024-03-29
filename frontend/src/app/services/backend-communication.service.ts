import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of, shareReplay, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TrackTO } from '../types/trackTO';
import { AlbumTO } from '../types/albumTO';
import { ArtistTO } from '../types/artistTO';
import { PlaylistTO } from '../types/playlistTO';
import { userTO } from '../types/userTO';
import { map } from 'rxjs/operators';

@Injectable({
    providedIn: 'root',
})
/**
 * Service responsible for communicating with the backend server.
 */
export class BackendCommunicationService {
    userId?: number;
    userProfile!: userTO;
    isLoggedIn = new BehaviorSubject<boolean>(false);
    isArtist = new BehaviorSubject<boolean>(false);

    private apiServerURL = environment.apiServerURL;
    constructor(private http: HttpClient) {
        if (!environment.production) {
            this.userId = 3;
            //this.isLoggedIn.next(true);
        }
    }

    /**
     * Fetches a song by its ID.
     * @param songId The ID of the song to fetch.
     * @returns An Observable that will emit the fetched song.
     */
    getSong(songId: number): Observable<TrackTO> {
        return this.http.get<TrackTO>(`${this.apiServerURL}/music/track/${songId}`).pipe(shareReplay(1));
    }

    /**
     * Retrieves an artist by their ID from the backend.
     * @param artistId - The ID of the artist to retrieve.
     * @param source - The source of the artist.
     * @returns An Observable that emits the ArtistTO object.
     */
    getArtist(artistId: number, source: string): Observable<ArtistTO> {
        const options = {
            params: new HttpParams().set('source', source),
        };
        return this.http.get<ArtistTO>(`${this.apiServerURL}/music/artist/${artistId}`, options).pipe(shareReplay(1));
    }

    /**
     * Fetches an album by its ID and updates track information with album cover.
     * @param albumId The ID of the album to fetch.
     * @returns An Observable that will emit the fetched album.
     */
    getAlbum(albumId: number): Observable<AlbumTO> {
        return this.http.get<AlbumTO>(`${this.apiServerURL}/music/album/${albumId}`).pipe(
            map((album) => ({
                ...album,
                trackTOList: album.trackTOList.map((track) => ({
                    ...track,
                    album: {
                        ...track.album,
                        cover_small: album.cover_small,
                    },
                })),
            })),
            shareReplay(1),
        );
    }

    /**
     * Retrieves a playlist from the backend server by ID.
     * @param playlistId - The ID of the playlist to retrieve.
     * @param source - The source of the playlist.
     * @returns An Observable that emits the retrieved PlaylistTO object.
     */
    getPlaylist(playlistId: number, source: string): Observable<PlaylistTO> {
        const options = {
            params: new HttpParams().set('source', source),
        };
        return this.http
            .get<PlaylistTO>(`${this.apiServerURL}/music/playlist/${playlistId}`, options)
            .pipe(shareReplay(1));
    }
    /**
     * Fetches search results for tracks based on a query.
     * @param searchQuery The search query for tracks.
     * @returns An Observable that will emit an array of track results.
     */
    getSearchResultsTracks(searchQuery: string): Observable<TrackTO[]> {
        return this.http.get<TrackTO[]>(`${this.apiServerURL}/music/search/tracks/${searchQuery}`).pipe(shareReplay(1));
    }

    /**
     * Fetches search results for albums based on a query.
     * @param searchQuery The search query for albums.
     * @returns An Observable that will emit an array of album results.
     */
    getSearchResultsAlbums(searchQuery: string): Observable<AlbumTO[]> {
        return this.http.get<AlbumTO[]>(`${this.apiServerURL}/music/search/albums/${searchQuery}`).pipe(shareReplay(1));
    }

    /**
     * Fetches search results for artists based on a query.
     * @param searchQuery The search query for artists.
     * @returns An Observable that will emit an array of artist results.
     */
    getSearchResultsArtists(searchQuery: string): Observable<ArtistTO[]> {
        return this.http
            .get<ArtistTO[]>(`${this.apiServerURL}/music/search/artists/${searchQuery}`)
            .pipe(shareReplay(1));
    }

    /**
     * Fetches search results for playlists based on a query.
     * @param searchQuery The search query for playlists.
     * @returns An Observable that will emit an array of playlist results.
     */
    getSearchResultsPlaylists(searchQuery: string): Observable<PlaylistTO[]> {
        return this.http
            .get<PlaylistTO[]>(`${this.apiServerURL}/music/search/playlists/${searchQuery}`)
            .pipe(shareReplay(1));
    }

    /**
     * Fetches the user's personal library.
     * @returns An Observable that will emit the user's profile information.
     */
    getUserPersonalLibrary(): Observable<userTO> {
        if (this.userProfile) {
            return of(this.userProfile);
        }
        return this.http.get<userTO>(`${this.apiServerURL}/users/findUserById/${this.userId}`).pipe(
            tap((userProfile: userTO) => (this.userProfile = userProfile)),
            shareReplay(1),
        );
    }

    /**
     * Adds a track to the user's liked tracks.
     * @param trackTO The track to be added.
     * @returns An Observable that will emit an HTTP response.
     */
    addToLikedTracks(trackTO: TrackTO): Observable<HttpResponse<any>> {
        return this.http.post<any>(`${this.apiServerURL}/users/addToLikedTracks?userId=${this.userId}`, trackTO, {
            observe: 'response',
        });
    }

    /**
     * Removes a track from the user's liked tracks.
     * @param trackId The ID of the track to be removed.
     * @returns An Observable that will emit an HTTP response.
     */
    removeFromLikedTracks(trackId: number): Observable<HttpResponse<any>> {
        return this.http
            .delete<any>(`${this.apiServerURL}/users/deleteFromLikedTracks?userId=${this.userId}&trackId=${trackId}`)
            .pipe(shareReplay(1));
    }

    /**
     * Adds an artist to the user's liked artists.
     * @param artistTO The artist to be added.
     * @returns An Observable that will emit an HTTP response.
     */
    addToLikedArtists(artistTO: ArtistTO): Observable<HttpResponse<any>> {
        return this.http.post<any>(`${this.apiServerURL}/users/addToLikedArtists?userId=${this.userId}`, artistTO, {
            observe: 'response',
        });
    }

    /**
     * Removes an artist from the user's liked artists.
     * @param artistId The ID of the artist to be removed.
     * @returns An Observable that will emit an HTTP response.
     */
    removeLikedArtists(artistId: number): Observable<HttpResponse<any>> {
        return this.http
            .delete<any>(`${this.apiServerURL}/users/deleteFromLikedArtists?userId=${this.userId}&artistId=${artistId}`)
            .pipe(shareReplay(1));
    }

    /**
     * Adds an album to the user's liked albums.
     * @param albumTO The album to be added.
     * @returns An Observable that will emit an HTTP response.
     */
    addToLikedAlbums(albumTO: AlbumTO): Observable<HttpResponse<any>> {
        return this.http.post<any>(`${this.apiServerURL}/users/addToLikedAlbums?userId=${this.userId}`, albumTO, {
            observe: 'response',
        });
    }

    /**
     * Removes an album from the user's liked albums.
     * @param albumId The ID of the album to be removed.
     * @returns An Observable that will emit an HTTP response.
     */
    removeLikedAlbums(albumId: number): Observable<HttpResponse<any>> {
        return this.http
            .delete<any>(`${this.apiServerURL}/users/removeFromLikedAlbums?userId=${this.userId}&albumId=${albumId}`)
            .pipe(shareReplay(1));
    }

    /**
     * Adds a playlist to the user's liked playlists.
     * @param playlistTO The playlist to be added.
     * @returns An Observable that will emit an HTTP response.
     */
    addToLikedPlaylists(playlistTO: PlaylistTO): Observable<HttpResponse<any>> {
        return this.http.post<any>(
            `${this.apiServerURL}/users/findUserById/${this.userId}/addToLikedPlaylists`,
            playlistTO,
            { observe: 'response' },
        );
    }

    /**
     * Removes a playlist from the user's liked playlists.
     * @param playlistId
     The ID of the playlist to be removed.
     * @returns An Observable that will emit an HTTP response.
     */
    removeLikedPlaylists(playlistId: number): Observable<HttpResponse<any>> {
        return this.http
            .delete<any>(
                `${this.apiServerURL}/users/findUserById/${this.userId}/removeFromLikedPlaylists/${playlistId}`,
            )
            .pipe(shareReplay(1));
    }

    /**
     * Creates a custom playlist with the given title for the user.
     * @param title The title of the custom playlist to be created.
     * @returns An Observable that will emit an HTTP response containing the playlist ID.
     */
    createPlaylist(title: string): Observable<HttpResponse<number>> {
        return this.http.post<any>(
            `${this.apiServerURL}/users/createCustomPlaylist?userId=${this.userId}&title=${title}`,
            null,
            { observe: 'response' },
        );
    }

    /**
     * Adds a track to a custom playlist.
     * @param playlistId The ID of the custom playlist to add the track to.
     * @param trackTO The track to be added to the playlist.
     * @returns An Observable that will emit an HTTP response.
     */
    addToCustomPlaylist(playlistId: number, trackTO: TrackTO): Observable<HttpResponse<any>> {
        return this.http.post<any>(
            `${this.apiServerURL}/users/addToCustomPlaylist?userId=${this.userId}&playlistId=${playlistId}`,
            trackTO,
            { observe: 'response' },
        );
    }

    /**
     * Removes a track from a custom playlist.
     * @param playlistId The ID of the custom playlist to remove the track from.
     * @param trackId The ID of the track to be removed from the playlist.
     * @returns An Observable that will emit an HTTP response.
     */
    removeFromCustomPlaylist(playlistId: number, trackId: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(
            `${this.apiServerURL}/users/deleteFromCustomPlaylist?userId=${this.userId}&playlistId=${playlistId}&trackId=${trackId}`,
        );
    }

    /**
     * Deletes a custom playlist.
     * @param playlistId The ID of the custom playlist to be deleted.
     * @returns An Observable that will emit an HTTP response.
     */
    deleteCustomPlaylist(playlistId: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(
            `${this.apiServerURL}/users/deleteCustomPlaylist?userId=${this.userId}&playlistId=${playlistId}`,
        );
    }

    /**
     * Logs in a user with the provided credentials.
     * @param credential The user's credential (e.g., username or email).
     * @param password The user's password.
     * @returns An Observable that will emit an HTTP response.
     */
    login(credential: string, password: string): Observable<HttpResponse<any>> {
        return this.http.post<any>(`${this.apiServerURL}/sign/in`, { credential, password }, { observe: 'response' });
    }

    /**
     * Registers a new user with the provided information.
     * @param username The username for the new user.
     * @param password The password for the new user.
     * @param email The email address for the new user.
     * @returns An Observable that will emit an HTTP response.
     */
    register(username: string, password: string, email: string): Observable<HttpResponse<any>> {
        return this.http.post<any>(
            `${this.apiServerURL}/sign/up`,
            { username, password, email },
            { observe: 'response' },
        );
    }

    /**
     * Sets the user's ID and marks the user as logged in.
     * @param userId The ID of the user.
     */
    setUserId(userId: number) {
        this.isLoggedIn.next(true);
        this.userId = userId;
    }

    /**
     * Retrieves recommendations for the user.
     * @returns An Observable that emits an array of TrackTO objects.
     */
    getRecommendations(): Observable<TrackTO[]> {
        return this.http.get<TrackTO[]>(`${this.apiServerURL}/music/recommendations/${this.userId}`);
    }

    promoteToArtist(picture: string): Observable<HttpResponse<any>> {
        return this.http.post<any>(
            `${this.apiServerURL}/users/promoteToArtist/${this.userId}?picture_big=${picture}&picture_small=${picture}`,
            {
                observe: 'response',
            },
        );
    }

    uploadSingle(track: TrackTO): Observable<HttpResponse<any>> {
        return this.http.post<any>(`${this.apiServerURL}/artists/addSingleToDiscography/${this.userId}`, track, {
            observe: 'response',
        });
    }
}
