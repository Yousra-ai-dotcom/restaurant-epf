package fr.epf.restaurant.model;

public class Ingredient {
    private Long id;
    private String nom;
    private String unite;
    private Double stockActuel;
    private Double seuilAlerte;

    public Ingredient() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom (String nom) {
        this.nom = nom;
    }

    public String getUnite() {
        return unite;
    }
    public void setUnite(String unite) {
        this.unite = unite;
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
}
