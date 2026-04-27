package fr.epf.restaurant.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.epf.restaurant.model.Client;
import fr.epf.restaurant.model.CommandeClient;

@Repository
public class CommandeClientDao {

    private final JdbcTemplate jdbcTemplate;

    public CommandeClientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CommandeClient> findAll() {
        String sql = """
            SELECT c.id,
                c.client_id AS client_id,
                c.date_commande,
                c.statut,
                cl.id AS client_id,
                cl.nom AS client_nom,
                cl.prenom AS client_prenom,
                COALESCE(SUM(l.quantite * p.prix), 0) AS total
            FROM COMMANDE_CLIENT c
            JOIN CLIENT cl ON c.client_id = cl.id
            LEFT JOIN LIGNE_COMMANDE_CLIENT l ON l.commande_client_id = c.id
            LEFT JOIN PLAT p ON p.id = l.plat_id
            GROUP BY c.id, c.client_id, c.date_commande, c.statut, cl.id, cl.nom, cl.prenom
            ORDER BY c.id
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CommandeClient cmd = new CommandeClient();

            cmd.setId(rs.getLong("id"));
            cmd.setDateCommande(rs.getTimestamp("date_commande").toLocalDateTime());
            cmd.setStatut(rs.getString("statut"));

            cmd.setClientId(rs.getLong("client_id"));
            cmd.setTotal(rs.getDouble("total"));

            Client client = new Client();
            client.setId(rs.getLong("client_id"));
            client.setNom(rs.getString("client_nom"));
            client.setPrenom(rs.getString("client_prenom"));

            cmd.setClient(client);

            cmd.setClientNom(
                rs.getString("client_prenom") + " " + rs.getString("client_nom")
            );

            return cmd;
        });
    }

    public CommandeClient findById(Long id) {
        String sql = """
            SELECT c.id,
                c.date_commande,
                c.statut,
                cl.id AS client_id,
                cl.nom AS client_nom,
                cl.prenom AS client_prenom,
                COALESCE(SUM(l.quantite * p.prix), 0) AS total
            FROM COMMANDE_CLIENT c
            JOIN CLIENT cl ON c.client_id = cl.id
            LEFT JOIN LIGNE_COMMANDE_CLIENT l ON l.commande_client_id = c.id
            LEFT JOIN PLAT p ON p.id = l.plat_id
            WHERE c.id = ?
            GROUP BY c.id, c.date_commande, c.statut, cl.id, cl.nom, cl.prenom
        """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            CommandeClient cmd = new CommandeClient();

            cmd.setId(rs.getLong("id"));
            cmd.setDateCommande(rs.getTimestamp("date_commande").toLocalDateTime());
            cmd.setStatut(rs.getString("statut"));
            cmd.setClientId(rs.getLong("client_id"));
            cmd.setTotal(rs.getDouble("total"));

            Client client = new Client();
            client.setId(rs.getLong("client_id"));
            client.setNom(rs.getString("client_nom"));
            client.setPrenom(rs.getString("client_prenom"));

            cmd.setClient(client);
            cmd.setClientNom(
                rs.getString("client_prenom") + " " + rs.getString("client_nom")
            );


            return cmd;
        }, id);
    }

    public Long save(Long clientId) {

        jdbcTemplate.update(
            "INSERT INTO COMMANDE_CLIENT(client_id, date_commande, statut) VALUES (?, CURRENT_TIMESTAMP, 'EN_ATTENTE')",

            clientId
        );

        return jdbcTemplate.queryForObject(
            "SELECT MAX(id) FROM COMMANDE_CLIENT",
            Long.class
        );
    }

    public void updateStatut(Long id, String statut) {
        jdbcTemplate.update(
                "UPDATE COMMANDE_CLIENT SET statut = ? WHERE id = ?",
                statut,
                id
        );
    }

    public List<CommandeClient> findByStatut(String statut) {
        String sql = """
            SELECT c.id,
               c.date_commande,
               c.statut,
               cl.id AS client_id,
               cl.nom AS client_nom,
               cl.prenom AS client_prenom
            FROM COMMANDE_CLIENT c
            JOIN CLIENT cl ON c.client_id = cl.id
            WHERE c.statut = ?
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CommandeClient cmd = new CommandeClient();

            cmd.setId(rs.getLong("id"));
            cmd.setDateCommande(rs.getTimestamp("date_commande").toLocalDateTime());
            cmd.setStatut(rs.getString("statut"));

            Client client = new Client();
            client.setId(rs.getLong("client_id"));
            client.setNom(rs.getString("client_nom"));
            client.setPrenom(rs.getString("client_prenom"));

            cmd.setClient(client);

            return cmd;
        }, statut);
    }

    public void delete(Long id) {
        jdbcTemplate.update(
                "DELETE FROM COMMANDE_CLIENT WHERE id = ?",
                id
        );
    }
    public void addLigne(Long commandeId, Long platId, int quantite) {
        String sql = """
            INSERT INTO LIGNE_COMMANDE_CLIENT (commande_client_id, plat_id, quantite)
            VALUES (?, ?, ?)
        """;

        jdbcTemplate.update(sql, commandeId, platId, quantite);
    }
}