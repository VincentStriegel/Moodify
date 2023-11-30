import { Component, Input } from '@angular/core';
import { TrackTO } from '../types/trackTO';
import { ActivatedRoute } from '@angular/router';
import { BackendCommunicationService } from '../services/backend-communication.service';
import { PlaylistTO } from '../types/playlistTO';
import { AlbumTO } from '../types/albumTO';
import { MusicPlayerService } from '../services/music-player.service';

@Component({
    selector: 'app-collection',
    templateUrl: './collection.component.html',
    styleUrls: ['./collection.component.css'],
})
export class CollectionComponent {
    query!: string;
    type!: string;
    id!: number;
    album?: AlbumTO;
    playlist?: PlaylistTO;
    isLiked = false;
    customPlaylistId?: number;
    @Input() isCustomPlaylist = false;

    tracks?: TrackTO[];
    constructor(
        private route: ActivatedRoute,
        private backendCommunicationService: BackendCommunicationService,
        private musicPlayerService: MusicPlayerService,
    ) {
        this.type = this.route.snapshot.paramMap.get('collectionType')!;
        this.id = parseInt(this.route.snapshot.paramMap.get('id')!);
        if (this.type == 'album') {
            this.backendCommunicationService.getAlbum(this.id).subscribe((data) => (this.album = data));
        } else if (this.type == 'custom-playlist') {
            this.backendCommunicationService.userProfile.personalLibrary.customPlaylists.forEach((playlist) => {
                if (playlist.id === this.id) {
                    this.customPlaylistId = playlist.id;
                    this.playlist = playlist;
                }
            });
        } else {
            this.backendCommunicationService.getPlaylist(this.id).subscribe((data) => (this.playlist = data));
        }

        if (backendCommunicationService.userProfile) {
            this.isLiked =
                this.type == 'album'
                    ? backendCommunicationService.userProfile.personalLibrary.likedAlbums.some(
                          (album) => album.id === this.id,
                      )
                    : backendCommunicationService.userProfile.personalLibrary.likedPlaylists.some(
                          (playlist) => playlist.id === this.id,
                      );
        }
    }
    ngOnInit(): void {
        window.scrollTo(0, 0);
    }
    onPlay() {
        const tracks = this.album?.trackTOList || this.playlist?.trackTOList || [];
        this.musicPlayerService.playTracks(tracks, this.album?.cover_small);
    }
    like(): void {
        this.type == 'album'
            ? this.backendCommunicationService.addToLikedAlbums(this.album!).subscribe(
                  () => {
                      this.isLiked = true;
                      this.backendCommunicationService.userProfile.personalLibrary.likedAlbums.push(this.album!);
                  },
                  (error) => {
                      console.error('Error:', error);
                  },
              )
            : this.backendCommunicationService.addToLikedPlaylists(this.playlist!).subscribe(
                  () => {
                      this.isLiked = true;
                      this.backendCommunicationService.userProfile.personalLibrary.likedPlaylists.push(this.playlist!);
                  },
                  (error) => {
                      console.error('Error:', error);
                  },
              );
    }
    unlike(): void {
        this.type == 'album'
            ? this.backendCommunicationService.removeLikedAlbums(this.album!.id).subscribe(
                  () => {
                      this.isLiked = false;
                      this.backendCommunicationService.userProfile.personalLibrary.likedAlbums.splice(
                          this.backendCommunicationService.userProfile.personalLibrary.likedAlbums.findIndex(
                              (album) => album.id === this.album!.id,
                          ),
                          1,
                      );
                  },
                  (error) => {
                      console.error('Error:', error);
                  },
              )
            : this.backendCommunicationService.removeLikedPlaylists(this.playlist!.id).subscribe(
                  () => {
                      this.isLiked = false;
                      this.backendCommunicationService.userProfile.personalLibrary.likedPlaylists.splice(
                          this.backendCommunicationService.userProfile.personalLibrary.likedPlaylists.findIndex(
                              (playlist) => playlist.id === this.playlist!.id,
                          ),
                          1,
                      );
                  },
                  (error) => {
                      console.error('Error:', error);
                  },
              );
    }

    removeFromCustomPlaylist(trackId: number): void {
        this.playlist!.trackTOList.splice(
            this.playlist!.trackTOList.findIndex((track) => track.id === trackId),
            1,
        );
    }
}
