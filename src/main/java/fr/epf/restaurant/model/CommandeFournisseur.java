package fr.epf.restaurant.model;

import java.time.LocalDateTime;

public class CommandeFournisseur {
    private Long id;
    private Long fournisseurId;
    private LocalDateTime dateCommande;
    private String statut;

    public CommandeFournisseur() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getFournisseurId() {
        return fournisseurId;
    }
    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }
    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getStatut() {
        return statut;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }
}

