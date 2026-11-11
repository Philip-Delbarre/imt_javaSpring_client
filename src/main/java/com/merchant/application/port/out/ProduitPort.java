package com.merchant.application.port.out;

import java.util.Collection;
import java.util.List;

import com.merchant.model.ProduitInfo;

/**
 * Port de sortie : ce dont le module Client a besoin à propos des produits.
 * Aujourd'hui l'adapter appelle ProduitService directement ; demain il pourrait faire un appel HTTP.
 */
public interface ProduitPort {

    /** Renvoie uniquement les produits qui existent parmi les ids demandés. */
    List<ProduitInfo> findByIds(Collection<Integer> idProduits);
}
