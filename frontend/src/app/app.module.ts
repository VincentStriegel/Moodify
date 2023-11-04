import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { HeaderComponent } from './header/header.component';
import { MusicPlayerComponent } from './music-player/music-player.component';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSliderModule } from '@angular/material/slider';
import { FormsModule } from '@angular/forms';
import { TrackElementComponent } from './track-element/track-element.component';
import { SearchResultComponent } from './search-result/search-result.component';

@NgModule({
    declarations: [
        AppComponent,
        LandingPageComponent,
        HeaderComponent,
        MusicPlayerComponent,
        TrackElementComponent,
        SearchResultComponent,
    ],
    imports: [BrowserModule, AppRoutingModule, HttpClientModule, MatProgressBarModule, MatSliderModule, FormsModule],
    providers: [],
    bootstrap: [AppComponent],
})
export class AppModule {}
