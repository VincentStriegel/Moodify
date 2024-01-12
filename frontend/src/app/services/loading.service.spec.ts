import { TestBed } from '@angular/core/testing';
import { LoadingService } from './loading.service';

describe('LoadingService', () => {
    let service: LoadingService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(LoadingService);
    });

    it('should be created', () => {
        service.loading$.subscribe((bool) => {
            expect(bool).toBeTruthy();
        });
    });

    it('should initialize with false loading state', () => {
        service.loading$.subscribe((bool) => {
            expect(bool).toBeFalsy();
        });
    });

    it('should show loading state', () => {
        service.show();
        service.loading$.subscribe((bool) => {
            expect(bool).toBeTruthy();
        });
    });

    it('should hide loading state', () => {
        service.hide();
        service.loading$.subscribe((bool) => {
            expect(bool).toBeFalsy();
        });
    });
});
