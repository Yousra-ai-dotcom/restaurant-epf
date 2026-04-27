package fr.epf.restaurant;

import fr.epf.restaurant.config.AppConfig;
import fr.epf.restaurant.config.WebConfig;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Point d'entrée : démarre un serveur Tomcat embarqué sur le port 8080.
 *
 * Architecture des contextes Spring :
 *   rootContext  (AnnotationConfigApplicationContext / AppConfig)
 *     └── webContext (AnnotationConfigWebApplicationContext / WebConfig)
 *           └── DispatcherServlet → routes /api/**
 *
 * Lancement : java -jar target/restaurant-epf-1.0-SNAPSHOT-shaded.jar
 */
public class Main {

    public static void main(String[] args) throws Exception {

        // ---- Contexte racine : DAOs + Services ----
        AnnotationConfigApplicationContext rootContext =
                new AnnotationConfigApplicationContext(AppConfig.class);

        // ---- Contexte web : Controllers ----
        AnnotationConfigWebApplicationContext webContext =
                new AnnotationConfigWebApplicationContext();
        webContext.setParent(rootContext);
        webContext.register(WebConfig.class);
        // Le refresh sera déclenché par le DispatcherServlet au démarrage du servlet

        // ---- Tomcat embarqué ----
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector(); // crée le connecteur HTTP (requis Tomcat 10)

        Context ctx = tomcat.addContext("", System.getProperty("java.io.tmpdir"));

        DispatcherServlet dispatcher = new DispatcherServlet(webContext);
        Tomcat.addServlet(ctx, "dispatcher", dispatcher).setLoadOnStartup(1);
        ctx.addServletMappingDecoded("/*", "dispatcher");

        tomcat.start();
        System.out.println("============================================================");
        System.out.println("  Serveur démarré sur http://localhost:8080");
        System.out.println("  Swagger UI  : http://localhost:8080/swagger");
        System.out.println("  OpenAPI JSON: http://localhost:8080/v3/api-docs");
        System.out.println("  API disponible :");
        System.out.println("    GET  /api/plats");
        System.out.println("    GET  /api/clients");
        System.out.println("    GET  /api/fournisseurs");
        System.out.println("    GET  /api/ingredients");
        System.out.println("    GET  /api/ingredients/alertes");
        System.out.println("    GET  /api/commandes/client");
        System.out.println("    POST /api/commandes/client");
        System.out.println("    GET  /api/commandes/fournisseur");
        System.out.println("    POST /api/commandes/fournisseur");
        System.out.println("============================================================");
        tomcat.getServer().await();
    }
}
