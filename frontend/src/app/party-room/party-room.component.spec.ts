import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PartyRoomComponent } from './party-room.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';

describe('PartyRoomComponent', () => {
    let component: PartyRoomComponent;
    let fixture: ComponentFixture<PartyRoomComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [PartyRoomComponent],
            imports: [RouterTestingModule, HttpClientTestingModule, CommonModule, FormsModule, MatSnackBarModule],
        });
        fixture = TestBed.createComponent(PartyRoomComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
