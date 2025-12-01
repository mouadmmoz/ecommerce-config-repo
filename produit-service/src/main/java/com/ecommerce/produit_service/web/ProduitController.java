package com.ecommerce.produit_service.web;
import com.ecommerce.produit_service.entities.Produit;
import com.ecommerce.produit_service.repository.ProduitRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/produits") @CrossOrigin(origins = "*")
public class ProduitController {
    private final ProduitRepository repo;
    public ProduitController(ProduitRepository repo) { this.repo = repo; }

    // Lecture
    @GetMapping public List<Produit> getAll() { return repo.findAll(); }
    @GetMapping("/categorie/{id}") public List<Produit> getByType(@PathVariable Long id) { return repo.findByCategorieId(id); }

    @GetMapping("/{id}")
    public Produit getById(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Produit introuvable ID: " + id));
    }

    // Gestion Stock (Appelé par Commande-Service)
    @PutMapping("/reduire-stock/{id}")
    public void reduireStock(@PathVariable Long id, @RequestParam Integer quantite) {
        Produit p = repo.findById(id).orElseThrow(() -> new RuntimeException("Produit introuvable"));
        if(p.getQuantiteEnStock() < quantite) throw new RuntimeException("Stock insuffisant pour: " + p.getNom());
        p.setQuantiteEnStock(p.getQuantiteEnStock() - quantite);
        repo.save(p);
    }

    // Admin CRUD
    @PostMapping public Produit create(@RequestBody Produit p) { return repo.save(p); }

    @PutMapping("/{id}")
    public Produit update(@PathVariable Long id, @RequestBody Produit d) {
        Produit p = repo.findById(id).orElseThrow();
        p.setNom(d.getNom()); p.setDescription(d.getDescription()); p.setPrix(d.getPrix());
        p.setQuantiteEnStock(d.getQuantiteEnStock()); p.setImageUrl(d.getImageUrl()); p.setCategorie(d.getCategorie());
        return repo.save(p);
    }

    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { repo.deleteById(id); }
}