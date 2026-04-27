package fr.epf.restaurant.dto;

import java.math.BigDecimal;

public class LigneCommandeFournisseurRequest {
    private Long ingredientId;
    private Double quantite;
    private BigDecimal prixUnitaire;

    public LigneCommandeFournisseurRequest() {}

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

}
