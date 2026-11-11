package com.merchant.model;

import java.math.BigDecimal;

/** Ce que le module Client sait d'un produit (vision du client, indépendante de l'entité Produit du MVC). */
public class ProduitInfo {

    private final Integer idProduit;
    private final String nomProduit;
    private final String designation;
    private final BigDecimal prixUnitaire;

    public ProduitInfo(Integer idProduit, String nomProduit, String designation, BigDecimal prixUnitaire) {
        this.idProduit = idProduit;
        this.nomProduit = nomProduit;
        this.designation = designation;
        this.prixUnitaire = prixUnitaire;
    }

    public Integer getIdProduit() {
        return idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public String getDesignation() {
        return designation;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }
}
