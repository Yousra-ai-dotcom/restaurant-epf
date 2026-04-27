package fr.epf.restaurant.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.epf.restaurant.model.Client;

@Repository
public class ClientDao {

    private final JdbcTemplate jdbcTemplate;

    public ClientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Client> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM CLIENT",
                new BeanPropertyRowMapper<>(Client.class)
        );
    }

    public Client findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM CLIENT WHERE id = ?",
                new BeanPropertyRowMapper<>(Client.class),
                id
        );
    }

    public void save(Client client) {
        jdbcTemplate.update(
                "INSERT INTO CLIENT(nom, prenom, email, telephone) VALUES (?, ?, ?, ?)",
                client.getNom(),
                client.getPrenom(),
                client.getEmail(),
                client.getTelephone()
        );
    }
}