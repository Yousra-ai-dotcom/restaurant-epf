package fr.epf.restaurant.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.epf.restaurant.model.Fournisseur;

@Repository
public class FournisseurDao {

    private final JdbcTemplate jdbcTemplate;

    public FournisseurDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Fournisseur> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM FOURNISSEUR",
                new BeanPropertyRowMapper<>(Fournisseur.class)
        );
    }

    public Fournisseur findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM FOURNISSEUR WHERE id = ?",
                new BeanPropertyRowMapper<>(Fournisseur.class),
                id
        );
    }

    public void save(Fournisseur fournisseur) {
        jdbcTemplate.update(
                "INSERT INTO FOURNISSEUR(nom, contact, email) VALUES (?, ?, ?)",
                fournisseur.getNom(),
                fournisseur.getContact(),
                fournisseur.getEmail()
        );
    }
}