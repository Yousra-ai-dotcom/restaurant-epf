package fr.epf.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.epf.restaurant.dao.CommandeClientDao;
import fr.epf.restaurant.dto.CreerCommandeClientRequest;
import fr.epf.restaurant.dto.LigneCommandeClientRequest;
import fr.epf.restaurant.dto.PreparationResultDto;
import fr.epf.restaurant.exception.StatutCommandeInvalideException;
import fr.epf.restaurant.model.CommandeClient;

@Service
public class CommandeClientService {

    private final CommandeClientDao commandeClientDao;
    private final StockService stockService;

    public CommandeClientService(CommandeClientDao commandeClientDao,
                                 StockService stockService) {
        this.commandeClientDao = commandeClientDao;
        this.stockService = stockService;
    }

    // Liste commandes
    public List<CommandeClient> findAll() {
        return commandeClientDao.findAll();
    }

    // Trouver commande
    public CommandeClient findById(Long id) {
        return commandeClientDao.findById(id);
    }

    // Créer commande
    @Transactional
    public void create(CreerCommandeClientRequest request) {
        if (request.getClientId() == null) {
            throw new RuntimeException("Client obligatoire");
        }
        Long commandeId = commandeClientDao.save(request.getClientId());

        for (LigneCommandeClientRequest ligne : request.getLignes()) {
            commandeClientDao.addLigne(
                commandeId,
                ligne.getPlatId(),
                ligne.getQuantite()
        );
        }
    }
    @Transactional
    public void create(Long clientId) {
        commandeClientDao.save(clientId);
    }

    // Préparer commande
    @Transactional
    public PreparationResultDto preparerCommande(Long id) {

        CommandeClient commande = commandeClientDao.findById(id);

        if (!"EN_ATTENTE".equals(commande.getStatut())) {
            throw new StatutCommandeInvalideException(
                "La commande doit être EN_ATTENTE pour être préparée"
            );
        }
        commandeClientDao.updateStatut(id, "EN_PREPARATION");
        CommandeClient updated = commandeClientDao.findById(id);
        return new PreparationResultDto(updated, List.of());
    }

    // Servir commande
    public void servirCommande(Long id) {

        CommandeClient commande = commandeClientDao.findById(id);

        if (!"EN_PREPARATION".equals(commande.getStatut())) {
            throw new StatutCommandeInvalideException(
                "La commande doit être EN_PREPARATION pour être servie"
            );
        }

        commandeClientDao.updateStatut(id, "SERVIE");
    }

    // Filtrer par statut
    public List<CommandeClient> findByStatut(String statut) {
        return commandeClientDao.findByStatut(statut);
    }

    // Supprimer commande
    public void delete(Long id) {
        commandeClientDao.delete(id);
    }
}