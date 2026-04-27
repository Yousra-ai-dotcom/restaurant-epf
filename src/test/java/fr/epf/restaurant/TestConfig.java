package fr.epf.restaurant;

import fr.epf.restaurant.config.AppConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Configuration Spring pour les tests.
 * Réutilise AppConfig : même base H2 en mémoire, réinitialisée à chaque démarrage du contexte.
 */
@Configuration
@Import(AppConfig.class)
public class TestConfig {
}
