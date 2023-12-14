import { Component } from '@angular/core';
import { LoadingService } from '../services/loading.service';

@Component({
    selector: 'app-loading-screen',
    templateUrl: './loading-screen.component.html',
})
export class LoadingScreenComponent {
    isLoading = true;

    constructor(private loadingService: LoadingService) {
        this.loadingService.loading$.subscribe((isLoading) => {
            this.isLoading = isLoading;
        });
    }
}
