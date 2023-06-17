package com.example.app.ui.models;

public class MotherboardModel {
    private String motherboardName;
    private String motherboardSocket;
    private String motherboardFormFactor;
    private String motherboardMemoryType;
    private String product_name;
    private String product_price;
    private String product_image;

    public MotherboardModel(String motherboardName, String motherboardSocket, String motherboardFormFactor, String motherboardMemoryType, String product_price, String product_image) {
        this.motherboardName = motherboardName;
        this.motherboardSocket = motherboardSocket;
        this.motherboardFormFactor = motherboardFormFactor;
        this.motherboardMemoryType = motherboardMemoryType;
        this.product_price = product_price;
        this.product_image = product_image;
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

    public String getMotherboardMemoryType() {
        return motherboardMemoryType;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProductDetails(String product_name, String product_price, String product_image) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_image = product_image;
    }
}
