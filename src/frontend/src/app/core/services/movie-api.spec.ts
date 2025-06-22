import { TestBed } from '@angular/core/testing';

import { MovieApiService } from './movie-api.service';

describe('MovieApi', () => {
  let service: MovieApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MovieApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
