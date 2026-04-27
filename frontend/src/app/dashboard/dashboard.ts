import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import {
  ClientService,
  PlatService,
  IngredientService,
  FournisseurService,
  CommandeClientService,
  CommandeFournisseurService,
} from '../services/api.service';
import {
  Client,
  Plat,
  Ingredient,
  Fournisseur,
  CommandeClient,
  CommandeFournisseur,
} from '../models/models';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class DashboardComponent implements OnInit {
  private clientService = inject(ClientService);
  private platService = inject(PlatService);
  private ingredientService = inject(IngredientService);
  private fournisseurService = inject(FournisseurService);
  private commandeClientService = inject(CommandeClientService);
  private commandeFournisseurService = inject(CommandeFournisseurService);
  private cdr = inject(ChangeDetectorRef);

  clients: Client[] = [];
  plats: Plat[] = [];
  ingredients: Ingredient[] = [];
  alertes: Ingredient[] = [];
  fournisseurs: Fournisseur[] = [];
  commandesClient: CommandeClient[] = [];
  commandesFournisseur: CommandeFournisseur[] = [];

  loading = true;
  error = '';

  // Modale commande fournisseur
  modalOuvert = false;
  ingredientModal: Ingredient | null = null;
  modalFournisseurId: number | null = null;
  modalQuantite = 1;
  modalPrixUnitaire = 0;
  modalErreur = '';
  modalSucces = '';
  prixParFournisseur = new Map<number, number>();

  // Toast notification
  toastMessage = '';
  toastType: 'success' | 'error' = 'success';
  toastVisible = false;

  ngOnInit(): void {
    forkJoin({
      clients:              this.clientService.getAll().pipe(catchError(() => of([] as Client[]))),
      plats:                this.platService.getAll().pipe(catchError(() => of([] as Plat[]))),
      ingredients:          this.ingredientService.getAll().pipe(catchError(() => of([] as Ingredient[]))),
      alertes:              this.ingredientService.getAlertes().pipe(catchError(() => of([] as Ingredient[]))),
      fournisseurs:         this.fournisseurService.getAll().pipe(catchError(() => of([] as Fournisseur[]))),
      commandesClient:      this.commandeClientService.getAll().pipe(catchError(() => of([] as CommandeClient[]))),
      commandesFournisseur: this.commandeFournisseurService.getAll().pipe(catchError(() => of([] as CommandeFournisseur[]))),
    }).subscribe({
      next: (data) => {
        this.clients = data.clients;
        this.plats = data.plats;
        this.ingredients = data.ingredients;
        this.alertes = data.alertes;
        this.fournisseurs = data.fournisseurs;
        this.commandesClient = data.commandesClient;
        this.commandesFournisseur = data.commandesFournisseur;
        this.loading = false;
        if (this.ingredients.length === 0 && this.clients.length === 0) {
          this.error = 'Impossible de contacter le serveur. Vérifiez que le backend est démarré sur le port 8080.';
        }
        this.cdr.detectChanges();
      },
    });
  }

  ouvrirModal(ingredient: Ingredient): void {
    this.ingredientModal = ingredient;
    this.modalFournisseurId = null;
    this.modalQuantite = 1;
    this.modalPrixUnitaire = 0;
    this.modalErreur = '';
    this.modalSucces = '';
    this.modalOuvert = true;

    this.ingredientService.getPrix(ingredient.id).subscribe({
      next: (prix) => {
        this.prixParFournisseur.clear();
        prix.forEach(p => this.prixParFournisseur.set(p.fournisseurId, p.prixUnitaire));
      },
    });
    this.ingredientService.getRecommandation(ingredient.id).subscribe({
      next: (rec) => {
        this.modalQuantite      = rec.quantiteRecommandee;
        this.modalFournisseurId = rec.fournisseurId;
        this.modalPrixUnitaire  = rec.prixUnitaire;
        this.cdr.markForCheck();
      },
    });
  }

  onFournisseurChange(id: number | null): void {
    if (id !== null) {
      this.modalPrixUnitaire = this.prixParFournisseur.get(id) ?? 0;
    }
  }

  fermerModal(): void {
    this.modalOuvert = false;
    this.ingredientModal = null;
    this.cdr.markForCheck();
  }

  soumettreCommande(): void {
    this.modalErreur = '';
    if (!this.modalFournisseurId) {
      this.modalErreur = 'Veuillez sélectionner un fournisseur.';
      return;
    }
    if (this.modalQuantite <= 0) {
      this.modalErreur = 'La quantité doit être supérieure à 0.';
      return;
    }

    // Capturer les valeurs avant de fermer la modale
    const payload = {
      fournisseurId: this.modalFournisseurId,
      lignes: [{
        ingredientId: this.ingredientModal!.id,
        quantite: this.modalQuantite,
        prixUnitaire: this.modalPrixUnitaire,
      }],
    };

    // Fermer la modale immédiatement
    this.fermerModal();

    this.commandeFournisseurService.creer(payload).subscribe({
      next: () => this.showToast('Commande fournisseur créée avec succès.', 'success'),
      error: () => this.showToast('Erreur lors de la création de la commande.', 'error'),
    });
  }

  showToast(message: string, type: 'success' | 'error'): void {
    this.toastMessage = message;
    this.toastType = type;
    this.toastVisible = true;
    this.cdr.markForCheck();
    setTimeout(() => {
      this.toastVisible = false;
      this.cdr.markForCheck();
    }, 3500);
  }

  get commandesEnAttente(): CommandeClient[] {
    return this.commandesClient.filter((c) => c.statut === 'EN_ATTENTE');
  }

  get commandesEnPreparation(): CommandeClient[] {
    return this.commandesClient.filter((c) => c.statut === 'EN_PREPARATION');
  }

  get commandesFournisseurEnAttente(): CommandeFournisseur[] {
    return this.commandesFournisseur.filter((c) => c.statut === 'EN_ATTENTE' || c.statut === 'ENVOYEE');
  }

  calculerTotal(commande: CommandeClient): number {
    return commande.lignes.reduce((sum, l) => sum + l.plat.prix * l.quantite, 0);
  }

  stockPercent(ingredient: Ingredient): number {
    if (ingredient.seuilAlerte <= 0) return 100;
    return Math.min(100, (ingredient.stockActuel / ingredient.seuilAlerte) * 100);
  }
}
