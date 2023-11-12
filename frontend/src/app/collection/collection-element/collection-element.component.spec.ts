import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollectionElementComponent } from './collection-element.component';

describe('CollectionComponent', () => {
  let component: CollectionElementComponent;
  let fixture: ComponentFixture<CollectionElementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CollectionElementComponent]
    });
    fixture = TestBed.createComponent(CollectionElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
