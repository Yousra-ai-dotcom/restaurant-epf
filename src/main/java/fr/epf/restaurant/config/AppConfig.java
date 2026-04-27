package fr.epf.restaurant.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Configuration principale Spring.
 *
 * Scanne uniquement les packages dao et service pour éviter de charger
 * WebConfig (qui nécessite un ServletContext) dans les contextes de test.
 * DatabaseConfig est importé explicitement.
 *
 * Note : pas de Spring Boot. Le contexte est démarré manuellement
 * dans Main.java avec AnnotationConfigApplicationContext.
 */
@Configuration
@ComponentScan(basePackages = {"fr.epf.restaurant.dao", "fr.epf.restaurant.service"})
@Import(DatabaseConfig.class)
public class AppConfig {
}
