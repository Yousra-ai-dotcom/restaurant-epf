export interface Plat {
  id: number;
  nom: string;
  description: string;
  prix: number;
}

export interface PlatIngredient {
  ingredient: Ingredient;
  quantiteRequise: number;
}

export interface PlatDetail extends Plat {
  ingredients: PlatIngredient[];
}

export interface Client {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
}

export interface Fournisseur {
  id: number;
  nom: string;
  contact: string;
  email: string;
}

export interface Ingredient {
  id: number;
  nom: string;
  unite: string;
  stockActuel: number;
  seuilAlerte: number;
}

export interface RecommandationCommande {
  fournisseurId: number;
  fournisseurNom: string;
  prixUnitaire: number;
  quantiteRecommandee: number;
}

export interface PrixIngredient {
  ingredientId: number;
  ingredientNom: string;
  ingredientUnite: string;
  prixUnitaire: number;
}

export interface AlerteStockDto {
  ingredient: Ingredient;
  stockActuel: number;
  seuilAlerte: number;
  quantiteCommandee: number;
  commandeFournisseurId: number;
}

export interface PreparationResultDto {
  commande: CommandeClient;
  alertes: AlerteStockDto[];
}

export interface LigneCommandeClient {
  id: number;
  commandeClientId: number;
  plat: Plat;
  quantite: number;
}

export interface CommandeClient {
  id: number;
  client: Client;
  dateCommande: string;
  statut: string;
  lignes: LigneCommandeClient[];
}

export interface LigneCommandeFournisseur {
  id: number;
  commandeFournisseurId: number;
  ingredient: Ingredient;
  quantiteCommandee: number;
  prixUnitaire: number;
}

export interface CommandeFournisseur {
  id: number;
  fournisseur: Fournisseur;
  dateCommande: string;
  statut: string;
  lignes: LigneCommandeFournisseur[];
}
