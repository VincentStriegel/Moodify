import { Injectable } from '@angular/core';
import { BehaviorSubject, debounce, timer } from 'rxjs';

/**
 * Service for managing loading state.
 */
@Injectable({
    providedIn: 'root',
})
export class LoadingService {
    private _loading$ = new BehaviorSubject<boolean>(false);
    public loading$ = this._loading$.pipe(debounce(() => timer(100)));

    /**
     * Show the loading state.
     */
    show() {
        this._loading$.next(true);
    }

    /**
     * Hide the loading state.
     */
    hide() {
        this._loading$.next(false);
    }
}
