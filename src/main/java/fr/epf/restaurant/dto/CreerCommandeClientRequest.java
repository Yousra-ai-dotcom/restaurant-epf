package fr.epf.restaurant.dto;

import java.util.List;

public class CreerCommandeClientRequest {
    private Long clientId;
    private List<LigneCommandeClientRequest> lignes;

    public CreerCommandeClientRequest() {}

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<LigneCommandeClientRequest> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommandeClientRequest> lignes) {
        this.lignes = lignes;
    }
}
