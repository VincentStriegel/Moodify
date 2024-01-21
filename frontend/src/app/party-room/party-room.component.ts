import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TrackTO } from '../types/trackTO';
import { BackendCommunicationService } from '../services/backend-communication.service';
import { environment } from 'src/environments/environment';
import { DataService } from '../services/data.service';
import { SnackbarService } from '../services/snackbar.service';

export type PartyRoomTO = {
    tracks: TrackTO[];
    currentPosition: number;
};

enum PartyRoomMessageType {
    SUGGEST_TRACK = 'SUGGEST_TRACK',
    RATE_TRACK = 'RATE_TRACK',
    SET_PLAYLIST_ID = 'SET_PLAYLIST_ID',
    REMOVE_TRACK = 'REMOVE_TRACK',
    SET_CURRENT_POSITION = 'SET_CURRENT_POSITION',
    JOIN_ROOM = 'JOIN_ROOM',
}

enum ThumbRating {
    THUMBS_UP = 'THUMBS_UP',
    THUMBS_DOWN = 'THUMBS_DOWN',
}

@Component({
    selector: 'app-party-room',
    templateUrl: './party-room.component.html',
    styleUrls: ['./party-room.component.css'],
})
export class PartyRoomComponent {
    title = 'WebSocketClient';
    song = '';
    query: string;
    searchQuery: string = '';
    searchedTracks: TrackTO[] = [];
    partyRoomTracks: TrackTO[] = [];
    prevPartyRoomTracks: TrackTO[] = [];
    currentTrack!: TrackTO;
    audio!: HTMLAudioElement;
    progress = 0;
    isPlaying = false;
    volume = 100;
    isOwner = false;
    partyRoomData!: PartyRoomTO;
    firstTime = true;
    playedTracksIds: number[] = [];
    intervalId?: any;

    trackRatings: Map<number, ThumbRating> = new Map<number, ThumbRating>();

    private webSocket: WebSocket;
    private webSocketServerURL = environment.webSocketServerURL;
    constructor(
        private route: ActivatedRoute,
        private backendCommunicationService: BackendCommunicationService,
        private snackbarService: SnackbarService,
        private dataService: DataService,
    ) {
        this.query = this.route.snapshot.paramMap.get('roomId') || '';
        this.webSocket = new WebSocket(`${this.webSocketServerURL}/party-room/${this.query}`);

        this.webSocket.onopen = () => {
            if (this.dataService.getPlaylistID() != null) {
                this.setPlayList(this.dataService.getPlaylistID());
                this.isOwner = true;
            } else {
                const message = {
                    messageType: PartyRoomMessageType.JOIN_ROOM,
                };
                this.webSocket.send(JSON.stringify(message));
            }
        };

        this.webSocket.onmessage = (event) => {
            this.partyRoomData = JSON.parse(event.data) as PartyRoomTO;
            this.prevPartyRoomTracks = this.partyRoomTracks;
            this.partyRoomTracks = this.partyRoomData.tracks;
            if (this.prevPartyRoomTracks.length != this.partyRoomTracks.length && (!this.firstTime || this.isOwner)) {
                const newTracks = this.partyRoomTracks.filter(
                    (track) =>
                        !this.prevPartyRoomTracks.some((prevTrack) => prevTrack.id === track.id) &&
                        !this.playedTracksIds.includes(track.id),
                );

                for (const track of newTracks) {
                    this.snackbarService.openSuccessSnackBar(
                        track.album.cover_small,
                        track.title,
                        'added to the queue',
                    );
                }
            }
            this.setupAudioPlayer();
        };
    }

    /**
     * Searches for tracks based on the provided search query.
     */
    searchTracks() {
        this.backendCommunicationService
            .getSearchResultsTracks(this.searchQuery)
            .subscribe((data) => (this.searchedTracks = data));
    }

    /**
     * Sends a suggested song to the WebSocket.
     * @param track - The trackTO to suggest.
     */
    suggestSong(track: TrackTO) {
        const message = {
            messageType: PartyRoomMessageType.SUGGEST_TRACK,
            trackTO: track,
        };
        this.webSocket.send(JSON.stringify(message));
    }

