package fr.epf.restaurant.dto;

import java.util.List;

import fr.epf.restaurant.model.CommandeClient;

public class PreparationResultDto {
    private CommandeClient commande;
    private List<AlerteStockDto> alertes;

    public PreparationResultDto(CommandeClient commande, List<AlerteStockDto> alertes) {
        this.commande = commande;
        this.alertes = alertes;
    }

    public CommandeClient getCommande() {
        return commande;
    }

    public void setCommande(CommandeClient commande) {
        this.commande = commande;
    }

    public List<AlerteStockDto> getAlertes() {
        return alertes;
    }

    public void setAlertes(List<AlerteStockDto> alertes) {
        this.alertes = alertes;
    }

}
