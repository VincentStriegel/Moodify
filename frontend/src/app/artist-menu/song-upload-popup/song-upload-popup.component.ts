import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { BackendCommunicationService } from 'src/app/services/backend-communication.service';
import { TrackTO } from '../../types/trackTO';

@Component({
    selector: 'app-song-upload-popup',
    templateUrl: './song-upload-popup.component.html',
    styleUrls: ['./song-upload-popup.component.css'],
})
export class SongUploadPopupComponent {
    @Output() closePopup: EventEmitter<{ playlistId?: number; playlistTitle: string }> = new EventEmitter<{
        playlistId?: number;
        playlistTitle: string;
    }>();

    songTitle = new FormControl('', [Validators.required]);
    songCoverSrc = new FormControl('', [Validators.required]);
    songAudioSrc = new FormControl('', [Validators.required]);
    audio?: HTMLAudioElement;
    isPlaying = false;

    constructor(private backendCommunicationService: BackendCommunicationService) {}

    close(): void {
        this.closePopup.emit({ playlistTitle: '' });
    }

    uploadSong(): void {
        if (!this.audio && this.songAudioSrc.value) {
            this.audio = new Audio(this.songAudioSrc.value);
        }
        const track = {
            duration: this.audio?.duration || 0,
            release_date: new Date().toISOString(),
            source: 'MOODIFY',
            title: this.songTitle.value!,
            cover_big: this.songCoverSrc.value!,
            cover_small: this.songCoverSrc.value!,
            preview: this.songAudioSrc.value!,
        };
        this.backendCommunicationService.uploadSingle(track as TrackTO).subscribe(
            () => {
                this.closePopup.emit();
            },
            () => {},
        );
    }

    playSong(): void {
        if (this.songAudioSrc.value && this.audio?.src !== this.songAudioSrc.value) {
            this.audio = new Audio(this.songAudioSrc.value);
            this.audio.play();
            this.isPlaying = true;
        } else if (this.audio?.src === this.songAudioSrc.value && !this.isPlaying) {
            this.audio.play();
            this.isPlaying = true;
        } else {
            this.isPlaying = false;
            if (this.audio) {
                this.audio.pause();
            }
        }
    }
}