    /**
     * Sets the playlist ID and sends a message to the WebSocket.
     * @param playlistId The ID of the playlist to set.
     */
    setPlayList(playlistId: number) {
        const message = {
            messageType: PartyRoomMessageType.SET_PLAYLIST_ID,
            playlistId: playlistId,
            userId: this.backendCommunicationService.userId,
        };

        this.webSocket.send(JSON.stringify(message));
    }

    /**
     * Removes a track from the party room, removes both locally and on the WebSocket.
     * @param track - The track to be removed.
     */
    removeTrack(track: TrackTO) {
        const message = {
            messageType: PartyRoomMessageType.REMOVE_TRACK,
            trackTO: track,
        };
        this.webSocket.send(JSON.stringify(message));
    }

    setupAudioPlayer(): void {
        if (!this.isPlaying && this.partyRoomTracks) {
            this.currentTrack = this.partyRoomTracks[0];
            this.audio = new Audio(this.currentTrack.preview);
            if (this.partyRoomData.currentPosition && this.audio && this.firstTime && !this.isOwner) {
                this.firstTime = false;
                this.audio.currentTime = this.partyRoomData.currentPosition + 0.65;
            }
            this.audio.play();
            this.isPlaying = true;
            this.audio.addEventListener('timeupdate', this.updateProgress.bind(this));
        }
    }

    updateProgress(): void {
        this.progress = (this.audio.currentTime / this.audio.duration) * 100;

        if (this.progress === 100) {
            this.isPlaying = false;
            this.playedTracksIds.push(this.currentTrack.id);
            this.removeTrack(this.currentTrack);
        }
    }

    ngOnInit(): void {
        this.intervalId = setInterval(() => {
            if (this.isOwner) {
                this.setCurrentPosition(this.audio.currentTime);
            }
        }, 500);
    }

    ngOnDestroy(): void {
        if (this.audio) {
            this.audio.removeEventListener('timeupdate', this.updateProgress);
            this.audio.pause();
        }
        clearInterval(this.intervalId);
        this.webSocket.close();
    }

    /**
     * Likes a track and sends a rating.
     * @param track - The trackTO to be liked.
     */
    likeTrack(track: TrackTO) {
        let rating: number;
        if (this.trackRatings.get(track.id) == ThumbRating.THUMBS_UP) {
            rating = +2;
        } else {
            rating = +1;
        }
        this.trackRatings.set(track.id, ThumbRating.THUMBS_UP);
        const message = {
            messageType: PartyRoomMessageType.RATE_TRACK,
            trackTO: track,
            rating: rating,
        };
        this.webSocket.send(JSON.stringify(message));
    }

    /**
     * Dislikes a track and sends a rating.
     * @param track - The trackTO to dislike.
     */
    dislikeTrack(track: TrackTO) {
        let rating: number;
        if (this.trackRatings.get(track.id) == ThumbRating.THUMBS_UP) {
            rating = -2;
        } else {
            rating = -1;
        }
        this.trackRatings.set(track.id, ThumbRating.THUMBS_DOWN);
        if (this.trackRatings.get(track.id) == ThumbRating.THUMBS_DOWN) {
            const message = {
                messageType: PartyRoomMessageType.RATE_TRACK,
                trackTO: track,
                rating: rating,
            };
            this.webSocket.send(JSON.stringify(message));
        }
    }

    /**
     * Retrieves the rating of a track.
     * @param trackId The ID of the track.
     * @returns The rating of the track, or undefined if it doesn't exist.
     */
    checkTrackRating(trackId: number): ThumbRating | undefined {
        return this.trackRatings.get(trackId);
    }

    copyToClipboard(value: string) {
        navigator.clipboard.writeText(value).then(
            function () {},
            function () {},
        );
    }

    /**
     * Sets the current track position in the party room.
     *
     * @param int - The current position to set.
     */
    setCurrentPosition(int: number) {
        const message = {
            messageType: PartyRoomMessageType.SET_CURRENT_POSITION,
            currentPosition: int,
        };
        this.webSocket.send(JSON.stringify(message));
    }

    /**
     * Checks if a track with the given trackId has been played.
     * @param trackId - The ID of the track to check.
     * @returns True if the track has been played, false otherwise.
     */
    checkIfPlayed(trackId: number): boolean {
        return this.playedTracksIds.includes(trackId);
    }
}
