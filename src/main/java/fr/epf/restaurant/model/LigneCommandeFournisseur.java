package fr.epf.restaurant.model;

import java.math.BigDecimal;

public class LigneCommandeFournisseur {

    private Long id;
    private Long commandeFournisseurId;
    private Long ingredientId;
    private Double quantiteCommandee;
    private BigDecimal prixUnitaire;

    public LigneCommandeFournisseur() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommandeFournisseurId() {
        return commandeFournisseurId;
    }
    public void setCommandeFournisseurId(Long commandeFournisseurId) {
        this.commandeFournisseurId = commandeFournisseurId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }
    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Double getQuantiteCommandee() {
        return quantiteCommandee;
    }
    public void setQuantiteCommandee(Double quantiteCommandee) {
        this.quantiteCommandee = quantiteCommandee;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }
    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
}

