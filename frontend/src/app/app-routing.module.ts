import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { SearchResultComponent } from './search-result/search-result.component';
import { ArtistProfileComponent } from './artist-profile/artist-profile.component';
import { CollectionComponent } from './collection/collection.component';

const routes: Routes = [
    { path: '', pathMatch: 'full', component: LandingPageComponent },
    { path: 'search/:query', pathMatch: 'full', component: SearchResultComponent },
    { path: 'artist/:artistId', pathMatch: 'full', component: ArtistProfileComponent },
    { path: 'collection/:collectionType/:id', pathMatch: 'full', component: CollectionComponent },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {}
