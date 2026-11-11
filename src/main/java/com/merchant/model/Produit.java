package com.merchant.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Produit {
    private int idProduct;
    private String productName;
    private String description;
    private float price;

    public Produit() {
    }

    public Produit (int idProduct, String productName, String description, float price ) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.description = description ;
        this.price = price;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    
}
