package fr.epf.restaurant.controller;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.epf.restaurant.dao.IngredientDao;
import fr.epf.restaurant.dto.AlerteStockDto;
import fr.epf.restaurant.dto.RecommandationDto;
import fr.epf.restaurant.model.Ingredient;
import fr.epf.restaurant.service.StockService;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientDao ingredientDao;
    private final StockService stockService;
    private final JdbcTemplate jdbcTemplate;

    public IngredientController(IngredientDao ingredientDao,
                                StockService stockService,
                                JdbcTemplate jdbcTemplate) {
        this.ingredientDao = ingredientDao;
        this.stockService = stockService;
        this.jdbcTemplate = jdbcTemplate;
    }

    // =========================
    // GET /api/ingredients
    // =========================
    @GetMapping
    public List<Ingredient> getAll() {
        return ingredientDao.findAll();
    }

    // =========================
    // GET /api/ingredients/alertes
    // =========================
    @GetMapping("/alertes")
    public List<AlerteStockDto> getAlertes() {
        return stockService.getAlertesStock();
    }

    // =========================
    // GET /api/ingredients/{id}/prix
    // =========================
    @GetMapping("/{id}/prix")
    public List<Map<String, Object>> getPrix(@PathVariable Long id) {

        String sql = """
                SELECT
                    f.id AS fournisseurId,
                    f.nom AS fournisseurNom,
                    fi.prix_unitaire AS prixUnitaire
                FROM FOURNISSEUR_INGREDIENT fi
                JOIN FOURNISSEUR f ON fi.fournisseur_id = f.id
                WHERE fi.ingredient_id = ?
                ORDER BY prix_unitaire
                """;

        return jdbcTemplate.queryForList(sql, id);
    }

    // =========================
    // GET /api/ingredients/{id}/recommandation
    // =========================
    @GetMapping("/{id}/recommandation")
    public RecommandationDto getRecommandation(@PathVariable Long id) {

        Ingredient ingredient = ingredientDao.findById(id);

        String sql = """
                SELECT
                    f.id AS fournisseurId,
                    f.nom AS fournisseurNom,
                    fi.prix_unitaire AS prixUnitaire
                FROM FOURNISSEUR_INGREDIENT fi
                JOIN FOURNISSEUR f ON fi.fournisseur_id = f.id
                WHERE fi.ingredient_id = ?
                ORDER BY prix_unitaire
                LIMIT 1
                """;

        Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);

        Double stock = ingredient.getStockActuel();
        Double seuil = ingredient.getSeuilAlerte();

        Double quantite;

        if (seuil > stock) {
            quantite = 2 * (seuil - stock);
        } else {
            quantite = seuil;
        }

        RecommandationDto dto = new RecommandationDto();

        dto.setFournisseurId(((Number) result.get("fournisseurId")).longValue());
        dto.setFournisseurNom((String) result.get("fournisseurNom"));
        dto.setPrixUnitaire((java.math.BigDecimal) result.get("prixUnitaire"));
        dto.setQuantiteRecommandee(quantite);

        return dto;
    }
}