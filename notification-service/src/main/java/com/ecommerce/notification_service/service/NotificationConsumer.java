package com.ecommerce.notification_service.service;


import com.ecommerce.notification_service.model.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j // Génère automatiquement le logger 'log'
public class NotificationConsumer {

    // Écoute la file définie dans RabbitConfig
    @RabbitListener(queues = "notificationQueue")
    public void consommerMessage(NotificationRequest request) {
        log.info("📨 [RABBITMQ] Nouvelle notification reçue !");
        log.info("------------------------------------------------------");
        log.info("Commande N° : {}", request.getCommandeId());
        log.info("Client      : {}", request.getEmailClient());
        log.info("Montant     : {} €", request.getMontant());
        log.info("Message     : {}", request.getMessage());
        log.info("------------------------------------------------------");

        // Simulation d'envoi d'email
        try {
            Thread.sleep(1000); // Simule le temps d'envoi SMTP
            log.info("✅ Email envoyé avec succès à {}", request.getEmailClient());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}