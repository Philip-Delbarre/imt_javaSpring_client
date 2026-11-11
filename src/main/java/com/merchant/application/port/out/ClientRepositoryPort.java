package com.merchant.application.port.out;

import java.util.List;
import java.util.Optional;

import com.merchant.model.Client;

/** Port de sortie : ce dont le module Client a besoin pour stocker ses clients. */
public interface ClientRepositoryPort {

    List<Client> findAll();

    Optional<Client> findById(int idClient);

    Client save(Client client);

    void deleteById(int idClient);
}
