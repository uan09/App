package com.example.app.ui.models;

public class PsuModel {
    private String productName;
    private String psuFormFactor;
    private String psuWattage;
    private String productPrice;
    private String productImage;

    public PsuModel(String productName, String psuFormFactor, String psuWattage, String productPrice, String productImage) {
        this.productName = productName;
        this.psuFormFactor = psuFormFactor;
        this.psuWattage = psuWattage;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public String getPsuFormFactor() {
        return psuFormFactor;
    }

    public String getPsuWattage() {
        return psuWattage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductImage() {
        return productImage;
    }
}
