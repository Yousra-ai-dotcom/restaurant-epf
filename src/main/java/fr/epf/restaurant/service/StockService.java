package fr.epf.restaurant.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fr.epf.restaurant.dao.IngredientDao;
import fr.epf.restaurant.dto.AlerteStockDto;
import fr.epf.restaurant.exception.StockInsuffisantException;
import fr.epf.restaurant.model.Ingredient;

@Service
public class StockService {

    private final IngredientDao ingredientDao;

    public StockService(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    // Consommer le stock
    public void consommerStock(Long ingredientId, Double quantite) {

        Ingredient ingredient = ingredientDao.findById(ingredientId);

        if (ingredient.getStockActuel() < quantite) {
            throw new StockInsuffisantException("Stock insuffisant pour " + ingredient.getNom());
        }

        Double nouveauStock = ingredient.getStockActuel() - quantite;

        ingredientDao.updateStock(ingredientId, nouveauStock);
    }

    // Ajouter du stock (commande fournisseur reçue)
    public void ajouterStock(Long ingredientId, Double quantite) {

        Ingredient ingredient = ingredientDao.findById(ingredientId);

        Double nouveauStock = ingredient.getStockActuel() + quantite;

        ingredientDao.updateStock(ingredientId, nouveauStock);
    }

    // Vérifier si stock suffisant
    public boolean stockSuffisant(Long ingredientId, Double quantite) {

        Ingredient ingredient = ingredientDao.findById(ingredientId);

        return ingredient.getStockActuel() >= quantite;
    }

    // Alerte stock
    public List<AlerteStockDto> getAlertesStock() {

        List<Ingredient> ingredients = ingredientDao.findSousAlerte();

        return ingredients.stream().map(i -> {

            AlerteStockDto dto = new AlerteStockDto();

            dto.setIngredientId(i.getId());
            dto.setNom(i.getNom());
            dto.setStockActuel(i.getStockActuel());
            dto.setSeuilAlerte(i.getSeuilAlerte());

            Double quantiteACommander = i.getSeuilAlerte() - i.getStockActuel();

            dto.setQuantiteACommander(quantiteACommander);

            return dto;

        }).collect(Collectors.toList());
    }
}