package com.merchant.adapter.in.rest;

import org.springframework.stereotype.Component;

import com.merchant.model.Client;
import com.merchant.model.ProduitInfo;

/** Convertit les objets REST (JSON) en objets du domaine, et inversement. */
@Component
public class ClientRestMapper {

    public Client toDomain(CreationClientDTO dto) {
        // idClient = null : il sera généré par la base à la création
        return new Client(null, dto.getNom(), dto.getPrenom(), dto.getEmail(), dto.getProduits(), dto.getEtatPanier());
    }

    // les produits sont ajoutés dans un deuxième temps, une fois leur détail récupéré
    public ClientDTO toDto(Client client) {
        return new ClientDTO(client.getIdClient(), client.getNom(), client.getPrenom(), client.getEmail(), client.getEtatPanier());
    }

    public ProduitDTO toProduitDto(ProduitInfo produit) {
        return new ProduitDTO(produit.getIdProduit(), produit.getNomProduit(), produit.getDesignation(),
                produit.getPrixUnitaire());
    }
}
