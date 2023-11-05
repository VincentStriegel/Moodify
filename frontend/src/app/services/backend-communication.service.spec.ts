import { TestBed } from '@angular/core/testing';

import { BackendCommunicationService } from './backend-communication.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('BackendCommunicationService', () => {
    let service: BackendCommunicationService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
        });
        service = TestBed.inject(BackendCommunicationService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
