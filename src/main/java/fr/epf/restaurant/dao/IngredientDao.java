package fr.epf.restaurant.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.epf.restaurant.model.Ingredient;

@Repository
public class IngredientDao {

    private final JdbcTemplate jdbcTemplate;

    public IngredientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Ingredient> findAll() {
        return jdbcTemplate.query(
        "SELECT id, nom, unite, stock_actuel AS stockActuel, seuil_alerte AS seuilAlerte FROM INGREDIENT",
            new BeanPropertyRowMapper<>(Ingredient.class)
        );
    }

    public Ingredient findById(Long id) {
        return jdbcTemplate.queryForObject(
            "SELECT id, nom, unite, stock_actuel AS stockActuel, seuil_alerte AS seuilAlerte "
            + "FROM INGREDIENT WHERE id = ?",
            new BeanPropertyRowMapper<>(Ingredient.class),
            id
        );
    }

    public void updateStock(Long id, Double stock) {
        jdbcTemplate.update(
                "UPDATE INGREDIENT SET stock_actuel = ? WHERE id = ?",
                stock,
                id
        );
    }

    public List<Ingredient> findSousAlerte() {
        return jdbcTemplate.query(
            "SELECT id, nom, unite, stock_actuel AS stockActuel, "
            + "seuil_alerte AS seuilAlerte FROM INGREDIENT "
            + "WHERE stock_actuel < seuil_alerte",
            new BeanPropertyRowMapper<>(Ingredient.class)
        );
    }
}