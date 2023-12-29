import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HeaderComponent } from './header.component';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { CommonModule } from '@angular/common';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('HeaderComponent', () => {
    let component: HeaderComponent;
    let fixture: ComponentFixture<HeaderComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [HeaderComponent],
            imports: [FormsModule, RouterTestingModule, CommonModule, HttpClientTestingModule],
        });
        fixture = TestBed.createComponent(HeaderComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should perform a search', () => {
        const routerSpy = jest.spyOn(component.router, 'navigateByUrl');
        component.query = 'test';
        component.search();
        expect(routerSpy).toHaveBeenCalledWith('/', { skipLocationChange: true });
    });

    it('should handle click outside event', () => {
        const event = new Event('click');
        const containsSpy = jest.spyOn(component.elementRef.nativeElement, 'contains') as jest.SpyInstance<
            boolean,
            [Node]
        >;
        component.isOpen = true;
        component.onClickOutside(event);
        expect(component.isOpen).toBe(false);
        expect(containsSpy).toHaveBeenCalledWith(event.target);
    });
});
