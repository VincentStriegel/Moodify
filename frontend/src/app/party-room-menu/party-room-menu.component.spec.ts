import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PartyRoomMenuComponent } from './party-room-menu.component';

describe('PartyRoomMenuComponent', () => {
  let component: PartyRoomMenuComponent;
  let fixture: ComponentFixture<PartyRoomMenuComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PartyRoomMenuComponent]
    });
    fixture = TestBed.createComponent(PartyRoomMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
