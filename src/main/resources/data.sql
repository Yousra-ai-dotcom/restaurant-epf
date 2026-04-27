-- ============================================================
-- data.sql - Données initiales
-- ============================================================

-- Ingrédients (certains partagés entre plusieurs plats)
--   id=1 : Pate brisee      -> Quiche Lorraine
--   id=2 : Oeufs            -> Quiche Lorraine + Omelette aux lardons
--   id=3 : Creme fraiche    -> Quiche Lorraine + Gratin dauphinois
--   id=4 : Lardons          -> Quiche Lorraine + Omelette aux lardons
--   id=5 : Gruyere          -> Quiche Lorraine + Gratin dauphinois
--   id=6 : Beurre           -> Omelette aux lardons + Gratin dauphinois
--   id=7 : Pommes de terre  -> Gratin dauphinois
INSERT INTO INGREDIENT (nom, unite, stock_actuel, seuil_alerte) VALUES
    ('Pate brisee',     'grammes', 500.0,  200.0),
    ('Oeufs',           'unites',   24.0,    6.0),
    ('Creme fraiche',   'grammes', 800.0,  200.0),
    ('Lardons',         'grammes', 600.0,  150.0),
    ('Gruyere',         'grammes', 400.0,  100.0),
    ('Beurre',          'grammes', 300.0,   80.0),
    ('Pommes de terre', 'grammes', 1500.0, 500.0);

-- Les 3 plats
INSERT INTO PLAT (nom, description, prix) VALUES
    ('Quiche Lorraine',      'Quiche traditionnelle aux lardons et fromage',  12.50),
    ('Omelette aux lardons', 'Omelette baveuse garnie de lardons fumes',       9.90),
    ('Gratin dauphinois',    'Gratin cremeux de pommes de terre au four',     11.00);

-- Quiche Lorraine (id=1) : 5 ingrédients
INSERT INTO PLAT_INGREDIENT (plat_id, ingredient_id, quantite_requise) VALUES
    (1, 1, 200.0),   -- 200g pate brisee
    (1, 2,   3.0),   -- 3 oeufs
    (1, 3, 150.0),   -- 150g creme fraiche
    (1, 4, 100.0),   -- 100g lardons
    (1, 5,  80.0);   -- 80g gruyere

-- Omelette aux lardons (id=2) : 3 ingrédients (oeufs et lardons partagés avec Quiche)
INSERT INTO PLAT_INGREDIENT (plat_id, ingredient_id, quantite_requise) VALUES
    (2, 2,  3.0),    -- 3 oeufs
    (2, 4, 80.0),    -- 80g lardons
    (2, 6, 20.0);    -- 20g beurre

-- Gratin dauphinois (id=3) : 4 ingrédients (creme, gruyere, beurre partagés)
INSERT INTO PLAT_INGREDIENT (plat_id, ingredient_id, quantite_requise) VALUES
    (3, 7, 400.0),   -- 400g pommes de terre
    (3, 3, 200.0),   -- 200g creme fraiche
    (3, 5, 100.0),   -- 100g gruyere
    (3, 6,  30.0);   -- 30g beurre

-- Clients
INSERT INTO CLIENT (nom, prenom, email, telephone) VALUES
    ('Dupont',  'Alice',  'alice.dupont@email.fr',   '0601020304'),
    ('Martin',  'Bob',    'bob.martin@email.fr',     '0605060708'),
    ('Bernard', 'Claire', 'claire.bernard@email.fr', '0609101112');

-- Fournisseurs
INSERT INTO FOURNISSEUR (nom, contact, email) VALUES
    ('Metro Cash and Carry', 'Jean Leroy',   'commandes@metro.fr'),
    ('Transgourmet',         'Sophie Blanc', 'pro@transgourmet.fr');

-- Catalogue des prix fournisseurs (Metro=1, Transgourmet=2)
-- Prix au gramme (ou à l'unité pour les oeufs)
-- Metro est moins cher sur : Oeufs, Gruyere, Pommes de terre
-- Transgourmet est moins cher sur : Pate brisee, Creme fraiche, Lardons, Beurre
INSERT INTO FOURNISSEUR_INGREDIENT (fournisseur_id, ingredient_id, prix_unitaire) VALUES
    (1, 1, 0.06),   -- Metro       : Pate brisee      0.06 €/g
    (1, 2, 0.42),   -- Metro       : Oeufs            0.42 €/unité
    (1, 3, 0.08),   -- Metro       : Creme fraiche    0.08 €/g
    (1, 4, 0.19),   -- Metro       : Lardons          0.19 €/g
    (1, 5, 0.24),   -- Metro       : Gruyere          0.24 €/g
    (1, 6, 0.11),   -- Metro       : Beurre           0.11 €/g
    (1, 7, 0.03),   -- Metro       : Pommes de terre  0.03 €/g
    (2, 1, 0.05),   -- Transgourmet: Pate brisee      0.05 €/g
    (2, 2, 0.35),   -- Transgourmet: Oeufs            0.35 €/unité
    (2, 3, 0.07),   -- Transgourmet: Creme fraiche    0.07 €/g
    (2, 4, 0.16),   -- Transgourmet: Lardons          0.16 €/g
    (2, 5, 0.31),   -- Transgourmet: Gruyere          0.31 €/g
    (2, 6, 0.09),   -- Transgourmet: Beurre           0.09 €/g
    (2, 7, 0.04);   -- Transgourmet: Pommes de terre  0.04 €/g
