import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollectionElementComponent } from './collection-element.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

describe('CollectionComponent', () => {
    let component: CollectionElementComponent;
    let fixture: ComponentFixture<CollectionElementComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [CollectionElementComponent],
            imports: [RouterTestingModule, HttpClientTestingModule],
        });
        fixture = TestBed.createComponent(CollectionElementComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
