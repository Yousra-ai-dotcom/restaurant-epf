package fr.epf.restaurant.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Expose la documentation OpenAPI 3.0 au format JSON sur /v3/api-docs
 * et une page Swagger UI sur /swagger.
 */
@RestController
public class OpenApiController {

    @GetMapping(value = "/v3/api-docs", produces = MediaType.APPLICATION_JSON_VALUE)
    public String apiDocs() {
        return """
        {
          "openapi": "3.0.3",
          "info": {
            "title": "Restaurant EPF – API",
            "description": "Gestion des commandes clients et fournisseurs du restaurant La Table de Margot",
            "version": "1.0.0"
          },
          "servers": [{ "url": "http://localhost:8080" }],
          "tags": [
            { "name": "Plats",                  "description": "Menu du restaurant" },
            { "name": "Clients",                "description": "Clients du restaurant" },
            { "name": "Fournisseurs",           "description": "Fournisseurs" },
            { "name": "Ingrédients",            "description": "Stock d'ingrédients" },
            { "name": "Commandes client",       "description": "Gestion des commandes des clients" },
            { "name": "Commandes fournisseur",  "description": "Demandes de livraison fournisseur" }
          ],
          "paths": {
            "/api/plats": {
              "get": {
                "tags": ["Plats"],
                "summary": "Lister tous les plats",
                "operationId": "listerPlats",
                "responses": {
                  "200": {
                    "description": "Liste des plats",
                    "content": { "application/json": { "schema": {
                      "type": "array", "items": { "$ref": "#/components/schemas/Plat" }
                    } } }
                  }
                }
              }
            },
            "/api/plats/{id}": {
              "get": {
                "tags": ["Plats"],
                "summary": "Obtenir un plat avec ses ingrédients et quantités requises",
                "operationId": "obtenirPlat",
                "parameters": [{
                  "name": "id", "in": "path", "required": true,
                  "schema": { "type": "integer", "format": "int64" }
                }],
                "responses": {
                  "200": { "description": "Plat trouvé", "content": {
                    "application/json": { "schema": { "$ref": "#/components/schemas/PlatDetail" } }
                  } },
                  "404": { "description": "Plat introuvable" }
                }
              }
            },
            "/api/clients": {
              "get": {
                "tags": ["Clients"],
                "summary": "Lister tous les clients",
                "operationId": "listerClients",
                "responses": {
                  "200": {
                    "description": "Liste des clients",
                    "content": { "application/json": { "schema": {
                      "type": "array", "items": { "$ref": "#/components/schemas/Client" }
                    } } }
                  }
                }
              }
            },
            "/api/fournisseurs": {
              "get": {
                "tags": ["Fournisseurs"],
                "summary": "Lister tous les fournisseurs",
                "operationId": "listerFournisseurs",
                "responses": {
                  "200": {
                    "description": "Liste des fournisseurs",
                    "content": { "application/json": { "schema": {
                      "type": "array", "items": { "$ref": "#/components/schemas/Fournisseur" }
                    } } }
                  }
                }
              }
            },
            "/api/ingredients": {
              "get": {
                "tags": ["Ingrédients"],
                "summary": "Lister tous les ingrédients",
                "operationId": "listerIngredients",
                "responses": {
                  "200": {
                    "description": "Liste des ingrédients",
                    "content": { "application/json": { "schema": {
                      "type": "array", "items": { "$ref": "#/components/schemas/Ingredient" }
                    } } }
                  }
                }
              }
            },
            "/api/ingredients/alertes": {
              "get": {
                "tags": ["Ingrédients"],
                "summary": "Ingrédients sous le seuil d'alerte",
                "operationId": "ingredientsEnAlerte",
                "responses": {
                  "200": {
                    "description": "Ingrédients dont le stock est inférieur au seuil",
                    "content": { "application/json": { "schema": {
                      "type": "array", "items": { "$ref": "#/components/schemas/Ingredient" }
                    } } }
                  }
                }
              }
            },
            "/api/commandes/client": {
              "get": {
                "tags": ["Commandes client"],
                "summary": "Lister les commandes clients",
                "operationId": "listerCommandesClient",
                "parameters": [
                  {
                    "name": "statut",
                    "in": "query",
                    "required": false,
                    "description": "Filtrer par statut (EN_ATTENTE, EN_PREPARATION, SERVIE)",
                    "schema": {
                      "type": "string",
                      "enum": ["EN_ATTENTE", "EN_PREPARATION", "SERVIE"]
                    }
                  }
                ],
                "responses": {
                  "200": {
                    "description": "Liste des commandes",
                    "content": { "application/json": { "schema": {
                      "type": "array",
                      "items": { "$ref": "#/components/schemas/CommandeClient" }
                    } } }
                  }
                }
              },
              "post": {
                "tags": ["Commandes client"],
                "summary": "Créer une commande client",
                "operationId": "creerCommandeClient",
                "requestBody": {
                  "required": true,
                  "content": { "application/json": { "schema": {
                    "$ref": "#/components/schemas/CreerCommandeClientRequest"
                  } } }
                },
                "responses": {
                  "201": {
                    "description": "Commande créée",
                    "content": { "application/json": { "schema": {
                      "$ref": "#/components/schemas/CommandeClient"
                    } } }
                  },
                  "400": {
                    "description": "Données invalides",
                    "content": { "application/json": { "schema": {
                      "$ref": "#/components/schemas/ErreurResponse"
                    } } }
                  }
                }
              }
            },
            "/api/commandes/client/{id}": {
              "get": {
                "tags": ["Commandes client"],
                "summary": "Obtenir une commande client avec ses lignes",
                "operationId": "obtenirCommandeClient",
                "parameters": [{
                  "name": "id", "in": "path", "required": true,
                  "schema": { "type": "integer", "format": "int64" }
                }],
                "responses": {
                  "200": { "description": "Commande trouvée", "content": {
                    "application/json": { "schema": {
                      "$ref": "#/components/schemas/CommandeClient"
                    } }
                  } },
                  "404": { "description": "Commande introuvable" }
                }
              }
            },
            "/api/commandes/client/{id}/preparer": {
              "put": {
                "tags": ["Commandes client"],
                "summary": "Passer la commande EN_PREPARATION (consomme le stock)",
                "operationId": "preparerCommandeClient",
                "parameters": [{
                  "name": "id", "in": "path", "required": true,
                  "schema": { "type": "integer", "format": "int64" }
                }],
                "responses": {
                  "200": {
                    "description": "Statut mis à jour",
                    "content": { "application/json": { "schema": {
                      "$ref": "#/components/schemas/CommandeClient"
                    } } }
                  },
                  "400": {
                    "description": "Stock insuffisant ou transition invalide",
                    "content": { "application/json": { "schema": {
                      "$ref": "#/components/schemas/ErreurResponse"
                    } } }
                  }
                }
              }
            },
            "/api/commandes/client/{id}/servir": {
              "put": {
                "tags": ["Commandes client"],
                "summary": "Marquer la commande comme SERVIE",
                "operationId": "servirCommandeClient",
                "parameters": [{
                  "name": "id", "in": "path", "required": true,
                  "schema": { "type": "integer", "format": "int64" }
                }],
                "responses": {
                  "200": {
                    "description": "Statut mis à jour",
                    "content": { "application/json": { "schema": {
                      "$ref": "#/components/schemas/CommandeClient"
                    } } }
                  },
                  "400": {
                    "description": "Transition de statut invalide",
                    "content": { "application/json": { "schema": {
                      "$ref": "#/components/schemas/ErreurResponse"
                    } } }
                  }
                }
              }
            },
            "/api/commandes/fournisseur": {
              "get": {
                "tags": ["Commandes fournisseur"],
                "summary": "Lister les commandes fournisseur",
                "operationId": "listerCommandesFournisseur",
                "parameters": [
                  {
                    "name": "statut",
                    "in": "query",
                    "required": false,
                    "description": "Filtrer par statut (EN_ATTENTE, ENVOYEE, RECUE)",
                    "schema": {
                      "type": "string",
                      "enum": ["EN_ATTENTE", "ENVOYEE", "RECUE"]
                    }
                  }
                ],
                "responses": {
                  "200": {
                    "description": "Liste des commandes fournisseur",
                    "content": { "application/json": { "schema": {
                      "type": "array",
                      "items": { "$ref": "#/components/schemas/CommandeFournisseur" }
                    } } }
                  }
                }
              },
              "post": {
                "tags": ["Commandes fournisseur"],
                "summary": "Créer une commande fournisseur (demande de livraison)",
                "operationId": "creerCommandeFournisseur",
                "requestBody": {
                  "required": true,
                  "content": { "application/json": { "schema": {
                    "$ref": "#/components/schemas/CreerCommandeFournisseurRequest"
                  } } }
                },
                "responses": {
                  "201": {
                    "description": "Commande créée",
                    "content": { "application/json": { "schema": {
                      "$ref": "#/components/schemas/CommandeFournisseur"
                    } } }
                  },
                  "400": {
                    "description": "Données invalides",
                    "content": { "application/json": { "schema": {
                      "$ref": "#/components/schemas/ErreurResponse"
                    } } }
                  }
                }
              }
            },
            "/api/commandes/fournisseur/{id}": {
              "get": {
                "tags": ["Commandes fournisseur"],
                "summary": "Obtenir une commande fournisseur avec ses lignes",
                "operationId": "obtenirCommandeFournisseur",
                "parameters": [{
                  "name": "id", "in": "path", "required": true,
                  "schema": { "type": "integer", "format": "int64" }
                }],
                "responses": {
                  "200": { "description": "Commande trouvée", "content": {
                    "application/json": { "schema": {
                      "$ref": "#/components/schemas/CommandeFournisseur"
                    } }
                  } },
                  "404": { "description": "Commande introuvable" }
                }
              }
            },
            "/api/commandes/fournisseur/{id}/envoyer": {
              "put": {
                "tags": ["Commandes fournisseur"],
                "summary": "Envoyer la commande au fournisseur (EN_ATTENTE → ENVOYEE)",
                "operationId": "envoyerCommandeFournisseur",
                "parameters": [{
                  "name": "id", "in": "path", "required": true,
                  "schema": { "type": "integer", "format": "int64" }
                }],
                "responses": {
                  "200": {
                    "description": "Statut mis à jour",
                    "content": { "application/json": { "schema": {
                      "$ref": "#/components/schemas/CommandeFournisseur"
                    } } }
                  },
                  "400": {
                    "description": "Transition invalide",
                    "content": { "application/json": { "schema": {
                      "$ref": "#/components/schemas/ErreurResponse"
                    } } }
                  }
                }
              }
            },
            "/api/commandes/fournisseur/{id}/recevoir": {
              "put": {
                "tags": ["Commandes fournisseur"],
                "summary": "Réceptionner la livraison (ENVOYEE → RECUE, met à jour le stock)",
                "operationId": "recevoirCommandeFournisseur",
                "parameters": [{
                  "name": "id", "in": "path", "required": true,
                  "schema": { "type": "integer", "format": "int64" }
                }],
                "responses": {
                  "200": {
                    "description": "Stock mis à jour",
                    "content": { "application/json": { "schema": {
                      "$ref": "#/components/schemas/CommandeFournisseur"
                    } } }
                  },
                  "400": {
                    "description": "Transition invalide",
                    "content": { "application/json": { "schema": {
                      "$ref": "#/components/schemas/ErreurResponse"
                    } } }
                  }
                }
              }
            },
          },
          "components": {
            "schemas": {
              "Plat": {
                "type": "object",
                "properties": {
                  "id":          { "type": "integer", "format": "int64" },
                  "nom":         { "type": "string", "example": "Quiche Lorraine" },
                  "description": { "type": "string" },
                  "prix":        { "type": "number", "format": "double", "example": 12.50 }
                }
              },
              "PlatDetail": {
                "type": "object",
                "properties": {
                  "id":          { "type": "integer", "format": "int64" },
                  "nom":         { "type": "string", "example": "Quiche Lorraine" },
                  "description": { "type": "string" },
                  "prix":        { "type": "number", "format": "double", "example": 12.50 },
                  "ingredients": {
                    "type": "array",
                    "items": { "$ref": "#/components/schemas/PlatIngredient" }
                  }
                }
              },
              "PlatIngredient": {
                "type": "object",
                "properties": {
                  "ingredient":      { "$ref": "#/components/schemas/Ingredient" },
                  "quantiteRequise": { "type": "number", "format": "double", "example": 2.0 }
                }
              },
              "Client": {
                "type": "object",
                "properties": {
                  "id":        { "type": "integer", "format": "int64" },
                  "nom":       { "type": "string", "example": "Dupont" },
                  "prenom":    { "type": "string", "example": "Alice" },
                  "email":     { "type": "string", "format": "email" },
                  "telephone": { "type": "string" }
                }
              },
              "Fournisseur": {
                "type": "object",
                "properties": {
                  "id":      { "type": "integer", "format": "int64" },
                  "nom":     { "type": "string", "example": "Metro Cash and Carry" },
                  "contact": { "type": "string" },
                  "email":   { "type": "string", "format": "email" }
                }
              },
              "Ingredient": {
                "type": "object",
                "properties": {
                  "id":           { "type": "integer", "format": "int64" },
                  "nom":          { "type": "string", "example": "Oeufs" },
                  "unite":        { "type": "string", "example": "unités" },
                  "stockActuel":  { "type": "number", "format": "double" },
                  "seuilAlerte":  { "type": "number", "format": "double" }
                }
              },
              "CommandeClient": {
                "type": "object",
                "properties": {
                  "id":           { "type": "integer", "format": "int64" },
                  "client":       { "$ref": "#/components/schemas/Client" },
                  "dateCommande": { "type": "string", "format": "date-time" },
                  "statut": {
                    "type": "string",
                    "enum": ["EN_ATTENTE", "EN_PREPARATION", "SERVIE"]
                  },
                  "lignes": {
                    "type": "array",
                    "items": { "$ref": "#/components/schemas/LigneCommandeClient" }
                  }
                }
              },
              "LigneCommandeClient": {
                "type": "object",
                "properties": {
                  "id":       { "type": "integer", "format": "int64" },
                  "plat":     { "$ref": "#/components/schemas/Plat" },
                  "quantite": { "type": "integer" }
                }
              },
              "CommandeFournisseur": {
                "type": "object",
                "properties": {
                  "id":           { "type": "integer", "format": "int64" },
                  "fournisseur":  { "$ref": "#/components/schemas/Fournisseur" },
                  "dateCommande": { "type": "string", "format": "date-time" },
                  "statut": {
                    "type": "string",
                    "enum": ["EN_ATTENTE", "ENVOYEE", "RECUE"]
                  },
                  "lignes": {
                    "type": "array",
                    "items": { "$ref": "#/components/schemas/LigneCommandeFournisseur" }
                  }
                }
              },
              "LigneCommandeFournisseur": {
                "type": "object",
                "properties": {
                  "id":                { "type": "integer", "format": "int64" },
                  "ingredient":        { "$ref": "#/components/schemas/Ingredient" },
                  "quantiteCommandee": { "type": "number", "format": "double" },
                  "prixUnitaire":      { "type": "number", "format": "double" }
                }
              },
              "CreerCommandeClientRequest": {
                "type": "object",
                "required": ["clientId", "lignes"],
                "properties": {
                  "clientId": { "type": "integer", "format": "int64", "example": 1 },
                  "lignes": {
                    "type": "array",
                    "items": {
                      "type": "object",
                      "required": ["platId", "quantite"],
                      "properties": {
                        "platId":   { "type": "integer", "format": "int64", "example": 1 },
                        "quantite": { "type": "integer", "example": 2 }
                      }
                    }
                  }
                }
              },
              "CreerCommandeFournisseurRequest": {
                "type": "object",
                "required": ["fournisseurId", "lignes"],
                "properties": {
                  "fournisseurId": { "type": "integer", "format": "int64", "example": 1 },
                  "lignes": {
                    "type": "array",
                    "items": {
                      "type": "object",
                      "required": ["ingredientId", "quantite", "prixUnitaire"],
                      "properties": {
                        "ingredientId": { "type": "integer", "format": "int64", "example": 2 },
                        "quantite":     { "type": "number", "format": "double", "example": 100.0 },
                        "prixUnitaire": { "type": "number", "format": "double", "example": 0.30 }
                      }
                    }
                  }
                }
              },
              "ErreurResponse": {
                "type": "object",
                "properties": {
                  "status":  { "type": "integer" },
                  "message": { "type": "string" }
                }
              }
            }
          }
        }
        """;
    }

    @GetMapping(value = "/swagger", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String swaggerUi() {
        return """
        <!DOCTYPE html>
        <html lang="fr">
        <head>
            <meta charset="UTF-8">
            <title>Restaurant EPF - API Docs</title>
            <link rel="stylesheet" href="https://unpkg.com/swagger-ui-dist@5.17.14/swagger-ui.css">
            <style>body { margin: 0; }</style>
        </head>
        <body>
            <div id="swagger-ui"></div>
            <script src="https://unpkg.com/swagger-ui-dist@5.17.14/swagger-ui-bundle.js"></script>
            <script src="https://unpkg.com/swagger-ui-dist@5.17.14/swagger-ui-standalone-preset.js">
            </script>
            <script>
            window.onload = function () {
                SwaggerUIBundle({
                    url: "/v3/api-docs",
                    dom_id: '#swagger-ui',
                    deepLinking: true,
                    presets: [
                        SwaggerUIBundle.presets.apis,
                        SwaggerUIStandalonePreset
                    ],
                    plugins: [SwaggerUIBundle.plugins.DownloadUrl],
                    layout: "StandaloneLayout"
                });
            };
            </script>
        </body>
        </html>
        """;
    }
}
