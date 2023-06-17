package com.example.app.ui.models;

public class MotherboardModel {
    private String motherboardName;
    private String motherboardSocket;
    private String motherboardFormFactor;
    private String product_name;
    private String product_image;
    private String product_price;



    public MotherboardModel(String motherboardName, String motherboardSocket, String motherboardFormFactor, String product_name, String product_price, String product_image) {
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_price = product_price;
        this.motherboardName = motherboardName;
        this.motherboardSocket = motherboardSocket;
        this.motherboardFormFactor = motherboardFormFactor;
    }

    public String getMotherboardName() {
        return motherboardName;
    }

    public String getMotherboardSocket() {
        return motherboardSocket;
    }

    public String getMotherboardFormFactor() {
        return motherboardFormFactor;
    }
}
