package com.merchant.adapter.in.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.merchant.application.port.in.ClientServicePort;
import com.merchant.exception.BadRequestException;
import com.merchant.model.Client;
import com.merchant.model.ProduitInfo;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

/**
 * Adapter entrant : expose le port d'entrée ClientServicePort en REST sur /api/clients.
 * Il n'appelle que le port d'entrée (jamais directement les ports de sortie ni la base).
 */
@Component
@Path("client")
@Produces(MediaType.APPLICATION_JSON)
public class ClientPresentation {

    private final ClientServicePort clientServicePort;
    private final ClientRestMapper mapper;

    public ClientPresentation(ClientServicePort clientServicePort, ClientRestMapper mapper) {
        this.clientServicePort = clientServicePort;
        this.mapper = mapper;
    }

    @GET
    public List<ClientDTO> getClients() {
        List<ClientDTO> resultat = new ArrayList<>();
        for (Client client : clientServicePort.consulterClients()) {
            resultat.add(versDto(client));
        }
        return resultat;
    }

    @GET
    @Path("{id}")
    public ClientDTO getClient(@PathParam("id") int id) {
        return versDto(clientServicePort.consulterClient(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response creationClient(CreationClientDTO clientDTO,@Context UriInfo uriInfo) {

    verifierCorps(clientDTO);

    Client client = mapper.toDomain(clientDTO);

    Client cree = clientServicePort.creerClient(client);

    URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(cree.getIdClient())).build();

    ResponseRequestDTO resultat = new ResponseRequestDTO("Client " + cree.getIdClient() + " créé",versDto(cree));

    return Response.created(location).entity(resultat).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifierClient(@PathParam("id") int id, CreationClientDTO clientDTO, @Context UriInfo uriInfo) {
        verifierCorps(clientDTO);
        //return versDto(clientServicePort.modifierClient(id, mapper.toDomain(clientDTO)));
        Client modifie = clientServicePort.modifierClient(id, mapper.toDomain(clientDTO));
        URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(modifie.getIdClient())).build();
        ResponseRequestDTO resultat = new ResponseRequestDTO("Client " + modifie.getIdClient() + " modifié",versDto(modifie));
    return Response.created(location).entity(resultat).type(MediaType.APPLICATION_JSON).build();

    }

    @DELETE
    @Path("{id}")
    public Response supprimerClient(@PathParam("id") int id) {
        clientServicePort.supprimerClient(id);
        return Response.ok("Client " + id + " supprimé").type(MediaType.TEXT_PLAIN).build();
    }

    // --- liens client <-> produits (par id uniquement) ---

    @POST
    @Path("{id}/produits/{produitId}")
    public ClientDTO ajouterProduit(@PathParam("id") int id, @PathParam("produitId") int produitId) {
        return versDto(clientServicePort.ajouterProduit(id, produitId));
    }

    @DELETE
    @Path("{id}/produits/{produitId}")
    public ClientDTO retirerProduit(@PathParam("id") int id, @PathParam("produitId") int produitId) {
        return versDto(clientServicePort.retirerProduit(id, produitId));
    }

    // transforme un Client en ClientDTO et y ajoute le détail de ses produits (ceux qui existent encore)
    private ClientDTO versDto(Client client) {
        ClientDTO dto = mapper.toDto(client);
        for (ProduitInfo produit : clientServicePort.consulterProduits(client)) {
            dto.getProduits().add(mapper.toProduitDto(produit));
        }
        return dto;
    }

    private void verifierCorps(CreationClientDTO clientDTO) {
        if (clientDTO == null) {
            throw new BadRequestException("Corps de requête manquant");
        }
        // contrôle des champs obligatoires (nom, prénom, email)
        clientDTO.verifier();
    }
}
