package com.ecommerce.notification_service.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // 1. Déclaration de la file d'attente
    // Si elle n'existe pas dans RabbitMQ, elle sera créée automatiquement au démarrage
    @Bean
    public Queue notificationQueue() {
        return new Queue("notificationQueue", false);
    }

    // 2. Convertisseur JSON pour lire les objets proprement
    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}