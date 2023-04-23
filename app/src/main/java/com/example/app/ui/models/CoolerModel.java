package com.example.app.ui.models;

public class CoolerModel {
    String coolerProductName;
    String coolerProductPrice;
    int image;

    public CoolerModel(String coolerProductName, String coolerProductPrice, int image) {
        this.coolerProductName = coolerProductName;
        this.coolerProductPrice = coolerProductPrice;
        this.image = image;
    }

    public String getCoolerProductName() {
        return coolerProductName;
    }

    public String getCoolerProductPrice() {
        return coolerProductPrice;
    }

    public int getImage() {
        return image;
    }
}
