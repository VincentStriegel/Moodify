import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TrackElementComponent } from './track-element.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { AlbumTO } from '../types/albumTO';
import { TrackTO } from '../types/trackTO';
import { MatMenuModule } from '@angular/material/menu';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('TrackElementComponent', () => {
    let component: TrackElementComponent;
    let fixture: ComponentFixture<TrackElementComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [TrackElementComponent],
            imports: [
                HttpClientTestingModule,
                MatSnackBarModule,
                MatMenuModule,
                MatTooltipModule,
                NoopAnimationsModule,
            ],
        });
        fixture = TestBed.createComponent(TrackElementComponent);
        component = fixture.componentInstance;
        component.track = {
            id: 1,
            title: 'title',
            duration: 1,
            preview: 'preview',
            release_date: 'release_date',
            artist: {
                id: 1,
                name: 'name',
                picture_small: 'picture_small',
                picture_big: 'picture_big',
                nb_fans: 1,
                albumTOList: new Array<AlbumTO>(),
                trackTOList: new Array<TrackTO>(),
            },
            album: {
                id: 1,
                title: 'title',
                cover_small: 'cover_small',
                cover_big: 'cover_big',
                release_date: 'release_date',
                trackTOList: new Array<TrackTO>(),
                number_of_songs: 1,
            },
        };
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should toggle play', () => {
        jest.spyOn(component['musicPlayerService'], 'playTrack');
        component.togglePlay();
        expect(component['musicPlayerService'].playTrack).toHaveBeenCalledWith(component.track, component.imageSrc);
    });

    it('should navigate to artist profile', () => {
        jest.spyOn(component.router, 'navigateByUrl');
        const artistId = 123;
        component.goToArtistProfile(artistId);
        expect(component.router.navigateByUrl).toHaveBeenCalledWith(`/artist/${artistId}`);
    });

    it('should suggest song', () => {
        jest.spyOn(component.suggestSongPartyRoom, 'emit');
        component.suggestSong();
        expect(component.suggestSongPartyRoom.emit).toHaveBeenCalled();
    });

    it('should close popup', () => {
        component.playlistPopup = true;
        component.closePopup();
        expect(component.playlistPopup).toBe(false);
    });

    it('should queue track', () => {
        jest.spyOn(component.musicPlayerService, 'addTrack');
        jest.spyOn(component.snackbarService, 'openSuccessSnackBar');
        component.queueTrack();
        expect(component.musicPlayerService.addTrack).toHaveBeenCalledWith(component.track);
        expect(component.snackbarService.openSuccessSnackBar).toHaveBeenCalledWith(
            component.track.album.cover_small,
            component.track.title,
            'added to queue',
        );
    });
});
