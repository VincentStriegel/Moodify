import { Component } from '@angular/core';
import { SnackbarService } from '../services/snackbar.service';

@Component({
    selector: 'app-snackbar',
    templateUrl: './snackbar.component.html',
    styleUrls: ['./snackbar.component.css'],
})
export class SnackbarComponent {
    imgSrc?: string;
    text?: string;
    message?: string;

    constructor(private SnackbarService: SnackbarService) {
        this.SnackbarService._snackbarSubject$.subscribe((data) => {
            this.imgSrc = data.img;
            this.text = data.text;
            this.message = data.message;
        });
    }
}
