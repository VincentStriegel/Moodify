import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PartyRoomComponent } from './party-room.component';

describe('PartyRoomComponent', () => {
    let component: PartyRoomComponent;
    let fixture: ComponentFixture<PartyRoomComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [PartyRoomComponent],
        });
        fixture = TestBed.createComponent(PartyRoomComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
