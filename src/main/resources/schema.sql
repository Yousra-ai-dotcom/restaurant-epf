-- ============================================================
-- schema.sql - Système de gestion restaurant "La Table de Margot"
-- ============================================================

DROP TABLE IF EXISTS LIGNE_COMMANDE_FOURNISSEUR;
DROP TABLE IF EXISTS COMMANDE_FOURNISSEUR;
DROP TABLE IF EXISTS FOURNISSEUR_INGREDIENT;
DROP TABLE IF EXISTS LIGNE_COMMANDE_CLIENT;
DROP TABLE IF EXISTS PLAT_INGREDIENT;
DROP TABLE IF EXISTS COMMANDE_CLIENT;
DROP TABLE IF EXISTS PLAT;
DROP TABLE IF EXISTS INGREDIENT;
DROP TABLE IF EXISTS FOURNISSEUR;
DROP TABLE IF EXISTS CLIENT;

CREATE TABLE CLIENT (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom        VARCHAR(100) NOT NULL,
    prenom     VARCHAR(100) NOT NULL,
    email      VARCHAR(150) UNIQUE NOT NULL,
    telephone  VARCHAR(20)
);

CREATE TABLE PLAT (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom         VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    prix        DECIMAL(8, 2) NOT NULL CHECK (prix > 0)
);

CREATE TABLE INGREDIENT (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom           VARCHAR(100) NOT NULL UNIQUE,
    unite         VARCHAR(20)  NOT NULL,
    stock_actuel  DOUBLE       NOT NULL DEFAULT 0,
    seuil_alerte  DOUBLE       NOT NULL DEFAULT 0
);

-- Association Plat <-> Ingrédient (quantité nécessaire pour préparer 1 portion)
CREATE TABLE PLAT_INGREDIENT (
    plat_id           BIGINT NOT NULL,
    ingredient_id     BIGINT NOT NULL,
    quantite_requise  DOUBLE NOT NULL CHECK (quantite_requise > 0),
    PRIMARY KEY (plat_id, ingredient_id),
    FOREIGN KEY (plat_id)       REFERENCES PLAT(id),
    FOREIGN KEY (ingredient_id) REFERENCES INGREDIENT(id)
);

CREATE TABLE COMMANDE_CLIENT (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_id      BIGINT      NOT NULL,
    date_commande  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    statut         VARCHAR(20) NOT NULL DEFAULT 'EN_ATTENTE',
    FOREIGN KEY (client_id) REFERENCES CLIENT(id),
    CHECK (statut IN ('EN_ATTENTE', 'EN_PREPARATION', 'SERVIE', 'ANNULEE'))
);

CREATE TABLE LIGNE_COMMANDE_CLIENT (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    commande_client_id  BIGINT  NOT NULL,
    plat_id             BIGINT  NOT NULL,
    quantite            INTEGER NOT NULL CHECK (quantite > 0),
    FOREIGN KEY (commande_client_id) REFERENCES COMMANDE_CLIENT(id),
    FOREIGN KEY (plat_id)            REFERENCES PLAT(id)
);

CREATE TABLE FOURNISSEUR (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom     VARCHAR(100) NOT NULL,
    contact VARCHAR(100),
    email   VARCHAR(150) UNIQUE NOT NULL
);

-- Catalogue des prix : quel fournisseur vend quel ingrédient à quel prix
CREATE TABLE FOURNISSEUR_INGREDIENT (
    fournisseur_id  BIGINT        NOT NULL,
    ingredient_id   BIGINT        NOT NULL,
    prix_unitaire   DECIMAL(8, 2) NOT NULL CHECK (prix_unitaire >= 0),
    PRIMARY KEY (fournisseur_id, ingredient_id),
    FOREIGN KEY (fournisseur_id) REFERENCES FOURNISSEUR(id),
    FOREIGN KEY (ingredient_id)  REFERENCES INGREDIENT(id)
);

CREATE TABLE COMMANDE_FOURNISSEUR (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    fournisseur_id BIGINT      NOT NULL,
    date_commande  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    statut         VARCHAR(20) NOT NULL DEFAULT 'EN_ATTENTE',
    FOREIGN KEY (fournisseur_id) REFERENCES FOURNISSEUR(id),
    CHECK (statut IN ('EN_ATTENTE', 'ENVOYEE', 'RECUE', 'ANNULEE'))
);

CREATE TABLE LIGNE_COMMANDE_FOURNISSEUR (
    id                       BIGINT AUTO_INCREMENT PRIMARY KEY,
    commande_fournisseur_id  BIGINT        NOT NULL,
    ingredient_id            BIGINT        NOT NULL,
    quantite_commandee       DOUBLE        NOT NULL CHECK (quantite_commandee > 0),
    prix_unitaire            DECIMAL(8, 2) NOT NULL CHECK (prix_unitaire >= 0),
    FOREIGN KEY (commande_fournisseur_id) REFERENCES COMMANDE_FOURNISSEUR(id),
    FOREIGN KEY (ingredient_id)           REFERENCES INGREDIENT(id)
);
