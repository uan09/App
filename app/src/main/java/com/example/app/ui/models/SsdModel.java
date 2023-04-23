package com.example.app.ui.models;

public class SsdModel {
    String ssdProductName;
    String ssdProductPrice;
    int image;

    public SsdModel(String ssdProductName, String ssdProductPrice, int image) {
        this.ssdProductName = ssdProductName;
        this.ssdProductPrice = ssdProductPrice;
        this.image = image;
    }

    public String getSsdProductName() {
        return ssdProductName;
    }

    public String getSsdProductPrice() {
        return ssdProductPrice;
    }

    public int getImage() {
        return image;
    }
}
