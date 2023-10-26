import { Component } from '@angular/core';
import { BackendCommunicationService } from '../services/backend-communication.service';
import { TrackTO } from '../types/trackTO';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent {
  testVar?: TrackTO ;
  constructor (private backendCommunicationService: BackendCommunicationService) {

  }
  ngOnInit(){
    this.backendCommunicationService.getSong(3135556).subscribe((data) => this.testVar = data);
  }
  ngAfterViewInit(){
    console.log(this.testVar)
  }
}
