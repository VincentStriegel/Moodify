import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SongUploadPopupComponent } from './song-upload-popup.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('SongUploadPopupComponent', () => {
    let component: SongUploadPopupComponent;
    let fixture: ComponentFixture<SongUploadPopupComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [SongUploadPopupComponent],
            imports: [
                HttpClientTestingModule,
                MatFormFieldModule,
                FormsModule,
                ReactiveFormsModule,
                MatInputModule,
                NoopAnimationsModule,
            ],
        });
        fixture = TestBed.createComponent(SongUploadPopupComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
