import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { EMPTY, Observable, finalize } from 'rxjs';
import { Router } from '@angular/router';
import { BackendCommunicationService } from '../services/backend-communication.service';
import { LoadingService } from '../services/loading.service';

/**
 * Interceptor for handling user-related HTTP requests, sends user to login page if login is required on the page the user was trying to access.
 */
@Injectable()
export class UserInterceptor implements HttpInterceptor {
    constructor(
        private router: Router,
        private backendCommunicationService: BackendCommunicationService,
        private loadingService: LoadingService,
    ) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this.loadingService.show();
        const urlsRequiringUserId = ['/users/findUserById'];

        if (urlsRequiringUserId.some((url) => req.url.includes(url))) {
            if (this.backendCommunicationService.userId === undefined) {
                this.router.navigate(['/login']);
                return EMPTY.pipe(finalize(() => this.loadingService.hide()));
            }
        }

        return next.handle(req).pipe(finalize(() => this.loadingService.hide()));
    }
}
