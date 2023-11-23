import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TrackTO } from '../types/trackTO';
import { MusicPlayerService } from '../services/music-player.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-track-element',
    templateUrl: './track-element.component.html',
    styleUrls: ['./track-element.component.css'],
})
export class TrackElementComponent {
    trackCover?: string;
    isPlaying = false;
    Math = Math;
    @Input({ required: true }) track!: TrackTO;
    @Input() imageSrc?: string;
    @Input() isPlayingTrack?: boolean;
    @Input() isPartyRoom?: boolean;
    @Output() suggestSongPartyRoom: EventEmitter<void> = new EventEmitter<void>();

    constructor(
        private musicPlayerService: MusicPlayerService,
        private router: Router,
    ) {}

    ngOnInit(): void {}

    togglePlay() {
        this.musicPlayerService.playTrack(this.track, this.imageSrc);
    }

    goToArtistProfile(artistId: number) {
        this.router.navigateByUrl(`/artist/${artistId}`);
    }
    suggestSong(): void {
        this.suggestSongPartyRoom.emit();
    }
}
