import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArtistProfileComponent } from './artist-profile.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('ArtistProfileComponent', () => {
    let component: ArtistProfileComponent;
    let fixture: ComponentFixture<ArtistProfileComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [ArtistProfileComponent],
            imports: [RouterTestingModule, HttpClientTestingModule],
        });
        fixture = TestBed.createComponent(ArtistProfileComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
