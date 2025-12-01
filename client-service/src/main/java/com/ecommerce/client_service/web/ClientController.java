package com.ecommerce.client_service.web;
import com.ecommerce.client_service.entities.Client;
import com.ecommerce.client_service.repository.ClientRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/clients") @CrossOrigin(origins = "*")
public class ClientController {
    private final ClientRepository repo;
    public ClientController(ClientRepository repo) { this.repo = repo; }

    @GetMapping public List<Client> getAll() { return repo.findAll(); }

    @GetMapping("/search")
    public Client getByEmail(@RequestParam String email) {
        return repo.findByEmail(email).orElseThrow(() -> new RuntimeException("Client non trouvé: " + email));
    }

    @PostMapping("/sync")
    public Client sync(@RequestBody Client c) {
        return repo.findByEmail(c.getEmail()).orElseGet(() -> repo.save(c));
    }
}