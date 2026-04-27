package fr.epf.restaurant.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Configuration de la base de données H2 en mémoire.
 *
 * EmbeddedDatabaseBuilder exécute automatiquement schema.sql puis data.sql
 * à chaque démarrage du contexte Spring, garantissant un état initial propre.
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    @Bean
    public Server h2TcpServer() throws SQLException {
        Server server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
        try {
            server.start();
            Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
        } catch (SQLException e) {
            if (e.getErrorCode() == 90061) { // port déjà utilisé
                System.out.println("[H2] TCP server non démarré : port 9092 déjà utilisé.");
            } else {
                throw e;
            }
        }
        return server;
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("restaurantdb")
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * Gestionnaire de transactions requis pour que @Transactional fonctionne.
     * En cas de RuntimeException, la transaction est automatiquement annulée (rollback).
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
