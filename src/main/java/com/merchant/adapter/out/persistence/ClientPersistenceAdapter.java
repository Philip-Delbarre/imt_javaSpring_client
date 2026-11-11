package com.merchant.adapter.out.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.merchant.application.port.out.ClientRepositoryPort;
import com.merchant.model.Client;

/** Adapter sortant : implémente le port de stockage avec Spring Data JPA. */
@Repository
public class ClientPersistenceAdapter implements ClientRepositoryPort {

    private static final String SEPARATEUR = ",";

    private final SpringDataClientRepository repository;

    public ClientPersistenceAdapter(SpringDataClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        for (ClientJpaEntity entity : repository.findAll()) {
            clients.add(toDomain(entity));
        }
        return clients;
    }

    @Override
    public Optional<Client> findById(int idClient) {
        Optional<ClientJpaEntity> entity = repository.findById(idClient);
        if (entity.isPresent()) {
            return Optional.of(toDomain(entity.get()));
        }
        return Optional.empty();
    }

    // id null = création (la base génère l'id) ; id renseigné = modification
    @Override
    public Client save(Client client) {
        ClientJpaEntity entity = new ClientJpaEntity(client.getIdClient(), client.getNom(), client.getPrenom(),
                client.getEmail(), versTexte(client.getProduitIds()), client.getEtatPanier());
        return toDomain(repository.save(entity));
    }

    @Override
    public void deleteById(int idClient) {
        repository.deleteById(idClient);
    }

    private Client toDomain(ClientJpaEntity entity) {
        return new Client(entity.getIdClient(), entity.getNom(), entity.getPrenom(), entity.getEmail(),
                versListe(entity.getProduitIds()), entity.getEtatPanier());
    }

    // {1, 4, 7} -> "1,4,7"
    private String versTexte(Set<Integer> ids) {
        StringBuilder texte = new StringBuilder();
        for (Integer id : ids) {
            if (texte.length() > 0) {
                texte.append(SEPARATEUR);
            }
            texte.append(id);
        }
        return texte.toString();
    }

    // "1,4,7" -> [1, 4, 7]
    private List<Integer> versListe(String texte) {
        List<Integer> ids = new ArrayList<>();
        if (texte == null || texte.isBlank()) {
            return ids;
        }
        for (String morceau : texte.split(SEPARATEUR)) {
            String propre = morceau.trim();
            if (!propre.isEmpty()) {
                ids.add(Integer.valueOf(propre));
            }
        }
        return ids;
    }
}
