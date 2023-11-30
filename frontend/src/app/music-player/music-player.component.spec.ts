import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MusicPlayerComponent } from './music-player.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('MusicPlayerComponent', () => {
    let component: MusicPlayerComponent;
    let fixture: ComponentFixture<MusicPlayerComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [MusicPlayerComponent],
            imports: [HttpClientTestingModule],
        });
        fixture = TestBed.createComponent(MusicPlayerComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
