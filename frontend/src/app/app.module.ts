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
import { MatChipsModule } from '@angular/material/chips';
import { ArtistProfileComponent } from './artist-profile/artist-profile.component';
import { CollectionElementComponent } from './collection/collection-element/collection-element.component';
import { CollectionComponent } from './collection/collection.component';

@NgModule({
    declarations: [
        AppComponent,
        LandingPageComponent,
        HeaderComponent,
        MusicPlayerComponent,
        TrackElementComponent,
        SearchResultComponent,
        ArtistProfileComponent,
        CollectionElementComponent,
        CollectionComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        MatProgressBarModule,
        MatSliderModule,
        FormsModule,
        MatChipsModule,
    ],
    providers: [],
    bootstrap: [AppComponent],
})
export class AppModule {}
