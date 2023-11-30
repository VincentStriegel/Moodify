import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { BackendCommunicationService } from '../services/backend-communication.service';

@Injectable()
export class UserInterceptor implements HttpInterceptor {
    constructor(
        private router: Router,
        private backendCommunicationService: BackendCommunicationService,
    ) {}
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const urlsRequiringUserId = [
            '/users/getUser',
            '/music/track',
            '/music/artist',
            '/music/album',
            '/music/playlist',
        ];

        if (urlsRequiringUserId.some((url) => req.url.includes(url))) {
            if (this.backendCommunicationService.userId === undefined) {
                this.router.navigate(['/login']);
            }
        }

        return next.handle(req);
    }
}
