package fr.epf.restaurant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.epf.restaurant.dto.CreerCommandeFournisseurRequest;
import fr.epf.restaurant.model.CommandeFournisseur;
import fr.epf.restaurant.service.CommandeFournisseurService;

@RestController
@RequestMapping("/api/commandes/fournisseur")
public class CommandeFournisseurController {

    private final CommandeFournisseurService service;

    public CommandeFournisseurController(CommandeFournisseurService service) {
        this.service = service;
    }

    // =========================
    // GET /api/commandes/fournisseur
    // =========================
    @GetMapping
    public List<CommandeFournisseur> getAll() {
        return service.findAll();
    }

    // =========================
    // GET /api/commandes/fournisseur/{id}
    // =========================
    @GetMapping("/{id}")
    public CommandeFournisseur getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // =========================
    // POST /api/commandes/fournisseur
    // =========================
    @PostMapping
    public void create(@RequestBody CreerCommandeFournisseurRequest request) {
        service.create(request.getFournisseurId());
    }

    // =========================
    // PUT /api/commandes/fournisseur/{id}/envoyer
    // =========================
    @PutMapping("/{id}/envoyer")
    public void envoyer(@PathVariable Long id) {
        service.envoyerCommande(id);
    }

    // =========================
    // PUT /api/commandes/fournisseur/{id}/recevoir
    // =========================
    @PutMapping("/{id}/recevoir")
    public void recevoir(@PathVariable Long id) {
        service.recevoirCommande(id);
    }

    // =========================
    // DELETE /api/commandes/fournisseur/{id}
    // =========================
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}