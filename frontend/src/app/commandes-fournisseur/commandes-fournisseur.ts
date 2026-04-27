import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { forkJoin } from 'rxjs';
import {
  CommandeFournisseurService,
  FournisseurService,
  IngredientService,
} from '../services/api.service';
import {
  CommandeFournisseur,
  Fournisseur,
  Ingredient,
  PrixIngredient,
} from '../models/models';

interface LigneFournisseurForm {
  ingredientId: number | null;
  quantite: number;
  prixUnitaire: number;
}

@Component({
  selector: 'app-commandes-fournisseur',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './commandes-fournisseur.html',
  styleUrl: './commandes-fournisseur.css',
})
export class CommandesFournisseurComponent implements OnInit {
  private commandeService = inject(CommandeFournisseurService);
  private fournisseurService = inject(FournisseurService);
  private ingredientService = inject(IngredientService);
  private route = inject(ActivatedRoute);
  private cdr = inject(ChangeDetectorRef);

  commandes: CommandeFournisseur[] = [];
  fournisseurs: Fournisseur[] = [];
  ingredients: Ingredient[] = [];

  /** Catalogue du fournisseur sélectionné : ingredientId → prixUnitaire */
  catalogueCourant = new Map<number, number>();

  /** Catalogues de tous les fournisseurs pour l'affichage */
  catalogues = new Map<number, PrixIngredient[]>();

  selectedFournisseurId: number | null = null;
  lignes: LigneFournisseurForm[] = [{ ingredientId: null, quantite: 1, prixUnitaire: 0 }];

  showDeleteModal = false;
  deleteTargetId: number | null = null;

  private preselectedIngredientId: number | null = null;
  errorMessage = '';
  successMessage = '';

  ngOnInit(): void {
    const id = this.route.snapshot.queryParamMap.get('ingredientId');
    if (id) {
      this.preselectedIngredientId = +id;
    }
    this.loadCommandes();
    this.loadIngredients();
    this.loadFournisseurs();
  }

  loadCommandes(): void {
    this.commandeService.getAll().subscribe({
      next: (data) => { this.commandes = data; this.cdr.markForCheck(); },
      error: () => (this.errorMessage = 'Erreur lors du chargement des commandes.'),
    });
  }

  loadFournisseurs(): void {
    this.fournisseurService.getAll().subscribe({
      next: (fournisseurs) => {
        this.fournisseurs = fournisseurs;
        if (fournisseurs.length === 0) { this.cdr.markForCheck(); return; }
        forkJoin(fournisseurs.map((f) => this.fournisseurService.getCatalogue(f.id))).subscribe({
          next: (resultats) => {
            fournisseurs.forEach((f, i) => this.catalogues.set(f.id, resultats[i]));
            this.cdr.markForCheck();
          },
        });
      },
      error: () => (this.errorMessage = 'Erreur lors du chargement des fournisseurs.'),
    });
  }

  loadIngredients(): void {
    this.ingredientService.getAll().subscribe({
      next: (data) => {
        this.ingredients = data;
        if (this.preselectedIngredientId) {
          this.lignes[0].ingredientId = this.preselectedIngredientId;
        }
        this.cdr.markForCheck();
      },
      error: () => (this.errorMessage = 'Erreur lors du chargement des ingrédients.'),
    });
  }

  onFournisseurChange(): void {
    this.catalogueCourant.clear();
    if (!this.selectedFournisseurId) return;
    const items = this.catalogues.get(this.selectedFournisseurId) ?? [];
    items.forEach((item) => this.catalogueCourant.set(item.ingredientId, item.prixUnitaire));
    // Mettre à jour les prix des lignes déjà saisies
    this.lignes.forEach((l) => {
      if (l.ingredientId !== null) {
        l.prixUnitaire = this.catalogueCourant.get(l.ingredientId) ?? 0;
      }
    });
  }

  onIngredientChange(ligne: LigneFournisseurForm): void {
    if (ligne.ingredientId !== null) {
      ligne.prixUnitaire = this.catalogueCourant.get(ligne.ingredientId) ?? 0;
    }
  }

  getCatalogue(fournisseurId: number): PrixIngredient[] {
    return this.catalogues.get(fournisseurId) ?? [];
  }

  ajouterLigne(): void {
    this.lignes.push({ ingredientId: null, quantite: 1, prixUnitaire: 0 });
  }

  supprimerLigne(i: number): void {
    this.lignes.splice(i, 1);
  }

  calculerTotal(commande: CommandeFournisseur): number {
    return commande.lignes.reduce(
      (sum, l) => sum + l.prixUnitaire * l.quantiteCommandee,
      0
    );
  }

  soumettreCommande(): void {
    this.errorMessage = '';
    this.successMessage = '';

    if (!this.selectedFournisseurId) {
      this.errorMessage = 'Veuillez sélectionner un fournisseur.';
      return;
    }

    const lignesValides = this.lignes.filter(
      (l) => l.ingredientId !== null && l.quantite > 0 && this.catalogueCourant.has(l.ingredientId!)
    );

    if (lignesValides.length === 0) {
      this.errorMessage = 'Veuillez ajouter au moins un ingrédient disponible chez ce fournisseur.';
      return;
    }

    const payload = {
      fournisseurId: this.selectedFournisseurId,
      lignes: lignesValides.map((l) => ({
        ingredientId: l.ingredientId as number,
        quantite: l.quantite,
        prixUnitaire: l.prixUnitaire,
      })),
    };

    this.commandeService.creer(payload).subscribe({
      next: () => {
        this.successMessage = 'Commande fournisseur créée avec succès.';
        this.selectedFournisseurId = null;
        this.catalogueCourant.clear();
        this.lignes = [{ ingredientId: null, quantite: 1, prixUnitaire: 0 }];
        this.loadCommandes();
      },
      error: () => (this.errorMessage = 'Erreur lors de la création de la commande.'),
    });
  }

  envoyer(id: number): void {
    this.commandeService.envoyer(id).subscribe({
      next: () => this.loadCommandes(),
      error: () => (this.errorMessage = "Erreur lors de l'envoi de la commande."),
    });
  }

  recevoir(id: number): void {
    this.commandeService.recevoir(id).subscribe({
      next: () => {
        this.successMessage = 'Commande reçue — stock mis à jour.';
        this.loadCommandes();
      },
      error: () => (this.errorMessage = 'Erreur lors de la réception de la commande.'),
    });
  }

  demanderSupprimer(id: number): void {
    this.deleteTargetId = id;
    this.showDeleteModal = true;
    this.cdr.markForCheck();
  }

  confirmerSupprimer(): void {
    if (this.deleteTargetId === null) return;
    const id = this.deleteTargetId;
    this.showDeleteModal = false;
    this.deleteTargetId = null;
    this.errorMessage = '';
    this.commandeService.supprimer(id).subscribe({
      next: () => this.loadCommandes(),
      error: (err) => {
        this.errorMessage = err.error?.message || 'Erreur lors de la suppression.';
        this.cdr.markForCheck();
      },
    });
  }

  annulerSupprimer(): void {
    this.showDeleteModal = false;
    this.deleteTargetId = null;
  }
}
