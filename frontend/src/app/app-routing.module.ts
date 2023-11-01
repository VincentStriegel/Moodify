import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { SearchResultComponent } from './search-result/search-result.component';

const routes: Routes = [
  {path: '', pathMatch: 'full', component: LandingPageComponent},
  {path: 'search/:query', pathMatch: 'full', component: SearchResultComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
