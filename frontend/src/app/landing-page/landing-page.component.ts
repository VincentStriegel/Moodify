import { Component } from '@angular/core';
import { BackendCommunicationService } from '../services/backend-communication.service';

@Component({
    selector: 'app-landing-page',
    templateUrl: './landing-page.component.html',
    styleUrls: ['./landing-page.component.css'],
})
export class LandingPageComponent {
    constructor(private backendCommunicationService: BackendCommunicationService) {}
}
