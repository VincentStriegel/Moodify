import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, firstValueFrom, shareReplay } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TrackTO } from '../types/trackTO';

@Injectable({
  providedIn: 'root'
})
export class BackendCommunicationService {

  private apiServerURL = environment.apiServerURL;
  constructor(private http: HttpClient) { }

  getSong(songId: number): Observable<TrackTO>{
    return this.http
    .get<TrackTO>(`${this.apiServerURL}/music/track/${songId}`)
    .pipe(shareReplay(1));
  }

  getSearchResults(searchQuery: string): Observable<TrackTO[]>{ 
    return this.http
    .get<TrackTO[]>(`${this.apiServerURL}/music/search/${searchQuery}`)
    .pipe(shareReplay(1));
  }
}
