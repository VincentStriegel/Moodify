import { TestBed } from '@angular/core/testing';

import { UserInterceptor } from './user-interceptor.interceptor';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('UserInterceptorInterceptor', () => {
    beforeEach(() =>
        TestBed.configureTestingModule({
            providers: [UserInterceptor],
            imports: [HttpClientTestingModule],
        }),
    );

    it('should be created', () => {
        const interceptor: UserInterceptor = TestBed.inject(UserInterceptor);
        expect(interceptor).toBeTruthy();
    });
});
