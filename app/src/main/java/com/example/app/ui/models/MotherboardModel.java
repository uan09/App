package com.example.app.ui.models;

public class MotherboardModel {
    String motherboardProductName;
    String motherboardProductPrice;
    int image;

    public MotherboardModel(String motherboardProductName, String motherboardProductPrice, int image) {
        this.motherboardProductName = motherboardProductName;
        this.motherboardProductPrice = motherboardProductPrice;
        this.image = image;
    }

    public String getMotherboardProductName() {
        return motherboardProductName;
    }

    public String getMotherboardProductPrice() {
        return motherboardProductPrice;
    }

    public int getImage() {
        return image;
    }
}
