import { Component } from '@angular/core';
import { TrackTO } from '../types/trackTO';
import { ActivatedRoute } from '@angular/router';
import { BackendCommunicationService } from '../services/backend-communication.service';


@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css']
})
export class SearchResultComponent {
  tracks!: TrackTO[];
  query: string;

  constructor(private route: ActivatedRoute, private backendCommunicationService: BackendCommunicationService) {
    this.query = this.route.snapshot.paramMap.get('query') || '';
  }

  ngOnInit(): void {
    this.backendCommunicationService.getSearchResults(this.query).subscribe((data) => this.tracks = data);
  }
}
