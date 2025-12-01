package com.ecommerce.paiement_service.entities;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Paiement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long commandeId; // ID de la commande payée

    private Double montant;

    private String mode; // Ex: "PAYPAL"

    private String statut; // Ex: "INITIE", "VALIDE", "ECHEC"

    private LocalDate date;
}