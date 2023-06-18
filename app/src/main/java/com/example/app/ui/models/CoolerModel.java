package com.example.app.ui.models;

public class CoolerModel {
    private String productName;
    private String coolerSocket;
    private String productPrice;
    private String productImage;
    private String coolerRPM;

    public CoolerModel(String productName, String coolerSocket, String productPrice, String productImage, String coolerRPM) {
        this.productName = productName;
        this.coolerSocket = coolerSocket;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.coolerRPM = coolerRPM;
    }

    public String getProductName() {
        return productName;
    }

    public String getCoolerSocket() {
        return coolerSocket;
    }
    public String getCoolerRPM() {
        return coolerRPM;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductImage() {
        return productImage;
    }
}
