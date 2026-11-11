package com.merchant.adapter.in.rest;

import java.util.ArrayList;
import java.util.List;

/** Corps JSON renvoyé pour un client : "produits" contient le détail des produits qui existent encore. */
public class ClientDTO {

    private final Integer idClient;
    private final String nom;
    private final String prenom;
    private final String email;
    private final String etatPanier;
    // on attache une liste de plusieurs produits à un client
    private final List<ProduitDTO> produits = new ArrayList<>();

    public ClientDTO(Integer idClient, String nom, String prenom, String email, String etatPanier) {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.etatPanier = etatPanier;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public List<ProduitDTO> getProduits() {
        return produits;
    }

    public String getEtatPanier() {
        return etatPanier;
    }
}
