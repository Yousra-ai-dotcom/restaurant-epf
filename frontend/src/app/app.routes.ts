import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard';
import { CommandesClientComponent } from './commandes-client/commandes-client';
import { CommandesFournisseurComponent } from './commandes-fournisseur/commandes-fournisseur';
import { ApiCheckerComponent } from './api-checker/api-checker';

export const routes: Routes = [
  { path: '', component: DashboardComponent, pathMatch: 'full' },
  { path: 'commandes-client', component: CommandesClientComponent },
  { path: 'commandes-fournisseur', component: CommandesFournisseurComponent },
  { path: 'api-checker', component: ApiCheckerComponent },
  { path: '**', redirectTo: '/' },
];
