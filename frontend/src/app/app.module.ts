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
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TrackElementComponent } from './track-element/track-element.component';
import { SearchResultComponent } from './search-result/search-result.component';
import { MatChipsModule } from '@angular/material/chips';
import { ArtistProfileComponent } from './artist-profile/artist-profile.component';
import { PartyRoomComponent } from './party-room/party-room.component';
import { CollectionElementComponent } from './collection/collection-element/collection-element.component';
import { CollectionComponent } from './collection/collection.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { LoginRegisterComponent } from './login-register/login-register.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PartyRoomMenuComponent } from './party-room-menu/party-room-menu.component';
import { UserProfileComponent } from './user-profile/user-profile.component';

@NgModule({
    declarations: [
        AppComponent,
        LandingPageComponent,
        HeaderComponent,
        MusicPlayerComponent,
        TrackElementComponent,
        SearchResultComponent,
        ArtistProfileComponent,
        PartyRoomComponent,
        CollectionElementComponent,
        CollectionComponent,
        LoginRegisterComponent,
        PartyRoomMenuComponent,
        UserProfileComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        MatProgressBarModule,
        MatSliderModule,
        FormsModule,
        MatChipsModule,
        MatToolbarModule,
        MatIconModule,
        MatButtonModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        BrowserAnimationsModule,
    ],
    providers: [],
    bootstrap: [AppComponent],
})
export class AppModule {}
