import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { CommandeClientService } from '../services/api.service';
import { ClientService } from '../services/api.service';
import { PlatService } from '../services/api.service';
import { CommandeClient, Client, Plat, PlatDetail } from '../models/models';

interface LigneForm {
  platId: number | null;
  quantite: number;
}

@Component({
  selector: 'app-commandes-client',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './commandes-client.html',
  styleUrl: './commandes-client.css',
})
export class CommandesClientComponent implements OnInit {
  private commandeService = inject(CommandeClientService);
  private clientService = inject(ClientService);
  private platService = inject(PlatService);
  private cdr = inject(ChangeDetectorRef);

  commandes: CommandeClient[] = [];
  clients: Client[] = [];
  plats: Plat[] = [];
  platDetails: PlatDetail[] = [];

  selectedClientId: number | null = null;
  lignes: LigneForm[] = [{ platId: null, quantite: 1 }];

  errorMessage = '';
  successMessage = '';
  expandedId: number | null = null;

  toggleDetail(id: number): void {
    this.expandedId = this.expandedId === id ? null : id;
  }
  stockModalMessage = '';
  stockManques: string[] = [];
  showStockModal = false;
  showDeleteModal = false;
  deleteTargetId: number | null = null;

  ngOnInit(): void {
    this.loadCommandes();
    this.loadClients();
    this.loadPlats();
  }

  loadCommandes(): void {
    this.commandeService.getAll().subscribe({
      next: (data) => { this.commandes = data; this.cdr.markForCheck(); },
      error: () => (this.errorMessage = 'Erreur lors du chargement des commandes.'),
    });
  }

  loadClients(): void {
    this.clientService.getAll().subscribe({
      next: (data) => { this.clients = data; this.cdr.markForCheck(); },
      error: () => (this.errorMessage = 'Erreur lors du chargement des clients.'),
    });
  }

  loadPlats(): void {
    this.platService.getAll().subscribe({
      next: (data) => {
        this.plats = data;
        forkJoin(data.map(p => this.platService.getById(p.id))).subscribe({
          next: (details) => { this.platDetails = details; this.cdr.markForCheck(); },
        });
        this.cdr.markForCheck();
      },
      error: () => (this.errorMessage = 'Erreur lors du chargement des plats.'),
    });
  }

  ajouterLigne(): void {
    this.lignes.push({ platId: null, quantite: 1 });
  }

  supprimerLigne(i: number): void {
    this.lignes.splice(i, 1);
  }

  calculerTotal(commande: CommandeClient): number {
    return commande.lignes.reduce(
      (sum, l) => sum + l.plat.prix * l.quantite,
      0
    );
  }

  soumettreCommande(): void {
    this.errorMessage = '';
    this.successMessage = '';

    if (!this.selectedClientId) {
      this.errorMessage = 'Veuillez sélectionner un client.';
      return;
    }

    const lignesValides = this.lignes.filter((l) => l.platId !== null && l.quantite > 0);
    if (lignesValides.length === 0) {
      this.errorMessage = 'Veuillez ajouter au moins une ligne valide.';
      return;
    }

    const payload = {
      clientId: this.selectedClientId,
      lignes: lignesValides.map((l) => ({
        platId: l.platId as number,
        quantite: l.quantite,
      })),
    };

    this.commandeService.creer(payload).subscribe({
      next: () => {
        this.successMessage = 'Commande créée avec succès.';
        this.selectedClientId = null;
        this.lignes = [{ platId: null, quantite: 1 }];
        this.loadCommandes();
        this.cdr.markForCheck();
      },
      error: () => (this.errorMessage = 'Erreur lors de la création de la commande.'),
    });
  }

  fermerStockModal(): void {
    this.showStockModal = false;
    this.stockModalMessage = '';
    this.stockManques = [];
  }

  preparer(id: number): void {
    this.errorMessage = '';
    this.commandeService.preparer(id).subscribe({
      next: () => this.loadCommandes(),
      error: (err) => {
        if (err.status === 422) {
          const msg: string = err.error?.message || 'Stock insuffisant pour préparer cette commande.';
          this.stockModalMessage = msg;
          const prefix = 'Stock insuffisant pour : ';
          this.stockManques = msg.startsWith(prefix)
            ? msg.slice(prefix.length).split(' | ')
            : [msg];
          this.showStockModal = true;
        } else {
          this.errorMessage = err.error?.message || 'Erreur lors de la mise en préparation.';
        }
        this.cdr.markForCheck();
      },
    });
  }

  servir(id: number): void {
    this.errorMessage = '';
    this.commandeService.servir(id).subscribe({
      next: () => this.loadCommandes(),
      error: (err) => (this.errorMessage = err.error?.message || 'Erreur lors du service de la commande.'),
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

  nomClient(commande: CommandeClient): string {
    return `${commande.client.prenom} ${commande.client.nom}`;
  }
}
