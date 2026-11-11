package com.merchant.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

// Spring Data génère l'implémentation au démarrage (findAll, findById, save, deleteById...)
public interface SpringDataClientRepository extends JpaRepository<ClientJpaEntity, Integer> {
}
