import { Injectable } from '@angular/core';
import { BehaviorSubject, debounce, timer } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {
  private _loading$ = new BehaviorSubject<boolean>(false);
  public loading$ = this._loading$.pipe(debounce(() => timer(100)));

  show() {
    this._loading$.next(true);
  }

  hide() {
    this._loading$.next(false);
  }
}