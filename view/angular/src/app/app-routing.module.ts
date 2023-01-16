import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

// import {DashboardComponent} from '../ctrl/dashboard.component';
// import {HeroesComponent} from '../ctrl/heroes.component';
const routes: Routes = [
  // {path: '', redirectTo: '/dashboard', pathMatch: 'full'},
//   {path: 'dashboard', component: DashboardComponent},
//   {path: 'heroes', component: HeroesComponent}
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
