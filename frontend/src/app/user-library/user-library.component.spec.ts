import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserLibraryComponent } from './user-library.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('UserLibraryComponent', () => {
    let component: UserLibraryComponent;
    let fixture: ComponentFixture<UserLibraryComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [UserLibraryComponent],
            imports: [HttpClientTestingModule],
        });
        fixture = TestBed.createComponent(UserLibraryComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
