package com.ecommerce.produit_service.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Produit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @Column(length = 2000)
    private String description;
    private Double prix;
    private Integer quantiteEnStock;
    private String imageUrl;
    @ManyToOne @JoinColumn(name = "categorie_id")
    private Categorie categorie;
}