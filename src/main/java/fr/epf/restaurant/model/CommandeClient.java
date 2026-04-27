package fr.epf.restaurant.model;

import java.time.LocalDateTime;

public class CommandeClient {
    private Long id;
    private Long clientId;
    private LocalDateTime dateCommande;
    private String statut;
    private Client client;
    private String clientNom;
    private double total;



    public CommandeClient() {}
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getClientNom() {
        return clientNom;
    }
    public void setClientNom(String clientNom) {
        this.clientNom = clientNom;
    }
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
