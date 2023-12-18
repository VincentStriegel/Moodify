import { TestBed } from '@angular/core/testing';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { BehaviorSubject } from 'rxjs';
import { SnackbarService } from './snackbar.service';
import { SnackbarComponent } from '../snackbar/snackbar.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('SnackbarService', () => {
  let service: SnackbarService;
  let snackBar: MatSnackBar;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SnackbarComponent],
      imports: [MatSnackBarModule, NoopAnimationsModule],
      providers: [SnackbarService, MatSnackBar]
    });
    service = TestBed.inject(SnackbarService);
    snackBar = TestBed.inject(MatSnackBar);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should open a success snackbar with the specified image, text, and message', () => {
    const img = 'success.png';
    const text = 'Success';
    const message = 'Operation completed successfully';

    jest.spyOn(service['_snackbarSubject$'], 'next');
    jest.spyOn(snackBar, 'openFromComponent');

    service.openSuccessSnackBar(img, text, message);

    expect(service['_snackbarSubject$'].next).toHaveBeenCalledWith({ img, text, message });
    expect(snackBar.openFromComponent).toHaveBeenCalledWith(SnackbarComponent, {
      duration: 2500,
      horizontalPosition: 'right',
      verticalPosition: 'top',
    });
  });

  it('should open an error snackbar with the specified message and default duration', () => {
    const message = 'An error occurred';

    jest.spyOn(snackBar, 'open');

    service.openErrorSnackBar(message);

    expect(snackBar.open).toHaveBeenCalledWith(message, 'Close', {
      duration: 5000
    });
  });

  it('should open an error snackbar with the specified message and custom duration', () => {
    const message = 'An error occurred';
    const durationInSeconds = 10;

    jest.spyOn(snackBar, 'open');

    service.openErrorSnackBar(message, durationInSeconds);

    expect(snackBar.open).toHaveBeenCalledWith(message, 'Close', {
      duration: durationInSeconds * 1000
    });
  });
});