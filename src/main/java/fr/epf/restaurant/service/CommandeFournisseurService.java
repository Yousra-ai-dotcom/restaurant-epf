package fr.epf.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.epf.restaurant.dao.CommandeFournisseurDao;
import fr.epf.restaurant.model.CommandeFournisseur;

@Service
public class CommandeFournisseurService {

    private final CommandeFournisseurDao commandeFournisseurDao;
    private final StockService stockService;

    public CommandeFournisseurService(CommandeFournisseurDao commandeFournisseurDao,
                                       StockService stockService) {
        this.commandeFournisseurDao = commandeFournisseurDao;
        this.stockService = stockService;
    }

    public List<CommandeFournisseur> findAll() {
        return commandeFournisseurDao.findAll();
    }

    public CommandeFournisseur findById(Long id) {
        return commandeFournisseurDao.findById(id);
    }

    // Créer commande fournisseur
    public void create(Long fournisseurId) {
        commandeFournisseurDao.save(fournisseurId);
    }

    // Envoyer commande
    public void envoyerCommande(Long id) {
        commandeFournisseurDao.updateStatut(id, "ENVOYEE");
    }

    // Recevoir commande fournisseur
    public void recevoirCommande(Long id) {

        commandeFournisseurDao.updateStatut(id, "RECUE");

        // normalement on ajouterait le stock ici
    }

    // Supprimer
    public void delete(Long id) {
        commandeFournisseurDao.delete(id);
    }
}