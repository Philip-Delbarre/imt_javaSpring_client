package com.merchant.application.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.merchant.application.port.in.ClientServicePort;
import com.merchant.application.port.out.ClientRepositoryPort;
import com.merchant.application.port.out.ProduitPort;
import com.merchant.exception.BadRequestException;
import com.merchant.exception.ResourceNotFoundException;
import com.merchant.model.Client;
import com.merchant.model.ProduitInfo;

import jakarta.transaction.Transactional;

/** Logique métier des clients. Elle ne dépend que des interfaces (ports), jamais de JPA ni de REST. */
@Component
@Transactional
public class ClientService implements ClientServicePort {

    private final ClientRepositoryPort clientRepositoryPort;
    private final ProduitPort produitPort;

    public ClientService(ClientRepositoryPort clientRepositoryPort, ProduitPort produitPort) {
        this.clientRepositoryPort = clientRepositoryPort;
        this.produitPort = produitPort;
    }

    @Override
    public List<Client> consulterClients() {
        return clientRepositoryPort.findAll();
    }

    @Override
    public Client consulterClient(int idClient) {
        return clientRepositoryPort.findById(idClient)
                .orElseThrow(() -> new ResourceNotFoundException("Client " + idClient + " introuvable"));
    }

    @Override
    public Client creerClient(Client client) {
        // l'id est toujours généré par la base : on s'assure qu'aucun id n'est imposé
        client.setIdClient(null);
        verifierProduits(client.getProduitIds());
        return clientRepositoryPort.save(client);
    }

    @Override
    public Client modifierClient(int idClient, Client donnees) {
        Client existant = consulterClient(idClient);
        existant.setNom(donnees.getNom());
        existant.setPrenom(donnees.getPrenom());
        existant.setEmail(donnees.getEmail());
        existant.setEtatPanier(donnees.getEtatPanier());
        //existant.remplacerProduits(donnees.getProduitIds());
        //verifierProduits(existant.getProduitIds());
        return clientRepositoryPort.save(existant);
    }

    @Override
    public void supprimerClient(int idClient) {
        // lève une 404 si le client n'existe pas
        consulterClient(idClient);
        clientRepositoryPort.deleteById(idClient);
    }

    @Override
    public Client ajouterProduit(int idClient, int idProduit) {
    Client client = consulterClient(idClient);
        String statutString []= {"Validé", "En Cours"};
        if (statutString[0].equals(client.getEtatPanier())) {
            throw new BadRequestException( "Impossible d'ajouter un produit : Le panier est validé");
        }
        else if (produitPort.findByIds(List.of(idProduit)).isEmpty()) {
            throw new ResourceNotFoundException("le Produit N°: " + idProduit + " introuvable");
            }
        client.addProduit(idProduit);
        if (client.getEtatPanier() == null ||  client.getEtatPanier() == "") {
            client.setEtatPanier(statutString[1]);
            }

            return clientRepositoryPort.save(client);
    }

    @Override
    public Client retirerProduit(int idClient, int idProduit) {
        Client client = consulterClient(idClient);
        client.removeProduit(idProduit);
        return clientRepositoryPort.save(client);
    }

    @Override
    public List<ProduitInfo> consulterProduits(Client client) {
        if (client.getProduitIds().isEmpty()) {
            return new ArrayList<>();
        }
        // seuls les produits encore existants sont renvoyés : les ids orphelins sont tolérés
        return produitPort.findByIds(client.getProduitIds());
    }

    // Vérifie que tous les ids de produits existent au moment de l'enregistrement
    private void verifierProduits(Set<Integer> idProduits) {
        if (idProduits.isEmpty()) {
            return;
        }
        Set<Integer> trouves = new HashSet<>();
        for (ProduitInfo produit : produitPort.findByIds(idProduits)) {
            trouves.add(produit.getIdProduit());
        }
        List<Integer> manquants = new ArrayList<>();
        for (Integer id : idProduits) {
            if (!trouves.contains(id)) {
                manquants.add(id);
            }
        }
       if (!manquants.isEmpty()) {
            throw new BadRequestException("Produit(s) inexistant(s) : " + manquants);
        }
    }
}
