import { TestBed } from '@angular/core/testing';

import { MusicPlayerService } from './music-player.service';

describe('MusicPlayerService', () => {
  let service: MusicPlayerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MusicPlayerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
