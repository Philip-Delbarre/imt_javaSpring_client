package com.merchant;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.merchant.adapter.in.rest.ClientPresentation;

import com.merchant.exception.BadRequestExceptionMapper;
import com.merchant.exception.ResourceNotFoundExceptionMapper;

import jakarta.ws.rs.ApplicationPath;

// Configure UNIQUEMENT l'API REST (Jersey). La base de données se configure dans application.properties.
@Component
// chemin de base de toutes les URL exposées : /api/...
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        // les classes qui exposent les web services
     // produits (MVC)
        register(ClientPresentation.class);  // clients (hexagonal)

        // les convertisseurs exception -> réponse JSON (404, 400)
        register(ResourceNotFoundExceptionMapper.class);
        register(BadRequestExceptionMapper.class);
    }
}
