package com.ecommerce.commande_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        // 1. Use the default constructor (the constructor taking ObjectMapper is deprecated)
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();

        // 2. Set the ObjectMapper explicitly via the setter
        converter.setObjectMapper(objectMapper);

        // 3. Security Configuration (Crucial for Spring AMQP 4.0+)
        // You must specify which packages are trusted to be deserialized.
        // Use "*" for all, or specify your package "com.ecommerce.*"
        converter.setTrustedPackages("*");

        return converter;
    }
}