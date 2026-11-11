package com.merchant.adapter.out.produit;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.merchant.model.ProduitInfo;
import com.merchant.application.port.out.ProduitPort;

import com.merchant.exception.ResourceNotFoundException;

// l'objectif de cette classe est de mettre dans la BD cleints les produits associés récupérés via leur ID 
@Component
public class ProduitAdapter implements ProduitPort {

    // on utilise l'url en dure; mais pas le choix !!! 
    @Value("${produit.service.url:http://localhost:8081}")
    private String urlProduit;

    private final RestTemplate restTemplate = new RestTemplate();

    // l'implement d'une interface avec Spiring passe par l'annotation "Override" pas le choix /// 
    @Override
    public List<ProduitInfo> findByIds(Collection<Integer> idProduits) {
        List<ProduitInfo> resultat = new ArrayList<>();
        if (idProduits.isEmpty()) {
            return resultat;
        }

        // {1, 4, 7} -> "1,4,7"
        String ids = "";
        for (Integer id : idProduits) {
            if (!ids.isEmpty()) {
                ids = ids + ",";
            }
            ids = ids + id;
        }

        try {
            // appel du service Produit : GET http://localhost:8081/api/produits?ids=1,4,7
            ProduitJson[] recus = restTemplate.getForObject(
                    urlProduit + "/api/produits?ids=" + ids, ProduitJson[].class);
            if (recus != null) {
                for (ProduitJson p : recus) {
                    resultat.add(new ProduitInfo(p.idProduit, p.nomProduit, p.designation, p.prixUnitaire));
                }
            }
        } catch (ResourceNotFoundException e) {
            // le service Produit ne répond pas (car arrêté ou défaillatn !! )
            throw new ResourceNotFoundException("Le service produit est indisponible");
        }
        return resultat;
    }

    // reçoit le JSON renvoyé par le service Produit (mêmes noms que dans ProduitResponse)
    public static class ProduitJson {
        public Integer idProduit;
        public String nomProduit;
        public String designation;
        public BigDecimal prixUnitaire;
    }
}