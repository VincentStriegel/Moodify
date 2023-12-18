import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SnackbarComponent } from '../snackbar/snackbar.component';
import { BehaviorSubject } from 'rxjs';

export type SnackbarData = {
  img: string,
  text: string,
  message: string

}

/**
 * Service for displaying snackbars.
 */
@Injectable({
  providedIn: 'root'
})
export class SnackbarService {
  _snackbarSubject$ = new BehaviorSubject<SnackbarData>({ img: '', text: '', message: '' });

  constructor(private _snackBar: MatSnackBar) {}

  /**
   * Opens a success snackbar with the specified image, text, and message.
   * @param img The image to display in the snackbar.
   * @param text The text to display in the snackbar.
   * @param message The message to display in the snackbar.
   */
  openSuccessSnackBar(img: string, text: string, message: string) {
    const snackbarData: SnackbarData = {
      img: img,
      text: text,
      message: message
    }
    this._snackbarSubject$.next(snackbarData);
    this._snackBar.openFromComponent(SnackbarComponent, {
      duration: 2500,
      horizontalPosition: 'right',
      verticalPosition: 'top',
    });
  }

  /**
   * Opens an error snackbar with the specified message and duration.
   * @param message The message to display in the snackbar.
   * @param durationInSeconds The duration of the snackbar in seconds. Default is 5 seconds.
   */
  openErrorSnackBar(message: string, durationInSeconds = 5) {
    this._snackBar.open(message, 'Close', {
      duration: durationInSeconds * 1000
    });
  }
}