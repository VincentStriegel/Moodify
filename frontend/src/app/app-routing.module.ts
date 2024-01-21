import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { SearchResultComponent } from './search-result/search-result.component';
import { ArtistProfileComponent } from './collection/artist-profile/artist-profile.component';
import { PartyRoomComponent } from './party-room/party-room.component';
import { CollectionComponent } from './collection/collection.component';
import { PartyRoomMenuComponent } from './party-room-menu/party-room-menu.component';
import { LoginRegisterComponent } from './login-register/login-register.component';
import { UserLibraryComponent } from './user-library/user-library.component';
import { ArtistMenuComponent } from './artist-menu/artist-menu.component';

const routes: Routes = [
    { path: '', pathMatch: 'full', component: LandingPageComponent },
    { path: 'search/:query', pathMatch: 'full', component: SearchResultComponent },
    { path: 'artist/:artistId/:source', pathMatch: 'full', component: ArtistProfileComponent },
    { path: 'party-room-menu', pathMatch: 'full', component: PartyRoomMenuComponent },
    { path: 'party-room/:roomId', pathMatch: 'full', component: PartyRoomComponent },
    { path: 'collection/:collectionType/:id/:source', pathMatch: 'full', component: CollectionComponent },
    { path: 'login', pathMatch: 'full', component: LoginRegisterComponent },
    { path: 'library', pathMatch: 'full', component: UserLibraryComponent },
    { path: 'artist-menu', pathMatch: 'full', component: ArtistMenuComponent },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {}
