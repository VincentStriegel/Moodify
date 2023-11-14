import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollectionComponent } from './collection.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('CollectionComponent', () => {
    let component: CollectionComponent;
    let fixture: ComponentFixture<CollectionComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [CollectionComponent],
            imports: [RouterTestingModule, HttpClientTestingModule],
        });
        fixture = TestBed.createComponent(CollectionComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
