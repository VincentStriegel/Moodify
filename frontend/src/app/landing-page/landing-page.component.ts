import { Component } from '@angular/core';
import { BackendCommunicationService } from '../services/backend-communication.service';
import { TrackTO } from '../types/trackTO';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent {
  testVar!: TrackTO ;
  testVar2!: TrackTO ;
  constructor (private backendCommunicationService: BackendCommunicationService) {

  }
}
