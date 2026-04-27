package fr.epf.restaurant.model;

public class LigneCommandeClient {
    private Long id;
    private Long commandeClientId;
    private Long platId;
    private Integer quantite;

    public LigneCommandeClient() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getCommandeClientId() {
        return commandeClientId;
    }
    public void setCommandeClientId(Long commandeClientId) {
        this.commandeClientId = commandeClientId;
    }

    public Long getPlatId() {
        return platId;
    }
    public void setPlatId(Long platId) {
        this.platId = platId;
    }

    public Integer getQuantite() {
        return quantite;
    }
    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

}

