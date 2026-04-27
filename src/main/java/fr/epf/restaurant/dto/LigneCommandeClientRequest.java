package fr.epf.restaurant.dto;

public class LigneCommandeClientRequest {

    private Long platId;
    private Integer quantite;

    public LigneCommandeClientRequest() {}

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
