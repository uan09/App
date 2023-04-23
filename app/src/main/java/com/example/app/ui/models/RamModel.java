package com.example.app.ui.models;

public class RamModel {
    String ramProductName;
    String ramProductPrice;
    int image;

    public RamModel(String ramProductName, String ramProductPrice, int image) {
        this.ramProductName = ramProductName;
        this.ramProductPrice = ramProductPrice;
        this.image = image;
    }

    public String getRamProductName() {
        return ramProductName;
    }

    public String getRamProductPrice() {
        return ramProductPrice;
    }

    public int getImage() {
        return image;
    }
}
