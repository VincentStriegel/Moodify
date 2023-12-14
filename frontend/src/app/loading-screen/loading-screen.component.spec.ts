import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadingScreenComponent } from './loading-screen.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

describe('LoadingScreenComponent', () => {
    let component: LoadingScreenComponent;
    let fixture: ComponentFixture<LoadingScreenComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [LoadingScreenComponent],
            imports: [MatProgressSpinnerModule],
        });
        fixture = TestBed.createComponent(LoadingScreenComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
