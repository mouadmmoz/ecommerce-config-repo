package com.ecommerce.produit_service.web;
import com.ecommerce.produit_service.entities.Categorie;
import com.ecommerce.produit_service.repository.CategorieRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/categories") @CrossOrigin(origins = "*")
public class CategorieController {
    private final CategorieRepository repo;
    public CategorieController(CategorieRepository repo) { this.repo = repo; }
    @GetMapping public List<Categorie> getAll() { return repo.findAll(); }
    @PostMapping public Categorie create(@RequestBody Categorie c) { return repo.save(c); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { repo.deleteById(id); }
}