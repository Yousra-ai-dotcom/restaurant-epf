package fr.epf.restaurant.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.epf.restaurant.model.CommandeFournisseur;

@Repository
public class CommandeFournisseurDao {

    private final JdbcTemplate jdbcTemplate;

    public CommandeFournisseurDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CommandeFournisseur> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM COMMANDE_FOURNISSEUR",
                new BeanPropertyRowMapper<>(CommandeFournisseur.class)
        );
    }

    public CommandeFournisseur findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM COMMANDE_FOURNISSEUR WHERE id = ?",
                new BeanPropertyRowMapper<>(CommandeFournisseur.class),
                id
        );
    }

    public void save(Long fournisseurId) {
        jdbcTemplate.update(
                "INSERT INTO COMMANDE_FOURNISSEUR(fournisseur_id) VALUES (?)",
                fournisseurId
        );
    }

    public void updateStatut(Long id, String statut) {
        jdbcTemplate.update(
                "UPDATE COMMANDE_FOURNISSEUR SET statut = ? WHERE id = ?",
                statut,
                id
        );
    }

    public void delete(Long id) {
        jdbcTemplate.update(
                "DELETE FROM COMMANDE_FOURNISSEUR WHERE id = ?",
                id
        );
    }
}