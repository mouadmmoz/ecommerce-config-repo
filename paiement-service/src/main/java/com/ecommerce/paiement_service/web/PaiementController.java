package com.ecommerce.paiement_service.web;

import com.ecommerce.paiement_service.entities.Paiement;
import com.ecommerce.paiement_service.repository.PaiementRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/paiements")
@CrossOrigin(origins = "*")
public class PaiementController {

    private final PaiementRepository repository;

    public PaiementController(PaiementRepository repository) {
        this.repository = repository;
    }

    // ÉTAPE 1 : Le client clique sur "Payer"
    // On génère une URL PayPal (Sandbox) et on la renvoie au Front Angular
    @PostMapping("/paypal/init")
    public Map<String, String> initPaypal(@RequestBody Paiement p) {
        // Simulation d'une URL PayPal standard pour un vendeur test
        // Dans Angular : window.location.href = response.redirectUrl
        String url = "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_xclick&business=vendeur@test.com&currency_code=EUR&amount=" + p.getMontant();

        return Map.of("redirectUrl", url);
    }

    // ÉTAPE 2 : Confirmation du paiement (Appelé au retour de PayPal ou par le système)
    @PostMapping("/confirmer")
    public Paiement confirmer(@RequestBody Paiement p) {
        p.setDate(LocalDate.now());
        p.setStatut("VALIDE");
        p.setMode("PAYPAL");
        return repository.save(p);
    }

    // (Optionnel) Consulter un paiement par ID
    @GetMapping("/{id}")
    public Paiement getPaiement(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Paiement introuvable"));
    }
}