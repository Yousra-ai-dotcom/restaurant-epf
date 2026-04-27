package fr.epf.restaurant.dto;

public class AlerteStockDto {
    private Long ingredientId;
    private String nom;
    private Double stockActuel;
    private Double seuilAlerte;
    private Double quantiteACommander;

    public AlerteStockDto() {}

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getStockActuel() {
        return stockActuel;
    }

    public void setStockActuel(Double stockActuel) {
        this.stockActuel = stockActuel;
    }

    public Double getSeuilAlerte() {
        return seuilAlerte;
    }

    public void setSeuilAlerte(Double seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }

    public Double getQuantiteACommander() {
        return quantiteACommander;
    }

    public void setQuantiteACommander(Double quantiteACommander) {
        this.quantiteACommander = quantiteACommander;
    }

}
