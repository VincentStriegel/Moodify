import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TrackTO } from '../types/trackTO';
import { BackendCommunicationService } from '../services/backend-communication.service';
import { MusicPlayerService } from '../services/music-player.service';
import { environment } from 'src/environments/environment';

export type PartyRoomTrackTO = {
    TrackTO: TrackTO;
    rating: number;
};

enum PartyRoomMessageType {
    SUGGEST_TRACK = 'SUGGEST_TRACK',
    RATE_TRACK = 'RATE_TRACK',
    SET_PLAYLIST = 'SET_PLAYLIST',
    REMOVE_TRACK = 'REMOVE_TRACK',
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
    trackRatings: Map<TrackTO, number> = new Map<TrackTO, number>();
    currentTrack!: TrackTO;
    audio!: HTMLAudioElement;
    progress = 0;
    isPlaying = false;
    private webSocket: WebSocket;
    private webSocketServerURL = environment.webSocketServerURL
    constructor(
        private route: ActivatedRoute,
        private backendCommunicationService: BackendCommunicationService,

    ) {
        this.query = this.route.snapshot.paramMap.get('roomId') || '';
        this.webSocket = new WebSocket(`${this.webSocketServerURL}/party-room/${this.query}`);
        this.webSocket.onmessage = (event) => {
            // this.musicPlayerService.playTrack(JSON.parse(event.data) as trackRatings);
            this.partyRoomTracks = JSON.parse(event.data) as TrackTO[];
            this.currentTrack = this.partyRoomTracks[0];
            this.setupAudioPlayer();
        };
        this.backendCommunicationService
            .getPlaylist(1516635421)
            .subscribe((data) => this.setPlayList(data.trackTOList));
    }

    searchTracks() {
        this.backendCommunicationService
            .getSearchResultsTracks(this.searchQuery)
            .subscribe((data) => (this.searchedTracks = data));
    }

    suggestSong(track: TrackTO) {
        const message = {
            messageType: PartyRoomMessageType.SUGGEST_TRACK,
            trackTO: track,
        };
        this.webSocket.send(JSON.stringify(message));
    }

    setPlayList(trackTOList: TrackTO[]) {
        const message = {
            messageType: PartyRoomMessageType.SET_PLAYLIST,
            trackTOList: trackTOList.slice(0, 9),
            // trackTOList1: trackTOList.slice(11, 19)
        };

        this.webSocket.send(JSON.stringify(message));
    }

    removeTrack(track: TrackTO) {
        const message = {
            messageType: PartyRoomMessageType.REMOVE_TRACK,
            trackTO: track,
        };
        this.webSocket.send(JSON.stringify(message));
    }

    setupAudioPlayer(): void {
        if (!this.isPlaying) {
            this.audio = new Audio(this.currentTrack.preview);
            this.audio.play();
            this.isPlaying = true;
            this.audio.addEventListener('timeupdate', this.updateProgress.bind(this));
        }
    }

    updateProgress(): void {
        this.progress = (this.audio.currentTime / this.audio.duration) * 100;

        if (this.progress === 100) {
            this.isPlaying = false;
            this.removeTrack(this.currentTrack);
        }
    }

    ngOnDestroy(): void {
        this.audio.removeEventListener('timeupdate', this.updateProgress);
        this.audio.pause();
        this.audio = undefined as any;
        this.webSocket.close();
    }
}
