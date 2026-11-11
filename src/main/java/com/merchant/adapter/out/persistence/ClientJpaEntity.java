package com.merchant.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Représentation JPA d'un client (propre à l'adapter de persistance, le domaine ne la voit jamais).
 * Table "clients" : aucune table de liaison, aucune clé étrangère vers "produits".
 */
@Entity
@Table(name = "clients")
public class ClientJpaEntity {

    // IDENTITY : la base génère l'id (1, 2, 3...) à chaque insertion
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idClient;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String email;

    @Column(name = "Etat du panier")
    private String etatPanier;

    // ids des produits séparés par des virgules, ex : "1,4,7" (vide = aucun produit)
    @Column(name = "produit_ids", length = 4000)
    private String produitIds;



    protected ClientJpaEntity() {}
        // requis par JPA


    public ClientJpaEntity(Integer idClient, String nom, String prenom, String email, String produitIds, String etatPanier) {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.produitIds = produitIds;
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

    public String getProduitIds() {
        return produitIds;
    }

    public String getEtatPanier() {
        return etatPanier;
    }


}
