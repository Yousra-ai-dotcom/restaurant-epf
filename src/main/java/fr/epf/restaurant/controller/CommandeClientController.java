package fr.epf.restaurant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.epf.restaurant.dto.CreerCommandeClientRequest;
import fr.epf.restaurant.dto.PreparationResultDto;
import fr.epf.restaurant.model.CommandeClient;
import fr.epf.restaurant.service.CommandeClientService;

@RestController
@RequestMapping("/api/commandes/client")
public class CommandeClientController {

    private final CommandeClientService commandeClientService;

    public CommandeClientController(CommandeClientService commandeClientService) {
        this.commandeClientService = commandeClientService;
    }

    // GET /api/commandes/client
    @GetMapping
    public List<CommandeClient> getAll(
        @RequestParam(required = false) String statut) {
            if (statut == null) {
                return commandeClientService.findAll();
            }
            return commandeClientService.findByStatut(statut);
    }

    // GET /api/commandes/client/{id}
    @GetMapping("/{id}")
    public CommandeClient getById(@PathVariable Long id) {
        return commandeClientService.findById(id);
    }

    // POST /api/commandes/client
    @PostMapping
    public void create(@RequestBody CreerCommandeClientRequest request) {
        commandeClientService.create(request);
    }

    // PUT /api/commandes/client/{id}/preparer
    @PutMapping("/{id}/preparer")
    public PreparationResultDto preparer(@PathVariable Long id) {
        return commandeClientService.preparerCommande(id);
    }

    // PUT /api/commandes/client/{id}/servir
    @PutMapping("/{id}/servir")
    public void servir(@PathVariable Long id) {
        commandeClientService.servirCommande(id);
    }

    // DELETE /api/commandes/client/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        commandeClientService.delete(id);
    }
}