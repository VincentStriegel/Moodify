import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackElementComponent } from './track-element.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('TrackElementComponent', () => {
    let component: TrackElementComponent;
    let fixture: ComponentFixture<TrackElementComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [TrackElementComponent],
            imports: [HttpClientTestingModule],
        });
        fixture = TestBed.createComponent(TrackElementComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
