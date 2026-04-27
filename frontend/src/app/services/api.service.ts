import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import {
  Plat,
  PlatDetail,
  Client,
  Fournisseur,
  Ingredient,
  PrixIngredient,
  RecommandationCommande,
  CommandeClient,
  CommandeFournisseur,
  PreparationResultDto,
} from '../models/models';

const API = environment.apiUrl;

@Injectable({ providedIn: 'root' })
export class PlatService {
  private http = inject(HttpClient);
  getAll(): Observable<Plat[]> {
    return this.http.get<Plat[]>(`${API}/api/plats`);
  }
  getById(id: number): Observable<PlatDetail> {
    return this.http.get<PlatDetail>(`${API}/api/plats/${id}`);
  }
}

@Injectable({ providedIn: 'root' })
export class ClientService {
  private http = inject(HttpClient);
  getAll(): Observable<Client[]> {
    return this.http.get<Client[]>(`${API}/api/clients`);
  }
}

@Injectable({ providedIn: 'root' })
export class FournisseurService {
  private http = inject(HttpClient);
  getAll(): Observable<Fournisseur[]> {
    return this.http.get<Fournisseur[]>(`${API}/api/fournisseurs`);
  }
  getCatalogue(id: number): Observable<PrixIngredient[]> {
    return this.http.get<PrixIngredient[]>(`${API}/api/fournisseurs/${id}/catalogue`);
  }
}

@Injectable({ providedIn: 'root' })
export class IngredientService {
  private http = inject(HttpClient);
  getAll(): Observable<Ingredient[]> {
    return this.http.get<Ingredient[]>(`${API}/api/ingredients`);
  }
  getAlertes(): Observable<Ingredient[]> {
    return this.http.get<Ingredient[]>(`${API}/api/ingredients/alertes`);
  }
  getRecommandation(id: number): Observable<RecommandationCommande> {
    return this.http.get<RecommandationCommande>(`${API}/api/ingredients/${id}/recommandation`);
  }
  getPrix(id: number): Observable<RecommandationCommande[]> {
    return this.http.get<RecommandationCommande[]>(`${API}/api/ingredients/${id}/prix`);
  }
}

@Injectable({ providedIn: 'root' })
export class CommandeClientService {
  private http = inject(HttpClient);

  getAll(statut?: string): Observable<CommandeClient[]> {
    const url = statut
      ? `${API}/api/commandes/client?statut=${statut}`
      : `${API}/api/commandes/client`;
    return this.http.get<CommandeClient[]>(url);
  }

  creer(data: {
    clientId: number;
    lignes: { platId: number; quantite: number }[];
  }): Observable<CommandeClient> {
    return this.http.post<CommandeClient>(`${API}/api/commandes/client`, data);
  }

  preparer(id: number): Observable<PreparationResultDto> {
    return this.http.put<PreparationResultDto>(
      `${API}/api/commandes/client/${id}/preparer`,
      {}
    );
  }

  servir(id: number): Observable<CommandeClient> {
    return this.http.put<CommandeClient>(
      `${API}/api/commandes/client/${id}/servir`,
      {}
    );
  }

  annuler(id: number): Observable<CommandeClient> {
    return this.http.put<CommandeClient>(
      `${API}/api/commandes/client/${id}/annuler`,
      {}
    );
  }

  supprimer(id: number): Observable<void> {
    return this.http.delete<void>(`${API}/api/commandes/client/${id}`);
  }
}

@Injectable({ providedIn: 'root' })
export class CommandeFournisseurService {
  private http = inject(HttpClient);

  getAll(statut?: string): Observable<CommandeFournisseur[]> {
    const url = statut
      ? `${API}/api/commandes/fournisseur?statut=${statut}`
      : `${API}/api/commandes/fournisseur`;
    return this.http.get<CommandeFournisseur[]>(url);
  }

  creer(data: {
    fournisseurId: number;
    lignes: { ingredientId: number; quantite: number; prixUnitaire: number }[];
  }): Observable<CommandeFournisseur> {
    return this.http.post<CommandeFournisseur>(
      `${API}/api/commandes/fournisseur`,
      data
    );
  }

  envoyer(id: number): Observable<CommandeFournisseur> {
    return this.http.put<CommandeFournisseur>(
      `${API}/api/commandes/fournisseur/${id}/envoyer`,
      {}
    );
  }

  recevoir(id: number): Observable<CommandeFournisseur> {
    return this.http.put<CommandeFournisseur>(
      `${API}/api/commandes/fournisseur/${id}/recevoir`,
      {}
    );
  }

  annuler(id: number): Observable<CommandeFournisseur> {
    return this.http.put<CommandeFournisseur>(
      `${API}/api/commandes/fournisseur/${id}/annuler`,
      {}
    );
  }

  supprimer(id: number): Observable<void> {
    return this.http.delete<void>(`${API}/api/commandes/fournisseur/${id}`);
  }
}
