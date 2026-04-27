package fr.epf.restaurant.dto;

import java.math.BigDecimal;

public class RecommandationDto {
    private Long fournisseurId;
    private String fournisseurNom;
    private BigDecimal prixUnitaire;
    private Double quantiteRecommandee;

    public RecommandationDto() {}

    public Long getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public String getFournisseurNom() {
        return fournisseurNom;
    }

    public void setFournisseurNom(String fournisseurNom) {
        this.fournisseurNom = fournisseurNom;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Double getQuantiteRecommandee() {
        return quantiteRecommandee;
    }

    public void setQuantiteRecommandee(Double quantiteRecommandee) {
        this.quantiteRecommandee = quantiteRecommandee;
    }

}
