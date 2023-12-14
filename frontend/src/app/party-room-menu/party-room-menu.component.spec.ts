import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PartyRoomMenuComponent } from './party-room-menu.component';
import { FormsModule } from '@angular/forms';
import { MatTooltipModule } from '@angular/material/tooltip';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('PartyRoomMenuComponent', () => {
    let component: PartyRoomMenuComponent;
    let fixture: ComponentFixture<PartyRoomMenuComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [PartyRoomMenuComponent],
            imports: [FormsModule, HttpClientTestingModule, MatTooltipModule],
        });
        fixture = TestBed.createComponent(PartyRoomMenuComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
