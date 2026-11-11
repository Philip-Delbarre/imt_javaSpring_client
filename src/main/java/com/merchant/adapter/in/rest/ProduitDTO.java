package com.merchant.adapter.in.rest;

import java.math.BigDecimal;

/** Détail d'un produit renvoyé dans un client (ClientDTO.produits). */
public class ProduitDTO {

    private final Integer idProduit;
    private final String nomProduit;
    private final String designation;
    private final BigDecimal prixUnitaire;

    public ProduitDTO(Integer idProduit, String nomProduit, String designation, BigDecimal prixUnitaire) {
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
