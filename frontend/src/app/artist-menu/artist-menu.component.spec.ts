import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArtistMenuComponent } from './artist-menu.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('ArtistMenuComponent', () => {
    let component: ArtistMenuComponent;
    let fixture: ComponentFixture<ArtistMenuComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [ArtistMenuComponent],
            imports: [
                HttpClientTestingModule,
                MatFormFieldModule,
                FormsModule,
                ReactiveFormsModule,
                MatInputModule,
                NoopAnimationsModule,
            ],
        });
        fixture = TestBed.createComponent(ArtistMenuComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
