import { Component, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { TrackTO } from '../types/trackTO';
import { BackendCommunicationService } from '../services/backend-communication.service';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css'],
})
export class HeaderComponent {
    songList!: TrackTO[];
    query!: string;
    isOpen = false;
    windowWidth: number;
    isLoggedIn = false;
    constructor(
        private router: Router,
        private elementRef: ElementRef,
        private backendCommunicationService: BackendCommunicationService,
    ) {
        this.windowWidth = window.innerWidth;
        document.addEventListener('click', this.onClickOutside.bind(this));
        window.addEventListener('resize', this.onResize.bind(this));
        this.backendCommunicationService.getUserPersonalLibrary().subscribe(() => {
            this.isLoggedIn = true;
        });
    }
    ngOnInit() {}
    search() {
        this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate(['/search', this.query]);
        });
    }

    onResize(event: Event): void {
        this.windowWidth = (event.target as Window).innerWidth;
    }

    onClickOutside(event: Event): void {
        if (!this.elementRef.nativeElement.contains(event.target)) {
            this.isOpen = false;
        }
    }
}
