package com.merchant.adapter.in.rest;

public class ResponseRequestDTO {

    private String message;
    private ClientDTO client;

    public ResponseRequestDTO(String message, ClientDTO client) {
        this.message = message;
        this.client = client;
    }

    public String getMessage() { return message; }
    public ClientDTO getClient() { return client; }
    
}

