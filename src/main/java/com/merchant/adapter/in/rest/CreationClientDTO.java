package com.merchant.adapter.in.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.merchant.exception.BadRequestException;

/**
 * Corps JSON reçu pour créer ou modifier un client.
 * Pas d'idClient : il est généré par la base (un idClient envoyé est ignoré).
 * "produits" (optionnel) : liste d'identifiants de produits, ex : [1, 4].
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreationClientDTO {

    private String nom;
    private String prenom;
    private String email;
    private String etatPanier;
    private List<Integer> produits = new ArrayList<>();

    // le constructeur vide + les setters sont requis : c'est ce qui permet à Jackson de remplir l'objet depuis le JSON
    public CreationClientDTO() {
    }

    /** Contrôle les données reçues ; lève une BadRequestException (HTTP 400) avec le détail des champs en erreur. */
    public void verifier() {
        Map<String, String> erreurs = new TreeMap<>();
        if (estVide(nom)) {
            erreurs.put("nom", "le nom est obligatoire");
        }
        if (estVide(prenom)) {
            erreurs.put("prenom", "le prénom est obligatoire");
        }
        if (estVide(email)) {
            erreurs.put("email", "l'email est obligatoire");
        } else if (!email.trim().matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
            erreurs.put("email", "email invalide");
        }
        if (!erreurs.isEmpty()) {
            throw new BadRequestException("Données invalides", erreurs);
        }
    }

    private boolean estVide(String texte) {
        return texte == null || texte.trim().isEmpty();
    }

    // dans un getter, "this." est facultatif : return nom; équivaut à return this.nom;
    // dans un setter, il est nécessaire car le paramètre porte le même nom que l'attribut
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getProduits() {
        return produits;
    }

    // attention : c'est une affectation, pas un enrichissement de liste !
    public void setProduits(List<Integer> produits) {
        this.produits = produits;
    }

    public String getEtatPanier() {
        return etatPanier;
    }

    public void setEtatPanier(String etatPanier) {
        this.etatPanier = etatPanier;
    }

}
