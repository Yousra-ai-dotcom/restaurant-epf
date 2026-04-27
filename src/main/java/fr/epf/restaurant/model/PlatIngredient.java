package fr.epf.restaurant.model;

public class PlatIngredient {

    private Long platId;
    private Long ingredientId;
    private Double quantiteRequise;

    public PlatIngredient() {}

    public Long getPlatId() {
        return platId;
    }

    public void setPlatId(Long platId) {
        this.platId = platId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Double getQuantiteRequise() {
        return quantiteRequise;
    }

    public void setQuantiteRequise(Double quantiteRequise) {
        this.quantiteRequise = quantiteRequise;
    }
}