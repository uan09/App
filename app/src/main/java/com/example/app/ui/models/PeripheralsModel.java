package com.example.app.ui.models;

public class PeripheralsModel {
    String peripheralsProductName;
    String peripheralsProductPrice;
    int image;

    public PeripheralsModel(String peripheralsProductName, String peripheralsProductPrice, int image) {
        this.peripheralsProductName = peripheralsProductName;
        this.peripheralsProductPrice = peripheralsProductPrice;
        this.image = image;
    }

    public String getPeripheralsProductName() {
        return peripheralsProductName;
    }

    public String getPeripheralsProductPrice() {
        return peripheralsProductPrice;
    }

    public int getImage() {
        return image;
    }
}
