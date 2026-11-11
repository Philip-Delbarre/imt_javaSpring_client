package com.merchant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.merchant.model.Client;
import com.merchant.adapter.in.rest.ClientRestMapper;

// Mock VMC est essentiel piour tester les endpoints REST sans lancer le serveur complet. Il simule les requêtes HTTP et permet de vérifier les réponses.

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MerchantApplicationTests {

    @LocalServerPort
    private int port =8080;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void testCreationClient() {
        Client clientFictif = new Client(12, "Doe", "John", "chat@fr", new ArrayList<>(),"");

        assertEquals(12, clientFictif.getIdClient().intValue());
        assertEquals("Doe", clientFictif.getNom());
        assertEquals("John", clientFictif.getPrenom());
        assertEquals("chat@fr", clientFictif.getEmail());
        assertTrue(clientFictif.getProduitIds().isEmpty());
    }

// test endpoint produit controller
// // la réposne peut ^tre null
 @SuppressWarnings("null") 
@Test

void testMapperOk() {
    
    ClientRestMapper mapper = new ClientRestMapper();
    Client clientFictif = new Client(12, "Doe", "John", "chat@fr", new ArrayList<>(), "");
    assertNotNull(mapper.toDto(clientFictif));  

}


    // tester une levée d'exception
    @Test
    void testGetClientInconnu() {
        ResponseEntity<String> reponse = restTemplate.getForEntity("http://localhost:" + port + "/api/clients/9999", String.class);
        assertEquals(HttpStatus.NOT_FOUND, reponse.getStatusCode());
        assertTrue(reponse.getBody().contains("Client 9999 introuvable"));
    }
}