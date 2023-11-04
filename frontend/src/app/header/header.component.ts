import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TrackTO } from '../types/trackTO';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css'],
})
export class HeaderComponent {
    songList!: TrackTO[];
    query!: string;
    constructor(private router: Router) {}
    ngOnInit() {}
    search() {
        this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate(['/search', this.query]);
        });
    }
}
