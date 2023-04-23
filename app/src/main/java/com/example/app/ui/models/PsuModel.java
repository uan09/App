package com.example.app.ui.models;

public class PsuModel {
    String psuProductName;
    String psuProductPrice;
    int image;

    public PsuModel(String psuProductName, String psuProductPrice, int image) {
        this.psuProductName = psuProductName;
        this.psuProductPrice = psuProductPrice;
        this.image = image;
    }

    public String getPsuProductName() {
        return psuProductName;
    }

    public String getPsuProductPrice() {
        return psuProductPrice;
    }

    public int getImage() {
        return image;
    }
}
