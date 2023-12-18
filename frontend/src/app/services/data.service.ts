import { Injectable } from '@angular/core';

/**
 * Service for managing data related to playlists.
 */
@Injectable({
    providedIn: 'root',
})
export class DataService {
    playlistId?: number;

    constructor() {}

    /**
     * Sets the playlist ID.
     * @param playlistID The ID of the playlist.
     */
    setPlaylistID(playlistID: number) {
        this.playlistId = playlistID;
    }

    /**
     * Gets the playlist ID.
     * @returns The ID of the playlist.
     */
    getPlaylistID() {
        return this.playlistId!;
    }
}
