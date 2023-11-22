import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginRegisterComponent } from './login-register.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('LoginRegisterComponent', () => {
    let component: LoginRegisterComponent;
    let fixture: ComponentFixture<LoginRegisterComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [LoginRegisterComponent],
            imports: [
                HttpClientTestingModule,
                MatFormFieldModule,
                FormsModule,
                ReactiveFormsModule,
                MatInputModule,
                NoopAnimationsModule,
            ],
        });
        fixture = TestBed.createComponent(LoginRegisterComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
