package fr.epf.restaurant.dto;

import java.util.List;

public class CreerCommandeFournisseurRequest {
    private Long fournisseurId;
    private List<LigneCommandeFournisseurRequest> lignes;

    public CreerCommandeFournisseurRequest() {}

    public Long getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public List<LigneCommandeFournisseurRequest> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommandeFournisseurRequest> lignes) {
        this.lignes = lignes;
    }

}
