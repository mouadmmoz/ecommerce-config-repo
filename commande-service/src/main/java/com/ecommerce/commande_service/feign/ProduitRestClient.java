package com.ecommerce.commande_service.feign;


import com.ecommerce.commande_service.model.ProduitBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

// "produit-service" doit correspondre exactement au nom dans Eureka
@FeignClient(name = "produit_service")
public interface ProduitRestClient {

    @GetMapping("/api/produits/{id}")
    ProduitBean getProduitById(@PathVariable("id") Long id);

    // Appelle la méthode pour décrémenter le stock dans le service produit
    @PutMapping("/api/produits/reduire-stock/{id}")
    void reduireStock(@PathVariable("id") Long id, @RequestParam("quantite") Integer quantite);
}