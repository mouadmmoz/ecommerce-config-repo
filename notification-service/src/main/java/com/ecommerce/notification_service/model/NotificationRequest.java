package com.ecommerce.notification_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;

@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class NotificationRequest implements Serializable {
    private Long commandeId;
    private Double montant;
    private String emailClient;
    private String message;
}