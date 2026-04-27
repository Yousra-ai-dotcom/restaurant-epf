# TP – Système de gestion de restaurant « La Table de Margot »

## Contexte

Vous devez implémenter le backend complet d'une application de gestion de restaurant. Le frontend Angular et la configuration Spring sont déjà fournis ; tout le reste est à coder.

---

## Ce qui vous est fourni

- **Configuration Spring** : `AppConfig`, `DatabaseConfig` (H2 en mémoire, JdbcTemplate), `WebConfig` (CORS, JSON, ressources statiques)
- **`Main.java`** : démarre Tomcat sur le port 8080
- **Ressources** : `schema.sql` (schéma BDD), `data.sql` (données de test)
- **`OpenApiController`** : fourni dans `controller/`, expose `/swagger` (Swagger UI) et `/v3/api-docs` (OpenAPI JSON) — ne pas modifier ni recréer
- **Frontend Angular** complet (composants, services, page de vérification d'endpoints)

> Le schéma SQL est votre référence principale : il définit toutes les entités, leurs champs et leurs relations.

---

## Ce que vous devez implémenter

### 1. DTOs (`dto/`)

Classes de transfert pour l'API :

- `CreerCommandeClientRequest` — `{ clientId, lignes: [{platId, quantite}] }`
- `CreerCommandeFournisseurRequest` — `{ fournisseurId, lignes: [{ingredientId, quantite, prixUnitaire}] }`
- `PreparationResultDto` — `{ commande, alertes }`
- `RecommandationDto` — fournisseur le moins cher pour un ingrédient + quantité recommandée
- `AlerteStockDto` — ingrédient en alerte + quantité à commander

### 2. Exceptions (`exception/`)
Utilisez les exceptions à bonne escient

### 3. DAOs (`dao/`)

Un DAO par entité principale, injecté via `JdbcTemplate` (`@Repository`) :

| Classe | `ClientDao` | `PlatDao` | `IngredientDao` | `FournisseurDao` | `CommandeClientDao` | `CommandeFournisseurDao` 

### 5. Services (`service/`)

| Classe | Responsabilités |
|---|---|
| `CommandeClientService` | Créer une commande, passer en préparation (consomme le stock), marquer comme servie, supprimer |
| `CommandeFournisseurService` | Créer une commande fournisseur, envoyer, recevoir (réapprovisionne le stock), supprimer |
| `StockService` | Consommer/restituer le stock, vérifier les alertes |

> **Règles métier à respecter :**
>
> **Préparation d'une commande client**
> - Vérifier que le stock de chaque ingrédient est suffisant avant de décrémenter (`stockActuel < quantitéRequise × quantitéPlat` → `StockInsuffisantException`)
> - Décrémenter le stock de chaque ingrédient une fois la commande passée en préparation
> - Une commande ne peut passer en préparation que si son statut est `EN_ATTENTE`
> - Une commande ne peut être marquée servie que si son statut est `EN_PREPARATION`
>
> **Alerte stock**
> - Un ingrédient est en alerte si `stockActuel < seuilAlerte` (strictement inférieur — à exactement le seuil, il n'est **pas** en alerte)
>
> **Recommandation de réapprovisionnement (`GET /api/ingredients/{id}/recommandation`)**
> - Retourne le fournisseur le **moins cher** pour cet ingrédient (prix unitaire le plus bas dans `FOURNISSEUR_INGREDIENT`)
> - La **quantité recommandée** est calculée ainsi :
>   - Si `seuilAlerte > stockActuel` : quantité = `2 × (seuilAlerte − stockActuel)`
>   - Sinon (stock exactement au seuil) : quantité = `seuilAlerte`

### 6. Controllers REST (`controller/`)

| Classe | Route de base | Opérations |
|---|---|---|
| `ClientController` | `/api/clients` | GET liste, POST créer |
| `PlatController` | `/api/plats` | GET liste, POST créer |
| `FournisseurController` | `/api/fournisseurs` | GET liste, POST créer, GET `/{id}/catalogue` → `[{ ingredientId, ingredientNom, ingredientUnite, prixUnitaire }]` |
| `IngredientController` | `/api/ingredients` | GET liste, GET `/alertes`, GET `/{id}/recommandation`, GET `/{id}/prix` → `[{ fournisseurId, fournisseurNom, prixUnitaire }]` |
| `CommandeClientController` | `/api/commandes/client` | GET liste (`?statut=`), GET `/{id}`, POST, PUT `/{id}/preparer`, PUT `/{id}/servir`, DELETE `/{id}` |
| `CommandeFournisseurController` | `/api/commandes/fournisseur` | GET liste, GET `/{id}`, POST, PUT `/{id}/envoyer`, PUT `/{id}/recevoir`, DELETE `/{id}` |

---

## Tests

Écrivez **au minimum 8 tests JUnit 5** couvrant au moins **2 cas d'erreur** (exceptions métier). Exemples :

- Créer une commande avec un client inexistant → CustomException? 
- Passer en préparation avec stock insuffisant → CustomException?
- Vérifier qu'un ingrédient sous le seuil est bien retourné par `findIngredientsSousAlerte`
- Vérifier qu'une commande reçue met bien à jour le stock

---

## Vérification

1. **Tests** : `mvn test` doit passer sans erreur.
2. **API Checker** : lancez le backend → onglet *API Checker* pour valider chaque endpoint.
3. **Frontend complet** : naviguez dans l'application pour tester le cycle complet (créer un client, des plats, passer une commande, la préparer, gérer le stock).
