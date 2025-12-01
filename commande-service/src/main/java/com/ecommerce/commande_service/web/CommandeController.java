package com.ecommerce.commande_service.web;
import com.ecommerce.commande_service.entities.Commande;
import com.ecommerce.commande_service.entities.LigneCommande;
import com.ecommerce.commande_service.feign.ProduitRestClient;
import com.ecommerce.commande_service.model.NotificationRequest;
import com.ecommerce.commande_service.model.ProduitBean;
import com.ecommerce.commande_service.repository.CommandeRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@CrossOrigin(origins = "*")
public class CommandeController {

    private final CommandeRepository repository;
    private final ProduitRestClient produitClient;
    private final RabbitTemplate rabbitTemplate;

    public CommandeController(CommandeRepository repository,
                              ProduitRestClient produitClient,
                              RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.produitClient = produitClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    // ADMIN : Voir toutes les commandes
    @GetMapping
    public List<Commande> getAllCommandes() {
        return repository.findAll();
    }

    // CLIENT : Voir mes commandes
    @GetMapping("/client/{clientId}")
    public List<Commande> getCommandesByClient(@PathVariable Long clientId) {
        return repository.findByClientId(clientId);
    }

    // CLIENT : Passer une commande
    @PostMapping
    public Commande passerCommande(@RequestBody Commande commandeRequest) {
        Commande nouvelleCommande = new Commande();
        nouvelleCommande.setClientId(commandeRequest.getClientId());
        nouvelleCommande.setDateCommande(LocalDate.now());
        nouvelleCommande.setStatut("EN_ATTENTE_PAIEMENT");

        double montantTotal = 0.0;

        // Traitement des lignes
        if (commandeRequest.getLignes() != null) {
            for (LigneCommande ligne : commandeRequest.getLignes()) {
                // 1. Vérifier et réduire le stock via Feign (Microservice Produit)
                // Si le stock est insuffisant, une exception sera levée par ProduitService et propagée ici
                produitClient.reduireStock(ligne.getProduitId(), ligne.getQuantite());

                // 2. Récupérer les infos à jour du produit (Prix)
                ProduitBean produit = produitClient.getProduitById(ligne.getProduitId());

                // 3. Construire la ligne
                ligne.setPrixUnitaire(produit.getPrix());
                ligne.setCommande(nouvelleCommande);

                montantTotal += (produit.getPrix() * ligne.getQuantite());
            }
            nouvelleCommande.setLignes(commandeRequest.getLignes());
        }

        nouvelleCommande.setMontantTotal(montantTotal);

        // 4. Sauvegarde en BDD
        Commande savedCommande = repository.save(nouvelleCommande);

        // 5. Envoi Notification Asynchrone (RabbitMQ)
        try {
            NotificationRequest notif = new NotificationRequest(
                    savedCommande.getId(),
                    savedCommande.getMontantTotal(),
                    "client@gmail.com", // Dans un cas réel, on récupérerait l'email via le service Client
                    "Votre commande a été enregistrée avec succès."
            );

            // Envoi dans la file "notificationQueue"
            rabbitTemplate.convertAndSend("notificationQueue", notif);

        } catch (Exception e) {
            System.err.println("Erreur envoi notification (Non bloquant) : " + e.getMessage());
        }

        return savedCommande;
    }
}