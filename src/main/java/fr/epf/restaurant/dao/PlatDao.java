package fr.epf.restaurant.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.epf.restaurant.model.Plat;

@Repository
public class PlatDao {

    private final JdbcTemplate jdbcTemplate;

    public PlatDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Plat> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM PLAT",
                new BeanPropertyRowMapper<>(Plat.class)
        );
    }

    public Plat findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM PLAT WHERE id = ?",
                new BeanPropertyRowMapper<>(Plat.class),
                id
        );
    }

    public void save(Plat plat) {
        jdbcTemplate.update(
                "INSERT INTO PLAT(nom, description, prix) VALUES (?, ?, ?)",
                plat.getNom(),
                plat.getDescription(),
                plat.getPrix()
        );
    }
}