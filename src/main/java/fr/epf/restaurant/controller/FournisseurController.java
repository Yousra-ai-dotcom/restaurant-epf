package fr.epf.restaurant.controller;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.epf.restaurant.dao.FournisseurDao;
import fr.epf.restaurant.model.Fournisseur;

@RestController
@RequestMapping("/api/fournisseurs")
public class FournisseurController {

    private final FournisseurDao fournisseurDao;
    private final JdbcTemplate jdbcTemplate;

    public FournisseurController(FournisseurDao fournisseurDao,
                                 JdbcTemplate jdbcTemplate) {
        this.fournisseurDao = fournisseurDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    // =========================
    // GET /api/fournisseurs
    // =========================
    @GetMapping
    public List<Fournisseur> getAll() {
        return fournisseurDao.findAll();
    }

    // =========================
    // POST /api/fournisseurs
    // =========================
    @PostMapping
    public void create(@RequestBody Fournisseur fournisseur) {
        fournisseurDao.save(fournisseur);
    }

    // =========================
    // GET /api/fournisseurs/{id}/catalogue
    // =========================
    @GetMapping("/{id}/catalogue")
    public List<Map<String, Object>> getCatalogue(@PathVariable Long id) {

        String sql = """
                SELECT i.id AS ingredientId,
                    i.nom AS ingredientNom,
                    i.unite AS ingredientUnite,
                    fi.prix_unitaire AS prixUnitaire
                FROM FOURNISSEUR_INGREDIENT fi
                JOIN INGREDIENT i ON fi.ingredient_id = i.id
                WHERE fi.fournisseur_id = ?
                """;

        return jdbcTemplate.queryForList(sql, id);
    }
}