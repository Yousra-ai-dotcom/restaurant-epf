package fr.epf.restaurant.model;

import java.math.BigDecimal;

public class FournisseurIngredient {
    private Long fournisseurId;
    private Long  ingredientId;
    private BigDecimal prixUnitaire;

    public FournisseurIngredient() {}

    public Long getFournisseurId() {
        return fournisseurId;
    }
    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }
    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }
    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }


}
