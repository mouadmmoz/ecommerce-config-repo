package com.ecommerce.paiement_service.repository;

import com.ecommerce.paiement_service.entities.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    // Méthodes standard JPA (save, findById, findAll...)
}