import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaylistPopupComponent } from './playlist-popup.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('PlaylistPopupComponent', () => {
    let component: PlaylistPopupComponent;
    let fixture: ComponentFixture<PlaylistPopupComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [PlaylistPopupComponent],
            imports: [HttpClientTestingModule],
        });
        fixture = TestBed.createComponent(PlaylistPopupComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
