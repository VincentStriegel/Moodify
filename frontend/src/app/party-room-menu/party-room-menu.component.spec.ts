import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PartyRoomMenuComponent } from './party-room-menu.component';
import { FormsModule } from '@angular/forms';

describe('PartyRoomMenuComponent', () => {
    let component: PartyRoomMenuComponent;
    let fixture: ComponentFixture<PartyRoomMenuComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [PartyRoomMenuComponent],
            imports: [FormsModule],
        });
        fixture = TestBed.createComponent(PartyRoomMenuComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
