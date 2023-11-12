import { Component, Input } from '@angular/core';
import { TrackTO } from '../types/trackTO';
import { ActivatedRoute } from '@angular/router';
import { BackendCommunicationService } from '../services/backend-communication.service';
import { ArtistTO } from '../types/artistTO';
import { PlaylistTO } from '../types/playlistTO';
import { AlbumTO } from '../types/albumTO';
import { MusicPlayerService } from '../services/music-player.service';

@Component({
  selector: 'app-collection',
  templateUrl: './collection.component.html',
  styleUrls: ['./collection.component.css']
})
export class CollectionComponent {
  query!: string;
  type!: string;
  id!: number;
  album?: AlbumTO;
  playlist?: PlaylistTO;

  tracks?: TrackTO[];
  constructor(
      private route: ActivatedRoute,
      private backendCommunicationService: BackendCommunicationService,
      private musicPlayerService: MusicPlayerService
  ) {
      this.query = this.route.snapshot.paramMap.get('collectionType')!;
      this.query.includes('album') ? this.type = 'album' : this.type = 'playlist';
      this.id = parseInt(this.route.snapshot.paramMap.get('id')!);
      this.type == 'album' ? this.backendCommunicationService.getAlbum(this.id).subscribe((data) => (this.album = data)): this.backendCommunicationService.getPlaylist(this.id).subscribe((data) => (this.playlist = data));
    }
  ngOnInit(): void {

  }
  onPlay() {
    const tracks = this.album?.trackTOList || this.playlist?.trackTOList || [];
    this.musicPlayerService.playTracks(tracks, this.album?.cover_small)
  }
}
