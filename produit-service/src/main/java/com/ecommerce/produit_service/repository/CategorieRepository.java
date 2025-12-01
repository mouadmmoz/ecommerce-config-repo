package com.ecommerce.produit_service.repository;
import com.ecommerce.produit_service.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {}