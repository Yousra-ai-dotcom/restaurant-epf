package fr.epf.restaurant;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import fr.epf.restaurant.dto.PreparationResultDto;
import fr.epf.restaurant.model.CommandeClient;
import fr.epf.restaurant.service.CommandeClientService;

@SpringJUnitConfig(TestConfig.class)
public class CommandeClientServiceTest {

    @Autowired
    private CommandeClientService service;

    @Test
    void testFindAllCommandes() {

        List<CommandeClient> commandes = service.findAll();

        assertNotNull(commandes);
    }

    @Test
    void testFindCommandeById() {

        service.create(1L);

        CommandeClient commande = service.findAll().get(0);

        assertNotNull(commande);
    }

    @Test
    void testPreparerCommande() {

        service.create(1L);

        CommandeClient commande = service.findAll().get(0);

        PreparationResultDto result =
            service.preparerCommande(commande.getId());

        assertNotNull(result);
    }

    @Test
    void testServirCommande() {

        service.create(1L);

        CommandeClient commande =
            service.findAll().get(service.findAll().size() - 1);

        service.preparerCommande(commande.getId());

        service.servirCommande(commande.getId());

        CommandeClient updated = service.findById(commande.getId());

        assertTrue("SERVIE".equals(updated.getStatut()));
    }

    @Test
    void testDeleteCommande() {

        service.delete(1L);

        service.create(1L);

        List<CommandeClient> before = service.findAll();

        service.delete(before.get(0).getId());

        List<CommandeClient> after = service.findAll();

        assertTrue(after.size() < before.size());
    }
    @Test
    void testPreparerCommandeStatutInvalide() {

        service.create(1L);

        List<CommandeClient> commandes = service.findAll();
        CommandeClient commande = commandes.get(commandes.size() - 1);
        service.preparerCommande(commande.getId());
        assertThrows(RuntimeException.class, () -> {
            service.preparerCommande(commande.getId());
        });
    }
    @Test
    void testServirCommandeStatutInvalide() {

        service.create(1L);

        List<CommandeClient> commandes = service.findAll();
        CommandeClient commande = commandes.get(commandes.size() - 1);

        assertThrows(RuntimeException.class, () -> {
            service.servirCommande(commande.getId());
        });
    }
}