package com.merchant.application.port.in;

import java.util.List;

import com.merchant.model.Client;
import com.merchant.model.ProduitInfo;

/** Port d'entrée : ce que l'extérieur (ici l'API REST) peut demander au module Client. */
public interface ClientServicePort {

    List<Client> consulterClients();

    Client consulterClient(int idClient);

    Client creerClient(Client client);

    /** Remplace nom, prénom, email et produits du client (PUT = remplacement complet). */
    Client modifierClient(int idClient, Client donnees);

    void supprimerClient(int idClient);

    Client ajouterProduit(int idClient, int idProduit);

    Client retirerProduit(int idClient, int idProduit);

    /** Détail des produits du client qui existent encore (les ids orphelins sont ignorés). */
    List<ProduitInfo> consulterProduits(Client client);
}
