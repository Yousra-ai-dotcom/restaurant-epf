package fr.epf.restaurant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.epf.restaurant.dao.ClientDao;
import fr.epf.restaurant.model.Client;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientDao clientDao;

    public ClientController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    // =========================
    // GET /api/clients
    // =========================
    @GetMapping
    public List<Client> getAllClients() {
        return clientDao.findAll();
    }

    // =========================
    // POST /api/clients
    // =========================
    @PostMapping
    public void createClient(@RequestBody Client client) {
        clientDao.save(client);
    }
}