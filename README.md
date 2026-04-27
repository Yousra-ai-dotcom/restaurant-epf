# Restaurant EPF — Système de gestion de restaurant
## 👤 Auteur

- Yousra Ameur
- 
École EPF — Informatique et Génie Logiciel**
---
## ✅ Fonctionnalités

- Gestion des clients
- Gestion des plats
- Création de commandes client
- Passage en préparation
- Passage en servie
- Gestion du stock
- Alertes de stock
- Commandes fournisseur
---

## 🧪 Tests

- Tests JUnit 5 réalisés
- Cas couverts :
  - Création commande
  - Préparation commande
  - Service commande
  - Stock insuffisant (exception)
---
## ⚠️ Difficultés rencontrées

- Gestion des relations SQL (commandes / lignes)
- Calcul du total des commandes
- Synchronisation frontend / backend
---
## Lancement

### Backend (Spring + Tomcat embarqué)

```bash
# Compiler et tester
mvn clean test

# Créer le jar exécutable
mvn clean package

# Lancer le serveur (port 8080)
java -jar target/restaurant-epf-1.0-SNAPSHOT.jar
```

Le serveur démarre sur **http://localhost:8080**.
L'interface Swagger est accessible sur **http://localhost:8080/swagger**.

### Frontend (Angular 21)

Prérequis : Node.js ≥ 18 et npm installés.

#### Installer Node.js et npm

**Windows** — télécharger et exécuter l'installeur `.msi` depuis [nodejs.org](https://nodejs.org) (npm est inclus). Vérifier l'installation :
```cmd
node -v
npm -v
```

**macOS** — via Homebrew (recommandé) :
```bash
brew install node
```
Ou télécharger l'installeur `.pkg` depuis [nodejs.org](https://nodejs.org). Vérifier :
```bash
node -v
npm -v
```

```bash
cd frontend

# Installer les dépendances (première fois uniquement)
npm install

# Lancer le serveur de développement
npm start
```

L'application Angular est accessible sur **http://localhost:4200**.
Elle se connecte automatiquement au backend sur `http://localhost:8080`.

> Le backend doit être démarré avant d'utiliser le frontend.

#### Autres commandes frontend

```bash
npm run build   # Build de production (génère frontend/dist/)
npm test        # Lancer les tests unitaires (Vitest)
```
---
## Architecture

```
Spring Framework 6 (sans Spring Boot)
├── config/
│   ├── AppConfig        – scan des beans, DataSource H2, JdbcTemplate
│   ├── DatabaseConfig   – TransactionManager, @EnableTransactionManagement
│   └── WebConfig        – MVC, CORS, Jackson, ressources statiques
├── dao/                 – accès données via JdbcTemplate (patron DAO)
├── service/             – logique métier avec @Transactional
├── controller/          – API REST JSON (@RestController)
└── Main                 – démonstration (4 scénarios) + Tomcat embarqué
```

---
## Qualité du code — Checkstyle

Checkstyle s'exécute automatiquement à chaque `mvn compile` / `mvn test` (phase `validate`) et affiche les violations dans la console sans bloquer le build.

```bash
# Lancer Checkstyle seul et afficher les violations
mvn checkstyle:check

# Générer un rapport HTML dans target/site/checkstyle.html
mvn checkstyle:checkstyle
```

Les règles sont définies dans `checkstyle.xml` à la racine du projet. 
