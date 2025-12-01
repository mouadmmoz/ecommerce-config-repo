package com.ecommerce.commande_service.model;

import lombok.Data;

@Data
public class ProduitBean {
    private Long id;
    private String nom;
    private Double prix;
    private Integer quantiteEnStock;
}