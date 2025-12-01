package com.ecommerce.commande_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data @AllArgsConstructor @NoArgsConstructor
public class NotificationRequest implements Serializable {
    private Long commandeId;
    private Double montant;
    private String emailClient;
    private String message;
}