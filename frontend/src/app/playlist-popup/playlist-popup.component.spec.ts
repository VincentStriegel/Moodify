import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaylistPopupComponent } from './playlist-popup.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatSnackBarModule } from '@angular/material/snack-bar';

describe('PlaylistPopupComponent', () => {
    let component: PlaylistPopupComponent;
    let fixture: ComponentFixture<PlaylistPopupComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [PlaylistPopupComponent],
            imports: [HttpClientTestingModule, MatSnackBarModule],
        });
        fixture = TestBed.createComponent(PlaylistPopupComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
