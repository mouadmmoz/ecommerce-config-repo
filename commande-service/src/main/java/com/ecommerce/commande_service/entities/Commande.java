package com.ecommerce.commande_service.entities;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Commande {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateCommande;

    private String statut; // "EN_ATTENTE_PAIEMENT", "PAYEE", "EXPEDIEE"

    private Long clientId; // ID du client

    private Double montantTotal;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<LigneCommande> lignes;
}