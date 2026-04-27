package fr.epf.restaurant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.epf.restaurant.dao.PlatDao;
import fr.epf.restaurant.model.Plat;

@RestController
@RequestMapping("/api/plats")
public class PlatController {

    private final PlatDao platDao;

    public PlatController(PlatDao platDao) {
        this.platDao = platDao;
    }

    // =========================
    // GET /api/plats
    // =========================
    @GetMapping
    public List<Plat> getAllPlats() {
        return platDao.findAll();
    }

    // =========================
    // POST /api/plats
    // =========================
    @PostMapping
    public void createPlat(@RequestBody Plat plat) {
        platDao.save(plat);
    }
}